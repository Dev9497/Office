package com.example.office.interfaces

interface RegisterResults {
    fun noMatchPassword()
    fun saveNewUser()
    fun matchPassword()
    fun cmpPassword(): Boolean
    fun displayErrorToast()
    fun goToLogin()
    fun hideKeyboard()
}