package com.example.office.viewmodel

import android.arch.lifecycle.ViewModel
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.office.interfaces.RegisterResults
import com.example.office.model.OfficeUser

class RegisterActivityViewmodel(private val listener: RegisterResults):ViewModel() {
    private val newOfficeUser: OfficeUser = OfficeUser(-1,"","","")

    //create function to set Email after user finishes entering text
    val newEmailTextWatcher: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                newOfficeUser.email = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }

    //create function to set Password after user finishes entering text
    val newPasswordTextWatcher: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                newOfficeUser.password = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }

    val confirmPasswordTextWatcher: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(newOfficeUser.password != s.toString())
                    listener.noMatchPassword()
                else
                    listener.matchPassword()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
    val newNameTextWatcher: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                newOfficeUser.name = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
    fun onSaveClicked(v: View){
        listener.hideKeyboard()
        val passwordEqual = listener.cmpPassword()
        if(passwordEqual)
            listener.saveNewUser()
        else
            listener.displayErrorToast()
    }
    fun onBackClicked(v:View){
        listener.hideKeyboard()
        listener.goToLogin()
    }
}