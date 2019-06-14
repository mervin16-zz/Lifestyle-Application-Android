package com.th3pl4gu3.lifestyle.ui.home.section_tobuy

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
import com.th3pl4gu3.lifestyle.databinding.FragmentToBuyBinding
import com.th3pl4gu3.lifestyle.ui.utils.toast
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.home.home.RoundedBottomSheetDialogFragmentForLifestyleItemDetails
import com.th3pl4gu3.lifestyle.ui.utils.action
import com.th3pl4gu3.lifestyle.ui.utils.snackBarWithAction

class FragmentToBuy : Fragment() {

    private lateinit var mBinding: FragmentToBuyBinding
    private lateinit var mToBuyViewModel: ToBuyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_to_buy, container, false)

        //Configures the screen views (Eg. Title, appearance of top bar etc...)
        configureScreenAppearance()

        //Get the activity's application
        val application = requireNotNull(this.activity).application

        //Fetch the database
        val dataSource = LifestyleDatabase.getInstance(application)

        //Instantiate the view model factory
        val viewModelFactory = ToBuyViewModelFactory(dataSource, application)

        //Instantiate the view model of this fragment
        mToBuyViewModel = ViewModelProviders.of(this, viewModelFactory).get(ToBuyViewModel::class.java)

        //Bind view model
        mBinding.toBuyViewModel = mToBuyViewModel

        //Instantiate the lifecycle owner
        mBinding.lifecycleOwner = this

        //RecyclerView's configuration
        val adapter = ToBuyAdapter(ToBuyListener{
            val bottomFragment =
                RoundedBottomSheetDialogFragmentForLifestyleItemDetails(it)
            bottomFragment.show(requireActivity().supportFragmentManager, bottomFragment.tag)
        })

        mBinding.RecyclerViewFromFragmentToBuyMain.adapter = adapter

        mToBuyViewModel.toBuysMediatorLiveData.observe(viewLifecycleOwner, Observer {
            it.let { x ->

                //Update the UI and determine whether recyclerview should be visible or not
                updateUI(x.isNotEmpty())

                adapter.submitList(x)
            }
        })

        //Swipe configurations
        val swipeHandler = object : ToBuySwipeToCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val swipedToBuy = (mBinding.RecyclerViewFromFragmentToBuyMain.adapter as ToBuyAdapter).currentList[viewHolder.adapterPosition]
                val fab = requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)

                when(direction){
                    ItemTouchHelper.LEFT -> {
                        mToBuyViewModel.markItem(swipedToBuy)
                    }

                    ItemTouchHelper.RIGHT -> {
                        mToBuyViewModel.markAsDeleted(swipedToBuy)

                        //Show Snackbar with 'Undo' action
                        requireActivity().findViewById<View>(android.R.id.content).snackBarWithAction(getString(R.string.Message_Exception_fromFragmentLifeStyleItems_ErrorWhileSwiping, swipedToBuy.title), anchorView = fab){
                            action(getString(R.string.Button_forLifestyleRestoreItem_SnackBar_Undo)){
                                mToBuyViewModel.insertItem(swipedToBuy)
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
        itemTouchHelper.attachToRecyclerView(mBinding.RecyclerViewFromFragmentToBuyMain)

        return mBinding.root
    }

    /**
     * Private functions for internal use ONLY
     **/

    private fun updateUI(recyclerViewVisible: Boolean){
        if(recyclerViewVisible){
            mBinding.RecyclerViewFromFragmentToBuyMain.visibility = View.VISIBLE
            mBinding.EmptyViewForRecyclerView.visibility = View.GONE
        }else{
            if(mToBuyViewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_COMPLETE){
                mBinding.TextViewFromFragmentToBuyEmptyView.text = getString(R.string.TextView_fromToBuyFragment_Message_EmptyList_Completed)
            }else if(mToBuyViewModel.currentToggleButtonState == ToggleButtonStates.BUTTON_ACTIVE){
                mBinding.TextViewFromFragmentToBuyEmptyView.text = getString(R.string.TextView_fromToBuyFragment_Message_EmptyList_Active)
            }

            mBinding.RecyclerViewFromFragmentToBuyMain.visibility = View.GONE
            mBinding.EmptyViewForRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun configureScreenAppearance(){
        //Set title of fragment
        val screenTitle = requireActivity().findViewById<TextView>(R.id.TextView_fromHomeActivity_Screen_Title)
        screenTitle.text = getString(R.string.TextView_fromFragmentInHomeActivity_ScreenTitle_ToBuy)

        //Show Top Bar
        val topBar = requireActivity().findViewById<RelativeLayout>(R.id.RelativeLayout_fromHomeActivity_TopBar)
        topBar.visibility = View.VISIBLE

        //Show Fab
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)
        fab.show()
    }
}
