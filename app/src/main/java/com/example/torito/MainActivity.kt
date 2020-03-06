package com.example.torito

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences =
            getSharedPreferences("my_preferences", MODE_PRIVATE)

        if(!preferences.getBoolean("onBoarding_complete",false)) {

            val onBoardActivity = Intent(this, OnBoardingActivity::class.java)
            startActivity(onBoardActivity)
            finish()
            return
        }

        // Set Full Screen Main Activity
        /*window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN) */

        setContentView(R.layout.activity_main)

        tvGoToRegister.setOnClickListener {
            Toast.makeText(this, getString(R.string.please_fill_your_register), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}