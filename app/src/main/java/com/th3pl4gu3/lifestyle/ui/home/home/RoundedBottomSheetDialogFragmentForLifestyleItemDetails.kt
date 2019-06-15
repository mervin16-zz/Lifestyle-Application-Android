package com.th3pl4gu3.lifestyle.ui.home.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.Lifestyle
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.databinding.FragmentBottomdialogGoalDetailsBinding
import com.th3pl4gu3.lifestyle.databinding.FragmentBottomdialogTobuyDetailsBinding
import com.th3pl4gu3.lifestyle.databinding.FragmentBottomdialogTodoDetailsBinding

class RoundedBottomSheetDialogFragmentForLifestyleItemDetails(private val lifestyle: Lifestyle) : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: ViewDataBinding

        when(lifestyle.type){
            LifestyleItem.GOAL.value -> {
                binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomdialog_goal_details, container, false)
                (binding as FragmentBottomdialogGoalDetailsBinding).myGoal = (lifestyle as Goal)
            }
            LifestyleItem.TO_BUY.value -> {
                binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomdialog_tobuy_details, container, false)
                (binding as FragmentBottomdialogTobuyDetailsBinding).myToBuy = (lifestyle as ToBuy)
            }
            LifestyleItem.TO_DO.value -> {
                binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomdialog_todo_details, container, false)
                (binding as FragmentBottomdialogTodoDetailsBinding).myToDo = (lifestyle as ToDo)
            }
            else -> {
                binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomdialog_todo_details, container, false)
                (binding as FragmentBottomdialogTodoDetailsBinding).myToDo = (lifestyle as ToDo)
            }
        }

        return binding.root
    }
}