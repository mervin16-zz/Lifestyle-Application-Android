package com.th3pl4gu3.lifestyle.ui.home.section_tobuy

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.tuning.Sort
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.databinding.FragmentToBuyBinding
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.utils.SharedPrefUtils
import com.th3pl4gu3.lifestyle.ui.utils.action
import com.th3pl4gu3.lifestyle.ui.utils.snackBarWithAction
import com.th3pl4gu3.lifestyle.ui.utils.toast



class FragmentToBuy : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var _viewModel: ToBuyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding: FragmentToBuyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_to_buy, container, false)

        //Set has menu to true to access bottom app bar's menu item here
        setHasOptionsMenu(true)

        //Configures the screen views (Eg. Title, appearance of top bar etc...)
        configureScreenAppearance()

        //Get the activity's application
        val application = requireNotNull(this.activity).application

        //Fetch the database
        val dataSource = LifestyleDatabase.getInstance(application)

        //Instantiate the view model of this fragment
        _viewModel =
            ViewModelProviders.of(this, ToBuyViewModelFactory(dataSource, application)).get(ToBuyViewModel::class.java)

        //Bind view model
        binding.toBuyViewModel = _viewModel

        //Instantiate the lifecycle owner
        binding.lifecycleOwner = this

        //Attach Sort class to ViewModel
        _viewModel.sort = getPreferences()

        //RecyclerView's configuration
        val adapter = ToBuyAdapter(ToBuyListener {
            requireActivity().findNavController(R.id.Container_fromHomeActivity_BottomAppBarFragments)
                .navigate(
                    R.id.BottomSheetDialog_fromFragmentToBuy_Details,
                    bundleOf(getString(R.string.ValuePair_forDataPassing_LifestyleItem) to it)
                )
        })

        binding.RecyclerViewFromFragmentToBuyMain.adapter = adapter

        _viewModel.toBuysMediatorLiveData.observe(viewLifecycleOwner, Observer {
            it.let { x ->

                //Update the UI and determine whether recyclerview should be visible or not
                updateUI(binding, x.isNotEmpty())

                adapter.submitList(x)
            }
        })

        //Swipe configurations
        val swipeHandler = object : ToBuySwipeToCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val swipedToBuy =
                    (binding.RecyclerViewFromFragmentToBuyMain.adapter as ToBuyAdapter).currentList[viewHolder.adapterPosition]
                val fab =
                    requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        _viewModel.markItem(swipedToBuy)
                    }

                    ItemTouchHelper.RIGHT -> {
                        _viewModel.markAsDeleted(swipedToBuy)

                        //Show Snackbar with 'Undo' action
                        requireActivity().findViewById<View>(android.R.id.content).snackBarWithAction(
                            getString(
                                R.string.Message_Exception_fromFragmentLifeStyleItems_ErrorWhileSwiping,
                                swipedToBuy.title
                            ), anchorView = fab
                        ) {
                            action(getString(R.string.Button_forLifestyleRestoreItem_SnackBar_Undo)) {
                                _viewModel.insertItem(swipedToBuy)
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
        itemTouchHelper.attachToRecyclerView(binding.RecyclerViewFromFragmentToBuyMain)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        requireContext().getSharedPreferences(getString(R.string.ValuePair_forSortPreferences_Name_ToBuy_SortingPreferences), Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()

        requireContext().getSharedPreferences(getString(R.string.ValuePair_forSortPreferences_Name_ToBuy_SortingPreferences), Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this)
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
                        bundleOf(getString(R.string.ValuePair_forDataPassing_LifestyleItem_Value) to LifestyleItem.TO_BUY.value)
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

    private fun updateUI(binding: FragmentToBuyBinding, recyclerViewVisible: Boolean) {
        if (recyclerViewVisible) {
            binding.RecyclerViewFromFragmentToBuyMain.visibility = View.VISIBLE
            binding.EmptyViewForRecyclerView.visibility = View.GONE
        } else {
            if (_viewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_COMPLETE) {
                binding.TextViewFromFragmentToBuyEmptyView.text =
                    getString(R.string.TextView_fromToBuyFragment_Message_EmptyList_Completed)
            } else if (_viewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_ACTIVE) {
                binding.TextViewFromFragmentToBuyEmptyView.text =
                    getString(R.string.TextView_fromToBuyFragment_Message_EmptyList_Active)
            }

            binding.RecyclerViewFromFragmentToBuyMain.visibility = View.GONE
            binding.EmptyViewForRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun configureScreenAppearance() {
        val activity = requireActivity()

        //Set title of fragment
        activity.findViewById<TextView>(R.id.TextView_fromHomeActivity_Screen_Title).text =
            getString(R.string.Menu_BottomAppBar_DrawerMain_ToBuy)

        //Show Top Bar
        activity.findViewById<RelativeLayout>(R.id.RelativeLayout_fromHomeActivity_TopBar).visibility =
            View.VISIBLE

        //Show Fab
        activity.findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached).show()
    }

    private fun getPreferences(): Sort<ToBuy> {
        return SharedPrefUtils(requireContext(), getString(R.string.ValuePair_forSortPreferences_Name_ToBuy_SortingPreferences)).getSort()
    }
}
