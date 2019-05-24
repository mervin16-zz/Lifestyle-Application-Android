package com.th3pl4gu3.lifestyle.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.databinding.ActivityHomeBinding


class ActivityHome : AppCompatActivity() {

    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        //Set the bottom bar as SupportActionBar
        setSupportActionBar(mBinding.BottomAppBarFromHomeActivityMain)
    }

    override fun onStart() {
        super.onStart()

        //Configure FAB
        mBinding.FABFromHomeActivityBottomAppBarAttached.setOnClickListener{
            Toast.makeText(this, "Not Implemented yet!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_bottomappbar_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val bottomNavDrawerFragment = RoundedBottomSheetDialogFragment()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
                true
            }
            R.id.BottomAppBar_fromHomeActivity_MenuMain_Search -> {
                Toast.makeText(this, "Not Implemented yet!", Toast.LENGTH_SHORT).show()
                false
            }

            else -> true
        }
    }

}
