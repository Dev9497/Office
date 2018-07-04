package com.example.office.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.office.interfaces.LoginResults

class MainActivityViewmodelFactory(private val listener: LoginResults): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel?> create (modelClass: Class<T>): T{
        return MainActivityViewModel(listener) as T
    }
}