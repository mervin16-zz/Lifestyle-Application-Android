package com.th3pl4gu3.lifestyle.ui.home.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.databinding.FragmentBottomappbarDrawerBinding
import com.th3pl4gu3.lifestyle.ui.settings.SettingsActivity

class RoundedBottomSheetDialogFragmentForBottomAppBarDrawer : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    private lateinit var mBinding: FragmentBottomappbarDrawerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomappbar_drawer, container, false)

        mBinding.BottomAppBarFromFragmentBottomAppBarDrawerDrawerSettingsIcon.setOnClickListener {
            this.dismiss()
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Navigation Component
        tieNavigationToBottomSheetDialog()
    }

    private fun tieNavigationToBottomSheetDialog(){
        val navController = Navigation.findNavController(requireActivity(), R.id.Container_fromHomeActivity_BottomAppBarFragments)
        mBinding.BottomAppBarFromFragmentBottomAppBarDrawerDrawerNavigationView.setupWithNavController(navController)
    }
}