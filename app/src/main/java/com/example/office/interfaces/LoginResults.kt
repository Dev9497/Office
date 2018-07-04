package com.example.office.interfaces

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.office.model.OfficeUser

interface LoginResults {
    fun isDataValid(): Boolean
    fun onSuccess(message: String)
    fun onError(message: String)
    fun goToRegister()
    fun hideKeyboard()
}