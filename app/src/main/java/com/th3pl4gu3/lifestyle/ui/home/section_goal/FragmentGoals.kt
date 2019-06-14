package com.th3pl4gu3.lifestyle.ui.home.section_goal


import android.os.Bundle
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
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.databinding.FragmentGoalsBinding
import com.th3pl4gu3.lifestyle.ui.utils.toast
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.home.home.RoundedBottomSheetDialogFragmentForLifestyleItemDetails
import com.th3pl4gu3.lifestyle.ui.utils.action
import com.th3pl4gu3.lifestyle.ui.utils.snackBarWithAction

class FragmentGoals : Fragment() {

    private lateinit var mBinding: FragmentGoalsBinding
    private lateinit var mGoalViewModel: GoalViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_goals, container, false)

        //Configures the screen views (Eg. Title, appearance of top bar etc...)
        configureScreenAppearance()

        //Get the activity's application
        val application = requireNotNull(this.activity).application

        //Fetch the database
        val dataSource = LifestyleDatabase.getInstance(application)

        //Instantiate the view model factory
        val viewModelFactory = GoalViewModelFactory(dataSource, application)

        //Instantiate the view model of this fragment
        mGoalViewModel = ViewModelProviders.of(this, viewModelFactory).get(GoalViewModel::class.java)

        //Bind view model
        mBinding.goalViewModel = mGoalViewModel

        //Instantiate the lifecycle owner
        mBinding.lifecycleOwner = this

        //RecyclerView's configuration
        val adapter = GoalAdapter(GoalListener{
            val bottomFragment =
                RoundedBottomSheetDialogFragmentForLifestyleItemDetails(it)
            bottomFragment.show(requireActivity().supportFragmentManager, bottomFragment.tag)
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

                val swipedGoal = (mBinding.RecyclerViewFromFragmentGoalMain.adapter as GoalAdapter).currentList[viewHolder.adapterPosition]
                val fab = requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)

                when(direction){
                    ItemTouchHelper.LEFT -> {
                        mGoalViewModel.markItem(swipedGoal)
                    }

                    ItemTouchHelper.RIGHT -> {
                        mGoalViewModel.markAsDeleted(swipedGoal)

                        //Show Snackbar with 'Undo' action
                        requireActivity().findViewById<View>(android.R.id.content).snackBarWithAction(getString(R.string.Message_Exception_fromFragmentLifeStyleItems_ErrorWhileSwiping, swipedGoal.title), anchorView = fab){
                            action(getString(R.string.Button_forLifestyleRestoreItem_SnackBar_Undo)){
                                mGoalViewModel.insertItem(swipedGoal)
                                //Restore Item
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
        itemTouchHelper.attachToRecyclerView(mBinding.RecyclerViewFromFragmentGoalMain)

        return mBinding.root
    }

    /**
     * Private functions for internal use ONLY
     **/

    private fun updateUI(recyclerViewVisible: Boolean){
        if(recyclerViewVisible){
            mBinding.RecyclerViewFromFragmentGoalMain.visibility = View.VISIBLE
            mBinding.EmptyViewForRecyclerView.visibility = View.GONE
        }else{
            if(mGoalViewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_COMPLETE){
                mBinding.TextViewFromFragmentGoalEmptyView.text = getString(R.string.TextView_fromGoalsFragment_Message_EmptyList_Completed)
            }else if(mGoalViewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_ACTIVE){
                mBinding.TextViewFromFragmentGoalEmptyView.text = getString(R.string.TextView_fromGoalsFragment_Message_EmptyList_Active)
            }

            mBinding.RecyclerViewFromFragmentGoalMain.visibility = View.GONE
            mBinding.EmptyViewForRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun configureScreenAppearance(){
        //Set title of fragment
        val screenTitle = requireActivity().findViewById<TextView>(R.id.TextView_fromHomeActivity_Screen_Title)
        screenTitle.text = getString(R.string.TextView_fromFragmentInHomeActivity_ScreenTitle_Goals)

        //Show Top Bar
        val topBar = requireActivity().findViewById<RelativeLayout>(R.id.RelativeLayout_fromHomeActivity_TopBar)
        topBar.visibility = View.VISIBLE

        //Show Fab
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)
        fab.show()
    }
}
