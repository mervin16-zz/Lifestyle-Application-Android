package com.th3pl4gu3.lifestyle.ui.home.section_dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.th3pl4gu3.lifestyle.databinding.FragmentDashboardBinding
import android.view.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.utils.toast


class FragmentDashboard : Fragment() {

    private lateinit var _viewModel: DashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding: FragmentDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

        //Set has menu to true to access BottomAppBar's menu item here
        setHasOptionsMenu(true)

        //Configures the screen views (Eg. Title, appearance of top bar etc...)
        configureScreenAppearance()

        //Get the activity's application
        val application = requireNotNull(this.activity).application

        //Fetch the database
        val dataSource = LifestyleDatabase.getInstance(application)

        //Fetch the ViewModel of this fragment
        _viewModel = ViewModelProviders.of(this, DashboardViewModelFactory(dataSource, application)).get(DashboardViewModel::class.java)

        //Bind view model with layout
        binding.viewModel = _viewModel

        //Instantiate the lifecycle owner of this fragment
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        changeStatusBarColorToAccent()
    }

    override fun onPause() {
        super.onPause()

        changeStatusBarColorToDefault()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.BottomAppBar_fromHomeActivity_MenuMain_Sort).isVisible = false
        menu.findItem(R.id.BottomAppBar_fromHomeActivity_MenuMain_Filter).isVisible = false
        menu.findItem(R.id.BottomAppBar_fromHomeActivity_MenuMain_Search).isVisible = true
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
