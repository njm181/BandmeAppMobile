package com.bandme.bandmeappmobile.data.dto.login.response

import com.google.gson.annotations.SerializedName

data class UserAuthenticated(
    @SerializedName("email")
    val email: String,
    @SerializedName("jwt")
    val jwt: String
)