package com.th3pl4gu3.lifestyle.ui.home.section_goal

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.databinding.FragmentGoalsBinding
import com.th3pl4gu3.lifestyle.ui.utils.toast
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.utils.action
import com.th3pl4gu3.lifestyle.ui.utils.snackBarWithAction

class FragmentGoals : Fragment() {

    private lateinit var mBinding: FragmentGoalsBinding
    private lateinit var mGoalViewModel: GoalViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_goals, container, false)

        //Set has menu to true to access bottom app bar's menu item here
        setHasOptionsMenu(true)

        //Configures the screen views (Eg. Title, appearance of top bar etc...)
        configureScreenAppearance()

        //Get the activity's application
        val application = requireNotNull(this.activity).application

        //Fetch the database
        val dataSource = LifestyleDatabase.getInstance(application)

        //Instantiate the view model of this fragment
        mGoalViewModel =
            ViewModelProviders.of(this, GoalViewModelFactory(dataSource, application)).get(GoalViewModel::class.java)

        //Bind view model
        mBinding.goalViewModel = mGoalViewModel

        //Instantiate the lifecycle owner
        mBinding.lifecycleOwner = this

        //RecyclerView's configuration
        val adapter = GoalAdapter(GoalListener {
            requireActivity().findNavController(R.id.Container_fromHomeActivity_BottomAppBarFragments)
                .navigate(R.id.BottomSheetDialog_fromFragmentGoal_Details, bundleOf(getString(R.string.ValuePair_forDataPassing_LifestyleItem) to it))
        })

        mBinding.RecyclerViewFromFragmentGoalMain.adapter = adapter

        mGoalViewModel.goalsMediatorLiveData.observe(viewLifecycleOwner, Observer {
            it.let { x ->

                //Update the UI and determine whether recyclerview should be visible or not
                updateUI(x.isNotEmpty())

                adapter.submitList(x)
            }
        })

        //Swipe configurations
        val swipeHandler = object : GoalSwipeToCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val swipedGoal =
                    (mBinding.RecyclerViewFromFragmentGoalMain.adapter as GoalAdapter).currentList[viewHolder.adapterPosition]
                val fab =
                    requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        mGoalViewModel.markItem(swipedGoal)
                    }

                    ItemTouchHelper.RIGHT -> {
                        mGoalViewModel.markAsDeleted(swipedGoal)

                        //Show Snackbar with 'Undo' action
                        requireActivity().findViewById<View>(android.R.id.content).snackBarWithAction(
                            getString(
                                R.string.Message_Exception_fromFragmentLifeStyleItems_ErrorWhileSwiping,
                                swipedGoal.title
                            ), anchorView = fab
                        ) {
                            action(getString(R.string.Button_forLifestyleRestoreItem_SnackBar_Undo)) {
                                mGoalViewModel.insertItem(swipedGoal)
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
        itemTouchHelper.attachToRecyclerView(mBinding.RecyclerViewFromFragmentGoalMain)

        return mBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.BottomAppBar_fromHomeActivity_MenuMain_Sort).isVisible = true
        menu.findItem(R.id.BottomAppBar_fromHomeActivity_MenuMain_Filter).isVisible = true
        menu.findItem(R.id.BottomAppBar_fromHomeActivity_MenuMain_Search).isVisible = false
    }

    /**
     * Private functions for internal use ONLY
     **/

    private fun updateUI(recyclerViewVisible: Boolean) {
        if (recyclerViewVisible) {
            mBinding.RecyclerViewFromFragmentGoalMain.visibility = View.VISIBLE
            mBinding.EmptyViewForRecyclerView.visibility = View.GONE
        } else {
            if (mGoalViewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_COMPLETE) {
                mBinding.TextViewFromFragmentGoalEmptyView.text =
                    getString(R.string.TextView_fromGoalsFragment_Message_EmptyList_Completed)
            } else if (mGoalViewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_ACTIVE) {
                mBinding.TextViewFromFragmentGoalEmptyView.text =
                    getString(R.string.TextView_fromGoalsFragment_Message_EmptyList_Active)
            }

            mBinding.RecyclerViewFromFragmentGoalMain.visibility = View.GONE
            mBinding.EmptyViewForRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun configureScreenAppearance() {
        val activity = requireActivity()

        //Set title of fragment
        activity.findViewById<TextView>(R.id.TextView_fromHomeActivity_Screen_Title).text =
            getString(R.string.Menu_BottomAppBar_DrawerMain_Goals)

        //Show Top Bar
        activity.findViewById<RelativeLayout>(R.id.RelativeLayout_fromHomeActivity_TopBar).visibility = View.VISIBLE

        //Show Fab
        activity.findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached).show()
    }


}
