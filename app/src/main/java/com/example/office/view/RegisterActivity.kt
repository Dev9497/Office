package com.example.office.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.office.R
import com.example.office.databinding.ActivityRegisterBinding
import com.example.office.interfaces.RegisterResults
import com.example.office.model.OfficeUser
import com.example.office.sql.DatabaseHelper
import com.example.office.viewmodel.RegisterActivityViewmodel
import com.example.office.viewmodel.RegisterActivityViewmodelFactory
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterResults {
    override fun hideKeyboard() {
        val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
    }

    override fun goToLogin() {
        startActivity(Intent(this,MainActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
    }

    override fun displayErrorToast() {
        Toast.makeText(this,"Cannot register as passwords don't match",Toast.LENGTH_SHORT).show()
    }

    override fun cmpPassword(): Boolean {
        if(tiet_new_password.text.toString() == tiet_confirm_password.text.toString())
            return true
        return false
    }

    private val newOfficeUser: OfficeUser = OfficeUser(-1,"","","")
    override fun matchPassword() {
        til_confirm_password.error = null
    }

    private lateinit var databaseHelper : DatabaseHelper

    private fun checkIfEmpty(): Boolean{
        if(tiet_new_name.text.toString().trim() == "" || tiet_new_email.editText!!.toString().trim() == "" || tiet_new_password.text.toString() == ""){
            Toast.makeText(this,"You cannot leave any fields empty during registration!",Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun checkIfAlreadyExists(): Boolean{
        return (databaseHelper.checkUser(tiet_new_email.editText!!.text.toString().trim()))
    }

    override fun saveNewUser() {
        if(!checkIfEmpty()){
            if(checkIfAlreadyExists())
                Toast.makeText(this,"User with the email already exists!",Toast.LENGTH_SHORT).show()
            else{
                newOfficeUser.name = tiet_new_name.text.toString().trim()
                newOfficeUser.email = tiet_new_email.editText!!.text.toString().trim()
                newOfficeUser.password = tiet_new_password.text.toString().trim()
                databaseHelper.addUser(newOfficeUser)
                Toast.makeText(this,"Registered Successfully! Login to continue.",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }

    override fun noMatchPassword() {
        til_confirm_password.isErrorEnabled = true
        til_confirm_password.error = "Passwords do not match. Try again!"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)

        databaseHelper = DatabaseHelper(this)

        val activityRegisterBinding = DataBindingUtil.setContentView<ActivityRegisterBinding>(this,R.layout.activity_register)
        activityRegisterBinding.registerActivityBinder = ViewModelProviders.of(this,RegisterActivityViewmodelFactory(this))
                .get(RegisterActivityViewmodel::class.java)
    }
}
