package com.example.office.view

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.office.R
import com.example.office.databinding.ActivityHelloUserBinding
import com.example.office.interfaces.DisplayClientDB
import com.example.office.viewmodel.HelloUserViewmodel
import com.example.office.viewmodel.HelloUserViewmodelFactory

class HelloUserActivity : AppCompatActivity(), DisplayClientDB {
    override fun goToLoginScreen() {
        startActivity(Intent(this,MainActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
    }

    override fun goToClientDB() {
        startActivity(Intent(this@HelloUserActivity,DisplayClientActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_hello_user)
        val activityHelloUserBinding = DataBindingUtil.setContentView<ActivityHelloUserBinding>(this,R.layout.activity_hello_user)
        activityHelloUserBinding.activityHelloUserBinder = ViewModelProviders.of(this,HelloUserViewmodelFactory(this))
                .get(HelloUserViewmodel::class.java)
    }
}
