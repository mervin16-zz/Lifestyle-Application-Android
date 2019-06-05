package com.th3pl4gu3.lifestyle.ui.add_item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.databinding.ActivityAddItemBinding
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.ui.Utils.toast
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase


class ActivityAddItem : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var mBinding: ActivityAddItemBinding
    private lateinit var mViewModel: AddItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get the binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_item)

        //Get the correct status bar color
        initializeStatusBarColor()

        //Configure EditTexts & Spinners in the form
        configureViews()

        //Get the application
        val application = requireNotNull(this).application

        //Get reference to database
        val database = LifestyleDatabase.getInstance(application)

        //Get the view model factory
        val viewModelFactory = AddItemViewModelFactory(database, application)

        //Get the view mode
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddItemViewModel::class.java)

        mViewModel = viewModel

        mBinding.lifecycleOwner = this

        // Add an Observer on the state variable for showing a Snackbar message
        mViewModel.showSnackBarEvent.observe(this, Observer {message ->
            if (message != null) {
                // Observed state is true.
                val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Ok") { snackbar.dismiss() }.show()
                snackbar.anchorView = mBinding.FABFromAddItemActivityAddItem
                // Reset state to make sure the snackbar is only shown once, even if the device
                // has a configuration change.
                viewModel.doneShowingSnackbar()
            }
        })

        // Add an Observer on the state variable for showing a Toast message
        mViewModel.showToastEvent.observe(this, Observer {message ->
            if (message != null) { // Observed state is not null.
                this.toast(message)
                // Reset state to make sure the snackbar is only shown once, even if the device
                // has a configuration change.
                viewModel.doneShowingToast()
            }
        })

        // Add Observers for TextBoxes
        mViewModel.showErrorMessageForItemTitle.observe(this, Observer {message ->
            if (message != null) {
                // Observed state is not null.
                mBinding.EditTextFromAddItemActivityName.error = message
                // Reset state to make sure the message is only shown once, even if the device
                // has a configuration change.
                viewModel.doneShowingErrorMessageForItemTitle()
            }
        })

        mViewModel.showErrorMessageForItemPrice.observe(this, Observer {message ->
            if (message != null) {
                // Observed state is not null.
                mBinding.EditTextFromAddItemActivityPrice.error = message
                // Reset state to make sure the message is only shown once, even if the device
                // has a configuration change.
                viewModel.doneShowingErrorMessageForItemPrice()
            }
        })

        mViewModel.showErrorMessageForItemQuantity.observe(this, Observer {message ->
            if (message != null) {
                // Observed state is not null.
                mBinding.EditTextFromAddItemActivityQuantity.error = message
                // Reset state to make sure the message is only shown once, even if the device
                // has a configuration change.
                viewModel.doneShowingErrorMessageForItemQuantity()
            }
        })

    }

    override fun onStart() {
        super.onStart()

        mBinding.FABFromAddItemActivityAddItem.setOnClickListener {

                val title = mBinding.EditTextFromAddItemActivityName.text.toString()
                val category = mBinding.SpinnerFromAddItemActivityCategory.selectedItem.toString()
                val type = mBinding.SpinnerFromAddItemActivityType.selectedItem.toString()
                val price = mBinding.EditTextFromAddItemActivityPrice.text.toString().toDoubleOrNull()
                val qty = mBinding.EditTextFromAddItemActivityQuantity.text.toString().toIntOrNull()

                mViewModel.addItem(type, title, category, price, qty)
        }

        mBinding.ImageViewFromAddItemActivityIconBack.setOnClickListener {
            finish()
        }
    }


    //Item selected listener for Type Spinner
    override fun onNothingSelected(parent: AdapterView<*>?) {return}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(position).toString()
        when(Utils.formattedStringToLifestyleEnum(selectedItem)){
            LifestyleItem.GOAL -> mBinding.LinearLayoutFromAddItemActivityToBuyExtraTextFields.visibility = View.GONE
            LifestyleItem.TO_BUY -> mBinding.LinearLayoutFromAddItemActivityToBuyExtraTextFields.visibility = View.VISIBLE
            LifestyleItem.TO_DO -> mBinding.LinearLayoutFromAddItemActivityToBuyExtraTextFields.visibility = View.GONE
        }
    }

    fun setPriority(view: View){
        //Update the view and determine whether a priority was selected or unselected
        val prioritySelected = updatePriorityViewAndDetermineIfPriorityHasBeenSelected(view)

        if(prioritySelected){
            //Set the priority of the view model
            mViewModel.setPriority(Integer.parseInt(view.tag.toString()))
        }else{
            //Flag to set priority to null
            mViewModel.setPriority(0)
        }
    }

    //Private Methods
    private fun initializeStatusBarColor() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)
    }

    private fun updatePriorityViewAndDetermineIfPriorityHasBeenSelected(view: View) : Boolean{

        if(!mBinding.ImageButtonFromAddItemActivityIconPriority1.isVisible ||
            !mBinding.ImageButtonFromAddItemActivityIconPriority2.isVisible ||
            !mBinding.ImageButtonFromAddItemActivityIconPriority3.isVisible ||
            !mBinding.ImageButtonFromAddItemActivityIconPriority4.isVisible){

            //If any of these views are not visible, it means that a priority was already selected.
            //Therefore we need to reset all views to visible again.
            mBinding.ImageButtonFromAddItemActivityIconPriority1.visibility = View.VISIBLE
            mBinding.ImageButtonFromAddItemActivityIconPriority2.visibility = View.VISIBLE
            mBinding.ImageButtonFromAddItemActivityIconPriority3.visibility = View.VISIBLE
            mBinding.ImageButtonFromAddItemActivityIconPriority4.visibility = View.VISIBLE
            return false
        }

        when(view.id){
            R.id.ImageButton_fromAddItemActivity_Icon_Priority1 -> {
                mBinding.ImageButtonFromAddItemActivityIconPriority2.visibility = View.GONE
                mBinding.ImageButtonFromAddItemActivityIconPriority3.visibility = View.GONE
                mBinding.ImageButtonFromAddItemActivityIconPriority4.visibility = View.GONE
            }
            R.id.ImageButton_fromAddItemActivity_Icon_Priority2 -> {
                mBinding.ImageButtonFromAddItemActivityIconPriority1.visibility = View.GONE
                mBinding.ImageButtonFromAddItemActivityIconPriority3.visibility = View.GONE
                mBinding.ImageButtonFromAddItemActivityIconPriority4.visibility = View.GONE
            }
            R.id.ImageButton_fromAddItemActivity_Icon_Priority3 -> {
                mBinding.ImageButtonFromAddItemActivityIconPriority1.visibility = View.GONE
                mBinding.ImageButtonFromAddItemActivityIconPriority2.visibility = View.GONE
                mBinding.ImageButtonFromAddItemActivityIconPriority4.visibility = View.GONE
            }
            R.id.ImageButton_fromAddItemActivity_Icon_Priority4 -> {
                mBinding.ImageButtonFromAddItemActivityIconPriority1.visibility = View.GONE
                mBinding.ImageButtonFromAddItemActivityIconPriority2.visibility = View.GONE
                mBinding.ImageButtonFromAddItemActivityIconPriority3.visibility = View.GONE
            }
            else -> {return false}
        }

        return true
    }

    private fun configureViews() {

        /*
         * Load Spinners
         */

        //Type Spinner
        val typeAdapter = ArrayAdapter(this, R.layout.custom_text_spinner_default, Utils.getLifestyleItemEnumsToFormattedString())
        typeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        mBinding.SpinnerFromAddItemActivityType.adapter = typeAdapter
        mBinding.SpinnerFromAddItemActivityType.onItemSelectedListener = this

        //Category Spinner
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.App_LifeStyleItem_Categories))
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        mBinding.SpinnerFromAddItemActivityCategory.adapter = categoryAdapter

    }
}
