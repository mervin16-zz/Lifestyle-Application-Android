package com.th3pl4gu3.lifestyle.ui.home.section_todo

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
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.databinding.FragmentBottomdialogTodoDetailsBinding
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel
import com.th3pl4gu3.lifestyle.ui.utils.toast

class RoundedBottomSheetDialogFragmentForToDoDetails : BottomSheetDialogFragment() {

    private lateinit var _viewModel: LifestyleOpsViewModel

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Bind the view and assign to binding object
        val binding: FragmentBottomdialogTodoDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomdialog_todo_details, container, false)

        //Get the activity's application
        val application = requireActivity().application

        //Fetch the database
        val dataSource = LifestyleDatabase.getInstance(application)

        //Instantiate the view model of this fragment
        _viewModel = ViewModelProviders.of(this, ToDoViewModelFactory(dataSource, application)).get(LifestyleOpsViewModel::class.java)

        //Get the lifestyle item and cast as To Do object
        val toDo = requireArguments().get(getString(R.string.ValuePair_forDataPassing_LifestyleItem)) as ToDo

        //Attach To Do object with variable in XML
        binding.myToDo = toDo


        /**
         * Listeners for Action Buttons in the Lifestyle Item's details
         */
        binding.IncludeLayoutForLifestyleItemActionButtons.ButtonForLifestyleItemActionEdit.setOnClickListener{
            //Need to call intent for Editing Lifestyle items
            requireContext().toast("Edit Button not implemented yet...")
        }

        binding.IncludeLayoutForLifestyleItemActionButtons.ButtonForLifestyleItemActionDelete.setOnClickListener{
            //Deletes the item
            _viewModel.markAsDeleted(toDo)
            //Dismiss the dialog
            this.dismiss()
        }

        binding.IncludeLayoutForLifestyleItemActionButtons.ButtonForLifestyleItemActionDone.setOnClickListener{
            //Marks the item as either complete or incomplete
            _viewModel.markItem(toDo)
            //Dismiss the dialog
            this.dismiss()
        }

        return binding.root
    }
}