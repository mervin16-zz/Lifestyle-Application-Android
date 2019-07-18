package com.th3pl4gu3.lifestyle.ui.home.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.databinding.FragmentBottomdialogSortBinding

class RoundedBottomSheetDialogFragmentSort : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    private lateinit var _binding: FragmentBottomdialogSortBinding

    private var currentCheckedChipInSorting = View.NO_ID

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomdialog_sort, container, false)

        forceSingleSelectionOnViewGroups()
        return _binding.root
    }

    private fun forceSingleSelectionOnViewGroups() {
        _binding.BottomSheetDialogFromFragmentBottomSheetSortSortChipGroup.setOnCheckedChangeListener{group, checkedId ->
            if(checkedId == View.NO_ID){
                group.check(currentCheckedChipInSorting)
                return@setOnCheckedChangeListener
            }

            currentCheckedChipInSorting = checkedId
        }
    }
}