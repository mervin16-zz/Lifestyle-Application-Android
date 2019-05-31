package com.th3pl4gu3.lifestyle.ui.add_item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.databinding.ActivityAddItemBinding
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.core.utils.toast
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase


class ActivityAddItem : AppCompatActivity() {

    private lateinit var mBinding: ActivityAddItemBinding
    private lateinit var mViewModel: AddItemViewModel

    private val mGeneralTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            return
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            return
        }

        override fun afterTextChanged(s: Editable?) {
            if (areSomeTextBoxesEmpty()) {
                mBinding.FABFromAddItemActivityAddItem.hide()
                return
            }

            mBinding.FABFromAddItemActivityAddItem.show()

        }

    }

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
                snackbar.setAction("Yes") { snackbar.dismiss() }.show()
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
    }

    override fun onStart() {
        super.onStart()

        mBinding.FABFromAddItemActivityAddItem.setOnClickListener {

                val title = mBinding.EditTextFromAddItemActivityName.text.toString()
                val category = mBinding.SpinnerFromAddItemActivityCategory.selectedItem.toString()
                val type = mBinding.SpinnerFromAddItemActivityType.selectedItem.toString()

                mViewModel.addItem(type, title, category)
        }

        mBinding.ImageViewFromAddItemActivityBackIcon.setOnClickListener {
            finish()
        }
    }

    fun setPriority(view: View){
        //Set the priority of the view model
        mViewModel.setPriority(Integer.parseInt(view.tag.toString()))
    }


    //Private Methods
    private fun initializeStatusBarColor() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)
    }

    private fun configureViews() {

        /*
         * Load Spinners
         */

        //Type Spinner
        val typeAdapter = ArrayAdapter(this, R.layout.custom_text_spinner_default, Utils.getLifestyleItemEnumsToFormattedString())
        typeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        mBinding.SpinnerFromAddItemActivityType.adapter = typeAdapter

        //Category Spinner
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.App_LifeStyleItem_Categories))
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        mBinding.SpinnerFromAddItemActivityCategory.adapter = categoryAdapter


        /*
         * Attach TextWatcher to EditTexts
         */

        mBinding.EditTextFromAddItemActivityName.addTextChangedListener(mGeneralTextWatcher)
        mBinding.EditTextFromAddItemActivityPrice.addTextChangedListener(mGeneralTextWatcher)
        mBinding.EditTextFromAddItemActivityQuantity.addTextChangedListener(mGeneralTextWatcher)
    }

    private fun areSomeTextBoxesEmpty(): Boolean{
        return mBinding.EditTextFromAddItemActivityName.text.isNullOrEmpty() ||
                mBinding.EditTextFromAddItemActivityPrice.text.isNullOrEmpty() ||
                mBinding.EditTextFromAddItemActivityQuantity.text.isNullOrEmpty()
    }
}
