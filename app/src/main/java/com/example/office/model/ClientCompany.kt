package com.example.office.model
import com.google.gson.annotations.SerializedName

data class ClientCompany (
        @SerializedName("name")
        var name: String? = null,

        @SerializedName("catchPhrase")
        var catchPhrase: String? = null,

        @SerializedName("bs")
        var bs: String? = null
)