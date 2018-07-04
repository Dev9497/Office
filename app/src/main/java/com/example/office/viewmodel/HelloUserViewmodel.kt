package com.example.office.viewmodel

import android.arch.lifecycle.ViewModel
import android.view.View
import com.example.office.interfaces.DisplayClientDB

class HelloUserViewmodel(private val listener: DisplayClientDB): ViewModel() {
    fun onClientClicked(v:View){
        listener.goToClientDB()
    }
    fun onLogoutClicked(v:View){
        listener.goToLoginScreen()
    }
}