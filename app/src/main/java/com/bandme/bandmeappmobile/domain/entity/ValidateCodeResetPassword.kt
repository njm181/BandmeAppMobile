package com.bandme.bandmeappmobile.domain.entity

import com.google.gson.annotations.SerializedName

data class ValidateCodeResetPassword(
    @SerializedName("isValid")
    val isValid: Boolean,
    @SerializedName("jwt")
    val jwt: String,
    @SerializedName("message")
    val message: String
)
