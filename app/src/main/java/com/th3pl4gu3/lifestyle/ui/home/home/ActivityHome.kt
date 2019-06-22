package com.th3pl4gu3.lifestyle.ui.home.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.ui.utils.toast
import com.th3pl4gu3.lifestyle.databinding.ActivityHomeBinding
import com.th3pl4gu3.lifestyle.ui.add_item.ActivityAddItem


class ActivityHome : AppCompatActivity(){

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
            startActivity(Intent(this, ActivityAddItem::class.java))
            overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out)
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
                this.findNavController(R.id.Container_fromHomeActivity_BottomAppBarFragments).navigate(R.id.BottomAppBar_fromActivityHome_Drawer)
                true
            }

            R.id.BottomAppBar_fromHomeActivity_MenuMain_Search -> {
                this.toast("Not Implemented yet!")
                false
            }

            else -> true
        }
    }

}