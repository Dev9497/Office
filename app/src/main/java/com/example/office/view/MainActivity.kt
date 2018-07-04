package com.example.office.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.office.R
import com.example.office.databinding.ActivityMainBinding
import com.example.office.interfaces.LoginResults
import com.example.office.sql.DatabaseHelper
import com.example.office.viewmodel.MainActivityViewModel
import com.example.office.viewmodel.MainActivityViewmodelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),LoginResults {
    override fun hideKeyboard() {
        val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
    }

    override fun goToRegister() {
        startActivity(Intent(this,RegisterActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
    }

    private lateinit var databaseHelper : DatabaseHelper
    override fun onSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show()
        val intent = Intent(this,HelloUserActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
    }

    override fun onError(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                .show()
        startActivity(Intent(this,MainActivity::class.java))
    }

    override fun isDataValid():Boolean {
        return(databaseHelper.checkUser(tiet_email.text.toString(),tiet_password.text.toString()))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        val activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        activityMainBinding.mainActivityBinder = ViewModelProviders.of(this,MainActivityViewmodelFactory(this))
                .get(MainActivityViewModel::class.java)
    }
}