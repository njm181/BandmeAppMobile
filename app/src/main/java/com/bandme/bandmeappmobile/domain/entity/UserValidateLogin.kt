package com.bandme.bandmeappmobile.domain.entity

import com.google.gson.annotations.SerializedName

data class UserValidateLogin(
    @SerializedName("isAuthenticated")
    val isAuthenticated: Boolean = false,
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("jwt")
    val jwt: String? = ""
)
