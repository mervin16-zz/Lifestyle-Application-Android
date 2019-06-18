package com.th3pl4gu3.lifestyle.ui.home.section_tobuy

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.databinding.FragmentBottomdialogTobuyDetailsBinding
import com.th3pl4gu3.lifestyle.ui.utils.toast

class RoundedBottomSheetDialogFragmentForToBuyDetails(private val toBuy: ToBuy, private val viewModelFactory: ToBuyViewModelFactory) : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Bind the view and assign to binding object
        val binding: FragmentBottomdialogTobuyDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomdialog_tobuy_details, container, false)

        //Get the ViewModel of To Buy
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(ToBuyViewModel::class.java)

        //Attach To Buy object with variable in XML
        binding.myToBuy = toBuy


        /**
         * Listeners for Action Buttons in the Lifestyle Item's details
         */
        binding.IncludeLayoutForLifestyleItemActionButtons.ButtonForLifestyleItemActionEdit.setOnClickListener{
            //Need to call intent for Editing Lifestyle items
            requireContext().toast("Edit Button not implemented yet...")
        }

        binding.IncludeLayoutForLifestyleItemActionButtons.ButtonForLifestyleItemActionDelete.setOnClickListener{
            //Deletes the item
            viewModel.markAsDeleted(toBuy)
            //Dismiss the dialog
            this.dismiss()
        }

        binding.IncludeLayoutForLifestyleItemActionButtons.ButtonForLifestyleItemActionDone.setOnClickListener{
            //Marks the item as either complete or incomplete
            viewModel.markItem(toBuy)
            //Dismiss the dialog
            this.dismiss()
        }

        return binding.root
    }
}