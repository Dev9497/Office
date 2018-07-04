package com.example.office.model
import com.google.gson.annotations.SerializedName

data class ClientAddress (
        @SerializedName("street")
        var street: String? = null,

        @SerializedName("suite")
        var suite: String? = null,

        @SerializedName("city")
        var city: String? = null,

        @SerializedName("zipcode")
        var zipcode: String? = null
)