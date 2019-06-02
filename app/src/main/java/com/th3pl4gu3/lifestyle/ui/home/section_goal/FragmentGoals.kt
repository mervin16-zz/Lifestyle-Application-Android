package com.th3pl4gu3.lifestyle.ui.home.section_goal


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.databinding.FragmentGoalsBinding

class FragmentGoals : Fragment() {

    private lateinit var mBinding: FragmentGoalsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_goals, container, false)

        configureScreenAppearance()

        return mBinding.root
    }

    private fun configureScreenAppearance(){
        //Set title of fragment
        val screenTitle = requireActivity().findViewById<TextView>(R.id.TextView_fromHomeActivity_Screen_Title)
        screenTitle.text = getString(R.string.TextView_fromFragmentInHomeActivity_ScreenTitle_Goals)

        //Show Top Bar
        val topBar = requireActivity().findViewById<RelativeLayout>(R.id.RelativeLayout_fromHomeActivity_TopBar)
        topBar.visibility = View.VISIBLE

        //Show Toggle Buttons
        val toggleButtonView = requireActivity().findViewById<LinearLayout>(R.id.LinearLayout_fromHomeActivity_ToggleButton)
        toggleButtonView.visibility = View.VISIBLE

        //Show Fab
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.FAB_fromHomeActivity_BottomAppBarAttached)
        fab.show()
    }
}
