package com.bandme.bandmeappmobile.data.dto.login.response

import com.google.gson.annotations.SerializedName

data class UserDataCreateAccount(
    @SerializedName("email")
    val email: String,
    @SerializedName("userType")
    val userType: String
)