package com.th3pl4gu3.lifestyle.ui.add_item

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.databinding.DataBindingUtil
import com.th3pl4gu3.lifestyle.R
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.databinding.ActivityAddItemBinding
import com.th3pl4gu3.lifestyle.ui.SpinnerAdapter
import com.th3pl4gu3.lifestyle.ui.utils.SharedPrefUtils
import com.th3pl4gu3.lifestyle.ui.utils.Validation
import com.th3pl4gu3.lifestyle.ui.utils.snackBar
import com.th3pl4gu3.lifestyle.ui.utils.toast
import kotlinx.android.synthetic.main.include_foradditem_section_calendar.view.*
import kotlinx.android.synthetic.main.include_foradditem_section_details.view.*


class ActivityAddItem : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var _binding: ActivityAddItemBinding
    private lateinit var _viewModel: AddItemViewModel

    private val spinnerTypeListener = object : OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {return}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            try{
                val selectedItem = parent?.getItemAtPosition(position).toString()

                if (selectedItem == getString(R.string.Spinner_fromAddItemActivity_Hint_ChooseTask)) return

                _viewModel.handleLifestyleItemSelections(selectedItem)

            }catch (ex: Exception){
                toast(getString(R.string.Message_Exception_fromActivityAddItem_ErrorWhileFetchingTask, ex.message))
            }
        }
    }

    private lateinit var spinnerCategoryAdapter: SpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get the binding
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_add_item)

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

        _viewModel = viewModel

        _binding.viewModel = viewModel

        _binding.lifecycleOwner = this

        // Add an Observer on the state variable for showing a Snackbar message
        _viewModel.showSnackBarEvent.observe(this, Observer { message ->
            if (message != null) {
                // Observed state is true.
                snackBar(message)
                // Reset state to make sure the snackbar is only shown once, even if the device
                // has a configuration change.
                viewModel.doneShowingSnackbar()
            }
        })
    }

    override fun onStart() {
        super.onStart()

        _binding.ImageButtonIconBack.setOnClickListener {
            onBackPressed()
        }

        _binding.ExtendedFABFromAddItemActivityAddItem.setOnClickListener {

            try{

                val type = _binding.SpinnerFromAddItemActivityType.selectedItem.toString()

                if(type == getString(R.string.Spinner_fromAddItemActivity_Hint_ChooseTask)){
                    snackBar(getString(R.string.Message_Info_fromFragmentLifeStyleItems_ChooseTask))
                }else{

                    val title = _binding.IncludeLayoutFromAddItemActivitySectionDetails.root.EditText_fromAddItemActivity_Name.text.toString()
                    val category = _binding.IncludeLayoutFromAddItemActivitySectionDetails.root.Spinner_fromAddItemActivity_Category.selectedItem.toString()

                    if(category == getString(R.string.Spinner_fromAddItemActivity_Hint_ChooseCategory)){
                        snackBar(getString(R.string.Message_Info_fromFragmentLifeStyleItems_ChooseCategory))
                        return@setOnClickListener
                    }

                    val validator = Validation()
                    validator.checkIfEmpty(_binding.IncludeLayoutFromAddItemActivitySectionDetails.root.EditText_fromAddItemActivity_Name)

                    when(Utils.formattedStringToLifestyleEnum(type)){
                        LifestyleItem.TO_DO -> {

                            if(validator.hasErrors()){
                                validator.errors.forEach{
                                    it.key.error = it.value
                                }

                                return@setOnClickListener
                            }

                            if (_viewModel.itemPriority == null) {
                                snackBar(getString(R.string.Message_Info_fromFragmentLifeStyleItems_ChoosePriority))
                                return@setOnClickListener
                            }

                            _viewModel.addToDo(title, category)
                        }

                        LifestyleItem.TO_BUY -> {
                            val price = _binding.IncludeLayoutFromAddItemActivitySectionDetails.root.EditText_fromAddItemActivity_Price.text.toString().toDoubleOrNull()
                            val qty = _binding.IncludeLayoutFromAddItemActivitySectionDetails.root.EditText_fromAddItemActivity_Quantity.text.toString().toIntOrNull()

                            validator.checkIfDoubleGreaterThanZero(_binding.IncludeLayoutFromAddItemActivitySectionDetails.root.EditText_fromAddItemActivity_Price)
                            validator.checkIfIntegerGreaterThanZero(_binding.IncludeLayoutFromAddItemActivitySectionDetails.root.EditText_fromAddItemActivity_Quantity)

                            if(validator.hasErrors()){
                                validator.errors.forEach{
                                    it.key.error = it.value
                                }

                                return@setOnClickListener
                            }

                            if (_viewModel.itemPriority == null) {
                                snackBar(getString(R.string.Message_Info_fromFragmentLifeStyleItems_ChoosePriority))
                                return@setOnClickListener
                            }

                            _viewModel.addToBuy(title, category, qty!!, price!!)
                        }

                        LifestyleItem.GOAL -> {

                            if(validator.hasErrors()){
                                validator.errors.forEach{
                                    it.key.error = it.value
                                }

                                return@setOnClickListener
                            }

                            _viewModel.addGoal(title, category)

                        }
                    }

                    validator.clear()

                }
            }catch (ex: Exception){
                toast(getString(R.string.Message_Exception_fromActivityAddItem_ErrorWhileAddingTask, ex.message))
            }
        }

        _binding.NestedScrollViewFromAddItemActivityMainContent.setOnScrollChangeListener { _, _, y, _, oldY ->
            if (oldY < y) {
                _binding.ExtendedFABFromAddItemActivityAddItem.shrink(true)
            } else if (oldY > y) {
                _binding.ExtendedFABFromAddItemActivityAddItem.extend(true)
            }
        }

        _binding.IncludeLayoutFromAddItemActivitySectionCalendar.root.Switch_fromAddItemActivity_Calendar_Enable.setOnCheckedChangeListener { _, isChecked ->
            _viewModel.calendarSection.value = isChecked
        }

        _binding.IncludeLayoutFromAddItemActivitySectionDetails.root.ImageButton_fromAddItemActivity_Icon_AddCategory.setOnClickListener {
            val bottomAppBarDialog = RoundedBottomSheetDialogFragmentAddCategory()
            bottomAppBarDialog.show(supportFragmentManager, bottomAppBarDialog.tag)
        }
    }

    override fun onResume() {
        super.onResume()

        this.getSharedPreferences(getString(R.string.ValuePair_forCategories_Name_Categories), Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()

        this.getSharedPreferences(getString(R.string.ValuePair_forCategories_Name_Categories), Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        //Category Spinner
        spinnerCategoryAdapter = SpinnerAdapter(this, R.layout.custom_text_spinner_default)

        spinnerCategoryAdapter.addAll(loadCategories())
        spinnerCategoryAdapter.add(getString(R.string.Spinner_fromAddItemActivity_Hint_ChooseCategory))
        spinnerCategoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        _binding.IncludeLayoutFromAddItemActivitySectionDetails.root.Spinner_fromAddItemActivity_Category.adapter =
            spinnerCategoryAdapter
        _binding.IncludeLayoutFromAddItemActivitySectionDetails.root.Spinner_fromAddItemActivity_Category.setSelection(
            spinnerCategoryAdapter.count
        )
    }

    /**
     * Private functions for internal use ONLY
     **/
    private fun initializeStatusBarColor() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)
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
        _binding.SpinnerFromAddItemActivityType.adapter = typeAdapter
        _binding.SpinnerFromAddItemActivityType.onItemSelectedListener = spinnerTypeListener
        _binding.SpinnerFromAddItemActivityType.setSelection(typeAdapter.count)

        //Category Spinner
        spinnerCategoryAdapter = SpinnerAdapter(this, R.layout.custom_text_spinner_default)

        spinnerCategoryAdapter.addAll(loadCategories())
        spinnerCategoryAdapter.add(getString(R.string.Spinner_fromAddItemActivity_Hint_ChooseCategory))
        spinnerCategoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        _binding.IncludeLayoutFromAddItemActivitySectionDetails.root.Spinner_fromAddItemActivity_Category.adapter =
            spinnerCategoryAdapter
        _binding.IncludeLayoutFromAddItemActivitySectionDetails.root.Spinner_fromAddItemActivity_Category.setSelection(
            spinnerCategoryAdapter.count
        )

    }

    private fun loadCategories(): List<String> {
        return ArrayList(SharedPrefUtils(this, getString(R.string.ValuePair_forCategories_Name_Categories)).getCategories())
    }

    private fun snackBar(message: String) {
        _binding.CoordinatorLayoutFromAddItemActivityTopParent.snackBar(message)
    }
}
