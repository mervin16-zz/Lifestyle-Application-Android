package com.th3pl4gu3.lifestyle.ui.home.section_todo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.Filter
import com.th3pl4gu3.lifestyle.ui.Utils.toast
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.databinding.FragmentToDoBinding
import com.th3pl4gu3.lifestyle.ui.Utils.SwipeToCallback
import com.th3pl4gu3.lifestyle.ui.home.ActivityHomeViewModel

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

        //Instantiate the lifecycle owner
        mBinding.lifecycleOwner = this

        //RecyclerView's configuration
        val adapter = ToDoAdapter()
        mBinding.RecyclerViewFromFragmentToDoMain.adapter = adapter

        mToDoViewModel._toDos.observe(viewLifecycleOwner, Observer {
            val newList = Filter<ToDo>(it).getActive()

            newList.let { x ->

                //Update the UI and determine whether recyclerview should be visible or not
                updateUI(x.isNotEmpty())

                adapter.submitList(x)
            }
        })

        //Swipe configurations
        val swipeHandler = object : SwipeToCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val swipedToDo = (mBinding.RecyclerViewFromFragmentToDoMain.adapter as ToDoAdapter).currentList[viewHolder.adapterPosition]

                when(direction){
                    ItemTouchHelper.LEFT -> {

                        mToDoViewModel.markAsCompleted(swipedToDo)

                        requireContext().toast("I was swiped left. ${swipedToDo.title} should be marked as completed.")
                    }

                    ItemTouchHelper.RIGHT -> {
                        mToDoViewModel.markAsDeleted(swipedToDo)

                        requireContext().toast("${swipedToDo.title} has been deleted.")
                    }

                    else ->{
                        requireContext().toast("There was an error while swiping your request. Please try again.")
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(mBinding.RecyclerViewFromFragmentToDoMain)

        return mBinding.root
    }


    //Private methods
    private fun updateUI(recyclerviewVisibile: Boolean){
        if(recyclerviewVisibile){
            mBinding.RecyclerViewFromFragmentToDoMain.visibility = View.VISIBLE
            mBinding.EmptyViewForRecyclerView.visibility = View.GONE
        }else{
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

        //Show Toggle Buttons
        val toggleButtonView = requireActivity().findViewById<LinearLayout>(R.id.LinearLayout_fromHomeActivity_ToggleButton)
        toggleButtonView.visibility = View.VISIBLE

        //Show Fab
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)
        fab.show()
    }
}
