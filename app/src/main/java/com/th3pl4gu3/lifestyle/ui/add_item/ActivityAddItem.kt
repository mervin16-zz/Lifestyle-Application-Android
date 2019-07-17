package com.th3pl4gu3.lifestyle.ui.add_item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.databinding.DataBindingUtil
import com.th3pl4gu3.lifestyle.R
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.databinding.ActivityAddItemBinding
import com.th3pl4gu3.lifestyle.ui.SpinnerAdapter
import com.th3pl4gu3.lifestyle.ui.utils.Validation
import com.th3pl4gu3.lifestyle.ui.utils.snackBar
import kotlinx.android.synthetic.main.include_foradditem_section_calendar.view.*
import kotlinx.android.synthetic.main.include_foradditem_section_details.view.*


class ActivityAddItem : AppCompatActivity() {

    private lateinit var mBinding: ActivityAddItemBinding
    private lateinit var mViewModel: AddItemViewModel

    private val spinnerTypeListener = object : OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {return}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            try{
                val selectedItem = parent?.getItemAtPosition(position).toString()

                if (selectedItem == getString(R.string.Spinner_fromAddItemActivity_Hint_ChooseTask)) return

                when (Utils.formattedStringToLifestyleEnum(selectedItem)) {
                    LifestyleItem.GOAL -> {
                        updateUI_DetailsExtraFields(false)
                        updateUI_Priorities(false)
                    }
                    LifestyleItem.TO_BUY -> {
                        updateUI_DetailsExtraFields(true)
                        updateUI_Priorities(true)
                    }
                    LifestyleItem.TO_DO -> {
                        updateUI_DetailsExtraFields(false)
                        updateUI_Priorities(true)
                    }
                }
            }catch (ex: Exception){
                showToast(getString(R.string.Message_Exception_fromActivityAddItem_ErrorWhileFetchingTask, ex.message))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get the binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_item)

        //Get the correct status bar color
        initializeStatusBarColor()

