package com.example.office.viewmodel

import android.arch.lifecycle.ViewModel
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.office.interfaces.LoginResults
import com.example.office.model.OfficeUser

class MainActivityViewModel(private val listener: LoginResults):ViewModel() {

    private val officeUser: OfficeUser = OfficeUser(-1,"","","")

    //create function to set Email after user finishes entering text
    val emailTextWatcher: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                officeUser.email = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }

    //create function to set Password after user finishes entering text
    val passwordTextWatcher: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                officeUser.password = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
    fun onLoginClicked(v: View){
        listener.hideKeyboard()
        val loginCode: Boolean = listener.isDataValid()
        when (loginCode) {
            true -> listener.onSuccess("Login Success")
            false -> listener.onError("Username or password is incorrect")

        }
    }
    fun onRegisterClicked(v:View){
        listener.hideKeyboard()
        listener.goToRegister()
    }
}