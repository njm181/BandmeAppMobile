package com.bandme.bandmeappmobile.data.dto.login.request

import com.google.gson.annotations.SerializedName

data class CreateAccountRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String = "",
    @SerializedName("provider")
    val provider: String,
    @SerializedName("userType")
    val userType: String,
    @SerializedName("profilePhoto")
    val profilePhoto: String = "",
    @SerializedName("firstName")
    val firstName: String = "",
    @SerializedName("lastName")
    val lastName: String = "",
)