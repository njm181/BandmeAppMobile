package com.bandme.bandmeappmobile.data.dto.login.request

import com.google.gson.annotations.SerializedName

data class ValidateLoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)