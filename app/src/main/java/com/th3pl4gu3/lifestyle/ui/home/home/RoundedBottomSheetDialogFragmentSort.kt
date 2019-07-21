package com.th3pl4gu3.lifestyle.ui.home.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder.*
import com.th3pl4gu3.lifestyle.core.enums.SortingValue.*
import com.th3pl4gu3.lifestyle.core.lifestyle.Lifestyle
import com.th3pl4gu3.lifestyle.core.tuning.Sort
import com.th3pl4gu3.lifestyle.databinding.FragmentBottomdialogSortBinding
import com.th3pl4gu3.lifestyle.ui.utils.SharedPrefUtils

class RoundedBottomSheetDialogFragmentSort : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    private var currentCheckedSortValue = View.NO_ID
    private lateinit var _sharedPreferenceName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentBottomdialogSortBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomdialog_sort, container, false)

        if(arguments!=null){

            when(arguments?.getInt(getString(R.string.ValuePair_forDataPassing_LifestyleItem_Value))){
                LifestyleItem.GOAL.value -> {
                    binding.ChipPriority.visibility = View.GONE
                    _sharedPreferenceName = getString(R.string.ValuePair_forSortPreferences_Name_Goals_SortingPreferences)
                }
                LifestyleItem.TO_DO.value -> {
                    _sharedPreferenceName = getString(R.string.ValuePair_forSortPreferences_Name_ToDo_SortingPreferences)
                }
                LifestyleItem.TO_BUY.value -> {
                    _sharedPreferenceName = getString(R.string.ValuePair_forSortPreferences_Name_ToBuy_SortingPreferences)
                }
            }

        }

        forceSingleChipSelection(binding)

        restoreSortUI(binding)

        binding.thisFragment = this

        return binding.root
    }


    /**
     * Public functions that are accessible from the outside
     **/

    fun sortValueClick(view: View){
        updateValue((view as Chip).tag.toString())
    }

    fun sortOrderClick(view: View){
        updateOrder((view as MaterialButton).tag.toString())
    }


    /**
     * Private functions for internal use ONLY
     **/

    private fun forceSingleChipSelection(binding: FragmentBottomdialogSortBinding){
        binding.BottomSheetDialogFromFragmentBottomSheetSortSortChipGroup.setOnCheckedChangeListener { group, checkedId ->

            if (checkedId == View.NO_ID) {
                group.check(currentCheckedSortValue)
                return@setOnCheckedChangeListener
            }

            currentCheckedSortValue = checkedId
        }
    }

    private fun restoreSortUI(binding: FragmentBottomdialogSortBinding){
        val sort = getPreferences()

        when(sort.order){
            ASC -> {binding.BottomSheetDialogFromFragmentBottomSheetSortSortToggleButtonGroup.check(R.id.BottomSheetDialog_fromFragmentBottomSheetSort_Sort_ToggleButtonGroup_Ascending)}
            DESC -> {binding.BottomSheetDialogFromFragmentBottomSheetSortSortToggleButtonGroup.check(R.id.BottomSheetDialog_fromFragmentBottomSheetSort_Sort_ToggleButtonGroup_Descending)}
        }

        when(sort.value){
            TITLE -> {binding.BottomSheetDialogFromFragmentBottomSheetSortSortChipGroup.check(R.id.Chip_Title)}
            DATE_ADDED -> {binding.BottomSheetDialogFromFragmentBottomSheetSortSortChipGroup.check(R.id.Chip_DateAdded)}
            DATE_COMPLETED -> {binding.BottomSheetDialogFromFragmentBottomSheetSortSortChipGroup.check(R.id.Chip_DateCompleted)}
            DAYS_ALIVE -> {binding.BottomSheetDialogFromFragmentBottomSheetSortSortChipGroup.check(R.id.Chip_DaysAlive)}
            CATEGORY -> {binding.BottomSheetDialogFromFragmentBottomSheetSortSortChipGroup.check(R.id.Chip_Category)}
            PRIORITY -> {binding.BottomSheetDialogFromFragmentBottomSheetSortSortChipGroup.check(R.id.Chip_Priority)}
        }
    }

    private fun updateValue(value: String) {
        SharedPrefUtils(requireContext(), _sharedPreferenceName).updateSortValue(value)

    }

    private fun updateOrder(order: String) {
        SharedPrefUtils(requireContext(), _sharedPreferenceName).updateSortOrder(order)
    }

    private fun getPreferences(): Sort<Lifestyle> {
        return SharedPrefUtils(requireContext(), _sharedPreferenceName).getSort()
    }
}