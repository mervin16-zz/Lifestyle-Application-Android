package com.th3pl4gu3.lifestyle.ui.home.section_todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.th3pl4gu3.lifestyle.ui.utils.toast
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.databinding.FragmentToDoBinding
import com.th3pl4gu3.lifestyle.ui.utils.action
import com.th3pl4gu3.lifestyle.ui.utils.snackBarWithAction
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates

class FragmentToDo : Fragment() {

    private lateinit var mBinding: FragmentToDoBinding
    private lateinit var mToDoViewModel: ToDoViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_to_do, container, false)

        //Configures the screen views (Eg. Title, appearance of top bar etc...)
        configureScreenAppearance()

        //Get the activity's application
        val application = requireNotNull(this.activity).application

        //Fetch the database
        val dataSource = LifestyleDatabase.getInstance(application)

        //Instantiate the view model of this fragment
        mToDoViewModel =
            ViewModelProviders.of(this, ToDoViewModelFactory(dataSource, application)).get(ToDoViewModel::class.java)

        //Bind view model
        mBinding.toDoViewModel = mToDoViewModel

        //Instantiate the lifecycle owner
        mBinding.lifecycleOwner = this

        //RecyclerView's configuration
        val adapter = ToDoAdapter(ToDoListener {
            requireActivity().findNavController(R.id.Container_fromHomeActivity_BottomAppBarFragments)
                .navigate(
                    R.id.BottomSheetDialog_fromFragmentToDo_Details,
                    bundleOf(getString(R.string.ValuePair_forDataPassing_LifestyleItem) to it)
                )
        })

        mBinding.RecyclerViewFromFragmentToDoMain.adapter = adapter

        mToDoViewModel.toDosMediatorLiveData.observe(viewLifecycleOwner, Observer {
            it.let { x ->

                //Update the UI and determine whether recyclerview should be visible or not
                updateUI(x.isNotEmpty())

                adapter.submitList(x)
            }
        })

        //Swipe configurations
        val swipeHandler = object : ToDoSwipeToCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val swipedToDo =
                    (mBinding.RecyclerViewFromFragmentToDoMain.adapter as ToDoAdapter).currentList[viewHolder.adapterPosition]
                val fab =
                    requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        mToDoViewModel.markItem(swipedToDo)
                    }

                    ItemTouchHelper.RIGHT -> {
                        mToDoViewModel.markAsDeleted(swipedToDo)

                        //Show Snackbar with 'Undo' action
                        requireActivity().findViewById<View>(android.R.id.content).snackBarWithAction(
                            getString(
                                R.string.Message_Exception_fromFragmentLifeStyleItems_ErrorWhileSwiping,
                                swipedToDo.title
                            ), anchorView = fab
                        ) {
                            action(getString(R.string.Button_forLifestyleRestoreItem_SnackBar_Undo)) {
                                mToDoViewModel.insertItem(swipedToDo)
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
        itemTouchHelper.attachToRecyclerView(mBinding.RecyclerViewFromFragmentToDoMain)

        return mBinding.root
    }

    /**
     * Private functions for internal use ONLY
     **/

    private fun updateUI(recyclerViewVisible: Boolean) {
        if (recyclerViewVisible) {
            mBinding.RecyclerViewFromFragmentToDoMain.visibility = View.VISIBLE
            mBinding.EmptyViewForRecyclerView.visibility = View.GONE
        } else {
            if (mToDoViewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_COMPLETE) {
                mBinding.TextViewFromFragmentToDoEmptyView.text =
                    getString(R.string.TextView_fromToDoFragment_Message_EmptyList_Completed)
            } else if (mToDoViewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_ACTIVE) {
                mBinding.TextViewFromFragmentToDoEmptyView.text =
                    getString(R.string.TextView_fromToDoFragment_Message_EmptyList_Active)
            }

            mBinding.RecyclerViewFromFragmentToDoMain.visibility = View.GONE
            mBinding.EmptyViewForRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun configureScreenAppearance() {

        val activity = requireActivity()

        //Set title of fragment
        activity.findViewById<TextView>(R.id.TextView_fromHomeActivity_Screen_Title).text =
            getString(R.string.Menu_BottomAppBar_DrawerMain_ToDo)

        //Show Top Bar
        activity.findViewById<RelativeLayout>(R.id.RelativeLayout_fromHomeActivity_TopBar).visibility = View.VISIBLE

        //Show Fab
        activity.findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached).show()
    }
}
