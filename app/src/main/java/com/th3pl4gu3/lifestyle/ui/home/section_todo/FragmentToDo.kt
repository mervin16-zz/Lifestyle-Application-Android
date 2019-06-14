package com.th3pl4gu3.lifestyle.ui.home.section_todo


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
import com.th3pl4gu3.lifestyle.ui.home.home.RoundedBottomSheetDialogFragmentForLifestyleItemDetails

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

        //Instantiate the view model factory
        val viewModelFactory = ToDoViewModelFactory(dataSource, application)

        //Instantiate the view model of this fragment
        mToDoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ToDoViewModel::class.java)

        //Bind view model
        mBinding.toDoViewModel = mToDoViewModel

        //Instantiate the lifecycle owner
        mBinding.lifecycleOwner = this

        //RecyclerView's configuration
        val adapter = ToDoAdapter(ToDoListener {
            val bottomFragment =
                RoundedBottomSheetDialogFragmentForLifestyleItemDetails(it)
            bottomFragment.show(requireActivity().supportFragmentManager, bottomFragment.tag)
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

                val swipedToDo = (mBinding.RecyclerViewFromFragmentToDoMain.adapter as ToDoAdapter).currentList[viewHolder.adapterPosition]
                val fab = requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)
                Log.d("POSITIONING", "Layout Position: ${viewHolder.layoutPosition}")
                Log.d("POSITIONING", "Adapter Position: ${viewHolder.adapterPosition}")
                Log.d("POSITIONING", "Old Position: ${viewHolder.oldPosition}")
                Log.d("POSITIONING", "Item Position in Array: ${(mBinding.RecyclerViewFromFragmentToDoMain.adapter as ToDoAdapter).currentList.indexOf(swipedToDo)}")
                when(direction){
                    ItemTouchHelper.LEFT -> {
                        mToDoViewModel.markItem(swipedToDo)
                    }

                    ItemTouchHelper.RIGHT -> {
                        mToDoViewModel.markAsDeleted(swipedToDo)

                        //Show Snackbar with 'Undo' action
                        requireActivity().findViewById<View>(android.R.id.content).snackBarWithAction(getString(R.string.Message_Exception_fromFragmentLifeStyleItems_ErrorWhileSwiping, swipedToDo.title), anchorView = fab){
                                action(getString(R.string.Button_forLifestyleRestoreItem_SnackBar_Undo)){
                                    mToDoViewModel.insertItem(swipedToDo)
                                }
                            }
                    }

                    else ->{
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

    private fun updateUI(recyclerViewVisible: Boolean){
        if(recyclerViewVisible){
            mBinding.RecyclerViewFromFragmentToDoMain.visibility = View.VISIBLE
            mBinding.EmptyViewForRecyclerView.visibility = View.GONE
        }else{
            if(mToDoViewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_COMPLETE){
                mBinding.TextViewFromFragmentToDoEmptyView.text = getString(R.string.TextView_fromToDoFragment_Message_EmptyList_Completed)
            }else if(mToDoViewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_ACTIVE){
                mBinding.TextViewFromFragmentToDoEmptyView.text = getString(R.string.TextView_fromToDoFragment_Message_EmptyList_Active)
            }

            mBinding.RecyclerViewFromFragmentToDoMain.visibility = View.GONE
            mBinding.EmptyViewForRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun configureScreenAppearance(){
        //Set title of fragment
        val screenTitle = requireActivity().findViewById<TextView>(R.id.TextView_fromHomeActivity_Screen_Title)
        screenTitle.text = getString(R.string.TextView_fromFragmentInHomeActivity_ScreenTitle_ToDo)

        //Show Top Bar
        val topBar = requireActivity().findViewById<RelativeLayout>(R.id.RelativeLayout_fromHomeActivity_TopBar)
        topBar.visibility = View.VISIBLE

        //Show Fab
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)
        fab.show()
    }
}
