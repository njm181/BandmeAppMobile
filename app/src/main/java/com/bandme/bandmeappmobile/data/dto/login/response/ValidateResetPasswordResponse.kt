package com.bandme.bandmeappmobile.data.dto.login.response

import com.google.gson.annotations.SerializedName

data class ValidateResetPasswordResponse(
    @SerializedName("jwt")
    val jwt: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("wasUpdated")
    val wasUpdated: Boolean
)