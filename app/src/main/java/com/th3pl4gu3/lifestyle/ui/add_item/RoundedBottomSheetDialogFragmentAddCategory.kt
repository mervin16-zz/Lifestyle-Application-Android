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
import com.th3pl4gu3.lifestyle.databinding.FragmentBottomdialogFilterBinding
import com.th3pl4gu3.lifestyle.ui.utils.SharedPrefUtils
import com.th3pl4gu3.lifestyle.ui.utils.toast

class RoundedBottomSheetDialogFragmentAddCategory : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    private lateinit var mBinding: FragmentBottomdialogAddCategoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomdialog_add_category, container, false)

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()

        mBinding.materialIconButton.setOnClickListener{
            try{
                if(saveCategory(mBinding.categoryput.text.toString())){
                    requireContext().toast("Category saved successfully")
                    dismiss()
                }else{
                    requireContext().toast("This category exists already")
                }
            }catch (ex: Exception){
                requireContext().toast(ex.message.toString())
                dismiss()
            }
        }
    }

    private fun saveCategory(category: String): Boolean {
        return SharedPrefUtils(requireContext(), getString(R.string.ValuePair_forCategories_Name_Categories)).updateCategories(category)
    }
}