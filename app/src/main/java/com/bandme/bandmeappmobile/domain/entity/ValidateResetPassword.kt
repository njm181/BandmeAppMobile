package com.bandme.bandmeappmobile.domain.entity

import com.google.gson.annotations.SerializedName

data class ValidateResetPassword(
    @SerializedName("jwt")
    val jwt: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("wasUpdated")
    val wasUpdated: Boolean
)
