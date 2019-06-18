package com.th3pl4gu3.lifestyle.ui.home.section_goal

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
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.databinding.FragmentBottomdialogGoalDetailsBinding
import com.th3pl4gu3.lifestyle.ui.utils.toast

class RoundedBottomSheetDialogFragmentForGoalDetails(private val goal: Goal, private val viewModelFactory: GoalViewModelFactory) : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Bind the view and assign to binding object
        val binding: FragmentBottomdialogGoalDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomdialog_goal_details, container, false)

        //Get the ViewModel of Goal
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(GoalViewModel::class.java)

        //Attach Goal object with variable in XML
        binding.myGoal = goal


        /**
         * Listeners for Action Buttons in the Lifestyle Item's details
         */
        binding.IncludeLayoutForLifestyleItemActionButtons.ButtonForLifestyleItemActionEdit.setOnClickListener{
            //Need to call intent for Editing Lifestyle items
            requireContext().toast("Edit Button not implemented yet...")
        }

        binding.IncludeLayoutForLifestyleItemActionButtons.ButtonForLifestyleItemActionDelete.setOnClickListener{
            //Deletes the item
            viewModel.markAsDeleted(goal)
            //Dismiss the dialog
            this.dismiss()
        }

        binding.IncludeLayoutForLifestyleItemActionButtons.ButtonForLifestyleItemActionDone.setOnClickListener{
            //Marks the item as either complete or incomplete
            viewModel.markItem(goal)
            //Dismiss the dialog
            this.dismiss()
        }

        return binding.root
    }
}