package com.example.torito.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.torito.OnBoardingActivity
import com.example.torito.R
import com.example.torito.databinding.ActivityLoginBinding
import com.example.torito.util.hide
import com.example.torito.util.show
import com.example.torito.util.toast
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), AuthListener {

    //App Activity
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

        val binding : ActivityLoginBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_login)

        val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

        //setContentView(R.layout.activity_login)

        tvGoToRegister.setOnClickListener {
            Toast.makeText(this, getString(R.string.please_fill_your_register), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    //AuthListener Interface
    override fun onStarted() {

        //to util.ViewUtils call
        progress_bar.show()
    }

    override fun onSuccess(loginResponse: LiveData<String>) {
        loginResponse.observe(this, Observer {
            progress_bar.hide()
            toast(it)
        })
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        toast(message)
    }
}