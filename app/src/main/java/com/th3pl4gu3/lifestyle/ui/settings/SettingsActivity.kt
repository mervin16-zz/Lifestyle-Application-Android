package com.th3pl4gu3.lifestyle.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity(){


    private lateinit var _binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)


    }

    override fun onStart() {
        super.onStart()

        _binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}
