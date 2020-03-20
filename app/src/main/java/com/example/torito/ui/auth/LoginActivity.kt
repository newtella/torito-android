package com.example.torito.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.torito.MapsActivity
import com.example.torito.OnBoardingActivity
import com.example.torito.R
import com.example.torito.data.db.AppDatabase
import com.example.torito.data.db.entities.User
import com.example.torito.data.network.NetworkConnectionInterceptor
import com.example.torito.data.network.ToritoApi
import com.example.torito.data.repositories.UserRepository
import com.example.torito.databinding.ActivityLoginBinding
import com.example.torito.util.hide
import com.example.torito.util.show
import com.example.torito.util.snackbar
import com.example.torito.util.toast
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), AuthListener {

    //App Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = ToritoApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)

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

        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
             if (user != null){
                 Intent(this, MapsActivity::class.java).also {
                     it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                     startActivity(it)
                 }
            }
        })

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

    override fun onSuccess(user: User) {
            progress_bar.hide()
        toast("${user.name} ha iniciado sesion")
        //root_layout.snackbar("${user.name} is logged in")
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}