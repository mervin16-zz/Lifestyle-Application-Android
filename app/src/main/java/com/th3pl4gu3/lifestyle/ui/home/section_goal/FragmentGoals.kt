package com.th3pl4gu3.lifestyle.ui.home.section_goal

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.tuning.Sort
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.databinding.FragmentGoalsBinding
import com.th3pl4gu3.lifestyle.ui.utils.toast
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.utils.SharedPrefUtils
import com.th3pl4gu3.lifestyle.ui.utils.action
import com.th3pl4gu3.lifestyle.ui.utils.snackBarWithAction



class FragmentGoals : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var _viewModel: GoalViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Inflate the layout for this fragment
        val binding: FragmentGoalsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_goals, container, false)

        //Set has menu to true to access BottomAppBar's menu item here
        setHasOptionsMenu(true)

        //Configures the screen views (Eg. Title, appearance of top bar etc...)
        configureScreenAppearance()

        //Get the activity's application
        val application = requireNotNull(this.activity).application

        //Fetch the database
        val dataSource = LifestyleDatabase.getInstance(application)

        //Fetch the ViewModel of this fragment
        _viewModel = ViewModelProviders.of(this, GoalViewModelFactory(dataSource, application)).get(GoalViewModel::class.java)

        //Bind view model with layout
        binding.goalViewModel = _viewModel

        //Instantiate the lifecycle owner of this fragment
        binding.lifecycleOwner = this

        //Attach Sort class to ViewModel
        _viewModel.sort = getPreferences()

        //RecyclerView's configuration
        val adapter = GoalAdapter(GoalListener {
            requireActivity().findNavController(R.id.Container_fromHomeActivity_BottomAppBarFragments)
                .navigate(R.id.BottomSheetDialog_fromFragmentGoal_Details, bundleOf(getString(R.string.ValuePair_forDataPassing_LifestyleItem) to it))
        })

        binding.RecyclerViewFromFragmentGoalMain.adapter = adapter

        _viewModel.goalsMediatorLiveData.observe(viewLifecycleOwner, Observer {
            it.let { x ->

                //Update the UI and determine whether recyclerview should be visible or not
                updateUI(binding, x.isNotEmpty())

                adapter.submitList(x)
            }
        })

        //Swipe configurations
        val swipeHandler = object : GoalSwipeToCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val swipedGoal =
                    (binding.RecyclerViewFromFragmentGoalMain.adapter as GoalAdapter).currentList[viewHolder.adapterPosition]
                val fab =
                    requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        _viewModel.markItem(swipedGoal)
                    }

                    ItemTouchHelper.RIGHT -> {
                        _viewModel.markAsDeleted(swipedGoal)

                        //Show Snackbar with 'Undo' action
                        requireActivity().findViewById<View>(android.R.id.content).snackBarWithAction(
                            getString(
                                R.string.Message_Exception_fromFragmentLifeStyleItems_ErrorWhileSwiping,
                                swipedGoal.title
                            ), anchorView = fab
                        ) {
                            action(getString(R.string.Button_forLifestyleRestoreItem_SnackBar_Undo)) {
                                _viewModel.insertItem(swipedGoal)
                            }
                        }
                    }

                    else -> {
                        requireContext().toast(getString(R.string.Message_Exception_fromFragmentLifeStyleItems_ErrorWhileSwiping))
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.RecyclerViewFromFragmentGoalMain)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        requireContext().getSharedPreferences(getString(R.string.ValuePair_forSortPreferences_Name_Goals_SortingPreferences), Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()

        requireContext().getSharedPreferences(getString(R.string.ValuePair_forSortPreferences_Name_Goals_SortingPreferences), Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.BottomAppBar_fromHomeActivity_MenuMain_Sort).isVisible = true
        menu.findItem(R.id.BottomAppBar_fromHomeActivity_MenuMain_Filter).isVisible = true
        menu.findItem(R.id.BottomAppBar_fromHomeActivity_MenuMain_Search).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.BottomAppBar_fromHomeActivity_MenuMain_Filter -> {
                requireActivity().findNavController(R.id.Container_fromHomeActivity_BottomAppBarFragments)
                    .navigate(R.id.BottomSheetDialog_fromActivityHome_Filter)
                true
            }

            R.id.BottomAppBar_fromHomeActivity_MenuMain_Sort -> {
                requireActivity().findNavController(R.id.Container_fromHomeActivity_BottomAppBarFragments)
                    .navigate(
                        R.id.BottomSheetDialog_fromActivityHome_Sort,
                        bundleOf(getString(R.string.ValuePair_forDataPassing_LifestyleItem_Value) to LifestyleItem.GOAL.value)
                    )
                true
            }

            else -> false
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        _viewModel.sort = getPreferences()
    }


    /**
     * Private functions for internal use ONLY
     **/
    private fun updateUI(binding: FragmentGoalsBinding, recyclerViewVisible: Boolean) {
        if (recyclerViewVisible) {
            binding.RecyclerViewFromFragmentGoalMain.visibility = View.VISIBLE
            binding.EmptyViewForRecyclerView.visibility = View.GONE
        } else {
            if (_viewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_COMPLETE) {
                binding.TextViewFromFragmentGoalEmptyView.text =
                    getString(R.string.TextView_fromGoalsFragment_Message_EmptyList_Completed)
            } else if (_viewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_ACTIVE) {
                binding.TextViewFromFragmentGoalEmptyView.text =
                    getString(R.string.TextView_fromGoalsFragment_Message_EmptyList_Active)
            }

            binding.RecyclerViewFromFragmentGoalMain.visibility = View.GONE
            binding.EmptyViewForRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun configureScreenAppearance() {
        val activity = requireActivity()

        //Set title of fragment
        activity.findViewById<TextView>(R.id.TextView_fromHomeActivity_Screen_Title).text = getString(R.string.Menu_BottomAppBar_DrawerMain_Goals)

        //Show Top Bar
        activity.findViewById<RelativeLayout>(R.id.RelativeLayout_fromHomeActivity_TopBar).visibility = View.VISIBLE

        //Show Fab
        activity.findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached).show()
    }

    private fun getPreferences(): Sort<Goal> {
        return SharedPrefUtils(requireContext(), getString(R.string.ValuePair_forSortPreferences_Name_Goals_SortingPreferences)).getSort()
    }
}
