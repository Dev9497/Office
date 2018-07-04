package com.example.office.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.office.interfaces.DisplayClientDB

class HelloUserViewmodelFactory(private val listener: DisplayClientDB): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HelloUserViewmodel(listener) as T
    }
}