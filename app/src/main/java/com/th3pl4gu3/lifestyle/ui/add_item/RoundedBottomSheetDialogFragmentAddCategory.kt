package com.th3pl4gu3.lifestyle.ui.add_item

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.databinding.FragmentBottomdialogAddCategoryBinding
import com.th3pl4gu3.lifestyle.ui.utils.SharedPrefUtils
import com.th3pl4gu3.lifestyle.ui.utils.Validation
import com.th3pl4gu3.lifestyle.ui.utils.toast

class RoundedBottomSheetDialogFragmentAddCategory : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentBottomdialogAddCategoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bottomdialog_add_category, container, false)

        binding.ButtonFromFragmentBottomSheetAddCategorySave.setOnClickListener {
            try {
                val category = binding.EditTextFromFragmentBottomSheetAddCategoryCategory
                val validator = Validation()

                validator.checkIfEmpty(category)

                if(validator.hasErrors()){
                    validator.errors.forEach{
                        it.key.error = it.value
                    }
                }else{
                    if (saveCategory(category.text.toString())) {
                        toast(getString(R.string.Message_Success_fromAddCategory_CategoryAdded, category.text.toString()))
                        dismiss()
                    } else {
                        toast(getString(R.string.Message_Info_fromAddCategory_CategoryAlreadyPresent))
                    }
                }

            } catch (ex: Exception) {
                toast(
                    getString(
                        R.string.Message_Exception_fromAddCategoryFragment_ErrorWhileAddingCategory,
                        ex.message.toString()
                    )
                )
                dismiss()
            }
        }

        return binding.root
    }

    private fun saveCategory(category: String): Boolean {
        return SharedPrefUtils(
            requireContext(),
            getString(R.string.ValuePair_forCategories_Name_Categories)
        ).updateCategories(category)
    }

    private fun toast(message: String) {
        requireContext().toast(message)
    }
}