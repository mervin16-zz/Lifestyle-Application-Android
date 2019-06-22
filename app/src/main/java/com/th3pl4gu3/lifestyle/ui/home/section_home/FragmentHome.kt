package com.th3pl4gu3.lifestyle.ui.home.section_home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.th3pl4gu3.lifestyle.databinding.FragmentHomeBinding
import com.th3pl4gu3.lifestyle.R

class FragmentHome : Fragment() {


    private lateinit var mBinding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        configureScreenAppearance()

        return mBinding.root
    }

    private fun configureScreenAppearance() {
        //Hide Top Bar
        requireActivity().findViewById<RelativeLayout>(R.id.RelativeLayout_fromHomeActivity_TopBar).visibility =
            View.GONE

        //Hide Fab
        requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached).hide()
    }

}
