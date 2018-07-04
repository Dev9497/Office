package com.example.office.model

import com.google.gson.annotations.SerializedName

data class Client(
        @SerializedName("id")
        var id: String? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("username")
        var username: String? = null,

        @SerializedName("email")
        var email: String? = null,

        @SerializedName("address")
        var address: ClientAddress? = null,

        @SerializedName("phone")
        var phone: String? = null,

        @SerializedName("website")
        var website: String? = null,

        @SerializedName("company")
        var company: ClientCompany? = null

)