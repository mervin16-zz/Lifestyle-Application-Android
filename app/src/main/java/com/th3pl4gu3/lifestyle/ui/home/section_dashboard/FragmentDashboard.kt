package com.th3pl4gu3.lifestyle.ui.home.section_dashboard

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.th3pl4gu3.lifestyle.databinding.FragmentDashboardBinding
import android.util.DisplayMetrics
import android.util.Log
import androidx.core.content.ContextCompat
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.ui.utils.toast
import kotlin.math.roundToInt




class FragmentDashboard : Fragment() {

    private lateinit var mBinding: FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

        configureScreenAppearance()

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()

        changeStatusBarColorToAccent()
    }

    override fun onPause() {
        super.onPause()

        changeStatusBarColorToDefault()
    }

    /**
     * Private functions for internal use ONLY
     **/
    private fun changeStatusBarColorToAccent() {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorAccent)
    }

    private fun changeStatusBarColorToDefault() {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
    }

    private fun configureScreenAppearance() {
        //Hide Top Bar
        requireActivity().findViewById<RelativeLayout>(R.id.RelativeLayout_fromHomeActivity_TopBar).visibility =
            View.GONE

        //Hide Fab
        requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached).hide()
    }
}