        //Configure Spinners in the form
        configureSpinners()

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
                showSnackBar(message)
                // Reset state to make sure the snackbar is only shown once, even if the device
                // has a configuration change.
                viewModel.doneShowingSnackbar()
            }
        })
    }

    override fun onStart() {
        super.onStart()

        mBinding.ImageButtonIconBack.setOnClickListener {
            onBackPressed()
        }

        mBinding.ExtendedFABFromAddItemActivityAddItem.setOnClickListener {

            try{

                val type = mBinding.SpinnerFromAddItemActivityType.selectedItem.toString()

                if(type == getString(R.string.Spinner_fromAddItemActivity_Hint_ChooseTask)){
                    showSnackBar(getString(R.string.Message_Info_fromFragmentLifeStyleItems_ChooseTask))
                }else{

                    val title = mBinding.IncludeLayoutFromAddItemActivitySectionDetails.EditText_fromAddItemActivity_Name.text.toString()
                    val category = mBinding.IncludeLayoutFromAddItemActivitySectionDetails.Spinner_fromAddItemActivity_Category.selectedItem.toString()

                    if(category == getString(R.string.Spinner_fromAddItemActivity_Hint_ChooseCategory)){
                        showSnackBar(getString(R.string.Message_Info_fromFragmentLifeStyleItems_ChooseCategory))
                        return@setOnClickListener
                    }

                    val validator = Validation()
                    validator.checkIfEmpty(mBinding.IncludeLayoutFromAddItemActivitySectionDetails.EditText_fromAddItemActivity_Name)

                    when(Utils.formattedStringToLifestyleEnum(type)){
                        LifestyleItem.TO_DO -> {

                            if(validator.hasErrors()){
                                validator.errors.forEach{
                                    it.key.error = it.value
                                }

                                return@setOnClickListener
                            }

                            if (mViewModel.itemPriority == null) {
                                showSnackBar(getString(R.string.Message_Info_fromFragmentLifeStyleItems_ChoosePriority))
                                return@setOnClickListener
                            }

                            mViewModel.addToDo(title, category)
                        }

                        LifestyleItem.TO_BUY -> {
                            val price = mBinding.IncludeLayoutFromAddItemActivitySectionDetails.EditText_fromAddItemActivity_Price.text.toString().toDoubleOrNull()
                            val qty = mBinding.IncludeLayoutFromAddItemActivitySectionDetails.EditText_fromAddItemActivity_Quantity.text.toString().toIntOrNull()

                            validator.checkIfDoubleGreaterThanZero(mBinding.IncludeLayoutFromAddItemActivitySectionDetails.EditText_fromAddItemActivity_Price)
                            validator.checkIfIntegerGreaterThanZero(mBinding.IncludeLayoutFromAddItemActivitySectionDetails.EditText_fromAddItemActivity_Quantity)

                            if(validator.hasErrors()){
                                validator.errors.forEach{
                                    it.key.error = it.value
                                }

                                return@setOnClickListener
                            }

                            if (mViewModel.itemPriority == null) {
                                showSnackBar(getString(R.string.Message_Info_fromFragmentLifeStyleItems_ChoosePriority))
                                return@setOnClickListener
                            }

                            mViewModel.addToBuy(title, category, qty!!, price!!)
                        }

                        LifestyleItem.GOAL -> {

                            if(validator.hasErrors()){
                                validator.errors.forEach{
                                    it.key.error = it.value
                                }

                                return@setOnClickListener
                            }

                            mViewModel.addGoal(title, category)

                        }
                    }

                    validator.clear()

                }
            }catch (ex: Exception){
                showToast(getString(R.string.Message_Exception_fromActivityAddItem_ErrorWhileAddingTask, ex.message))
            }
        }

        mBinding.NestedScrollViewFromAddItemActivityMainContent.setOnScrollChangeListener { _, _, y, _, oldY ->
            if (oldY < y) {
                mBinding.ExtendedFABFromAddItemActivityAddItem.shrink(true)
            } else if (oldY > y) {
                mBinding.ExtendedFABFromAddItemActivityAddItem.extend(true)
            }
        }

        mBinding.IncludeLayoutFromAddItemActivitySectionCalendar.Switch_fromAddItemActivity_Calendar_Enable.setOnCheckedChangeListener { _, isChecked ->
            updateUI_Calendar(isChecked)
        }
    }

    fun setPriority(view: View) {
        //Update the view and determine whether a priority was selected or unselected
        val prioritySelected = updatePriorityViewAndDetermineIfPriorityHasBeenSelected(view)

        if (prioritySelected) {
            //Set the priority of the view model
            mViewModel.setPriority(Integer.parseInt(view.tag.toString()))
        } else {
            //Flag to set priority to null
            mViewModel.setPriority(0)
        }
    }


    /**
     * Private functions for internal use ONLY
     **/
    private fun initializeStatusBarColor() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)
    }

    private fun updatePriorityViewAndDetermineIfPriorityHasBeenSelected(view: View): Boolean {

        if (!mBinding.IncludeLayoutFromAddItemActivitySectionPriority.ImageButtonFromAddItemActivityIconPriority1.isVisible ||
            !mBinding.IncludeLayoutFromAddItemActivitySectionPriority.ImageButtonFromAddItemActivityIconPriority2.isVisible ||
            !mBinding.IncludeLayoutFromAddItemActivitySectionPriority.ImageButtonFromAddItemActivityIconPriority3.isVisible ||
            !mBinding.IncludeLayoutFromAddItemActivitySectionPriority.ImageButtonFromAddItemActivityIconPriority4.isVisible
        ) {

            //If any of these views are not visible, it means that a priority was already selected.
            //Therefore we need to reset all views to visible again.
            updateUI_Priority1Icon(true)
            updateUI_Priority2Icon(true)
            updateUI_Priority3Icon(true)
            updateUI_Priority4Icon(true)

            return false
        }

        when (view.id) {
            R.id.ImageButton_fromAddItemActivity_Icon_Priority1 -> {
                updateUI_Priority2Icon(false)
                updateUI_Priority3Icon(false)
                updateUI_Priority4Icon(false)
            }
            R.id.ImageButton_fromAddItemActivity_Icon_Priority2 -> {
                updateUI_Priority1Icon(false)
                updateUI_Priority3Icon(false)
                updateUI_Priority4Icon(false)
            }
            R.id.ImageButton_fromAddItemActivity_Icon_Priority3 -> {
                updateUI_Priority1Icon(false)
                updateUI_Priority2Icon(false)
                updateUI_Priority4Icon(false)
            }
            R.id.ImageButton_fromAddItemActivity_Icon_Priority4 -> {
                updateUI_Priority1Icon(false)
                updateUI_Priority2Icon(false)
                updateUI_Priority3Icon(false)
            }
            else -> {
                return false
            }
        }

        return true
    }

    private fun configureSpinners() {

        /*
         * Load Spinners
         */

        //Type Spinner
        val typeList = Utils.getLifestyleItemEnumsToFormattedString()
        val typeAdapter = SpinnerAdapter(this, R.layout.custom_text_spinner_default)

        typeAdapter.addAll(typeList)
        typeAdapter.add(getString(R.string.Spinner_fromAddItemActivity_Hint_ChooseTask))
        typeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        mBinding.SpinnerFromAddItemActivityType.adapter = typeAdapter
        mBinding.SpinnerFromAddItemActivityType.onItemSelectedListener = spinnerTypeListener
        mBinding.SpinnerFromAddItemActivityType.setSelection(typeAdapter.count)

        //Category Spinner
        val categoryList = resources.getStringArray(R.array.App_LifeStyle_Items_Categories)
        val categoryAdapter = SpinnerAdapter(this, R.layout.custom_text_spinner_default)

        categoryAdapter.addAll(categoryList.asList())
        categoryAdapter.add(getString(R.string.Spinner_fromAddItemActivity_Hint_ChooseCategory))
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        mBinding.IncludeLayoutFromAddItemActivitySectionDetails.Spinner_fromAddItemActivity_Category.adapter =
            categoryAdapter
        //mBinding.IncludeLayoutFromAddItemActivitySectionDetails.Spinner_fromAddItemActivity_Category.onItemSelectedListener = this
        mBinding.IncludeLayoutFromAddItemActivitySectionDetails.Spinner_fromAddItemActivity_Category.setSelection(
            categoryAdapter.count
        )

    }

    private fun updateUI_DetailsExtraFields(visible: Boolean) {
        setViewVisibility(mBinding.IncludeLayoutFromAddItemActivitySectionDetails.TextInputLayout_fromAddItemActivity_Details_Price, visible)
        setViewVisibility(mBinding.IncludeLayoutFromAddItemActivitySectionDetails.TextInputLayout_fromAddItemActivity_Details_Quantity, visible)
    }

    private fun updateUI_Priorities(enabled: Boolean) {
        setViewVisibility(mBinding.IncludeLayoutFromAddItemActivitySectionPriority.root, enabled)
    }

    private fun updateUI_Calendar(isVisible: Boolean) {

        setViewVisibility(mBinding.IncludeLayoutFromAddItemActivitySectionCalendar.TextInputLayout_fromAddItemActivity_Calendar_Date, isVisible)

        setViewVisibility(mBinding.IncludeLayoutFromAddItemActivitySectionCalendar.TextInputLayout_fromAddItemActivity_Calendar_Description, isVisible)

    }

    private fun updateUI_Priority1Icon(isVisible: Boolean){
        setViewVisibility(mBinding.IncludeLayoutFromAddItemActivitySectionPriority.ImageButtonFromAddItemActivityIconPriority1, isVisible)
    }

    private fun updateUI_Priority2Icon(isVisible: Boolean){
        setViewVisibility(mBinding.IncludeLayoutFromAddItemActivitySectionPriority.ImageButtonFromAddItemActivityIconPriority2, isVisible)
    }

    private fun updateUI_Priority3Icon(isVisible: Boolean){
        setViewVisibility(mBinding.IncludeLayoutFromAddItemActivitySectionPriority.ImageButtonFromAddItemActivityIconPriority3, isVisible)
    }

    private fun updateUI_Priority4Icon(isVisible: Boolean){
        setViewVisibility(mBinding.IncludeLayoutFromAddItemActivitySectionPriority.ImageButtonFromAddItemActivityIconPriority4, isVisible)
    }

    private fun setViewVisibility(v: View, isVisible: Boolean){
        v.visibility = if(isVisible) View.VISIBLE else View.GONE
    }

    private fun showSnackBar(message: String) {
        mBinding.CoordinatorLayoutFromAddItemActivityTopParent.snackBar(message)
    }

    private fun showToast(message: String) {
        this.showToast(message)
    }
}
