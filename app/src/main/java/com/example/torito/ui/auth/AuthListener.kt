package com.example.torito.ui.auth

import androidx.lifecycle.LiveData

interface AuthListener {
    fun onStarted()
    fun onSuccess(loginResponse: LiveData<String>)
    fun onFailure(message: String)
}