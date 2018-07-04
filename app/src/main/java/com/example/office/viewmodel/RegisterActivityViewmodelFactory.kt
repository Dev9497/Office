package com.example.office.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.office.interfaces.RegisterResults

class RegisterActivityViewmodelFactory(private val listener: RegisterResults): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        return RegisterActivityViewmodel(listener) as T
    }
}