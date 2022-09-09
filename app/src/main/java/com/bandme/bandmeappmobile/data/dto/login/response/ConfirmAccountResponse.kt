package com.bandme.bandmeappmobile.data.dto.login.response

import com.google.gson.annotations.SerializedName

data class ConfirmAccountResponse(
    @SerializedName("isConfirm")
    val isConfirm: Boolean,
    @SerializedName("jwt")
    val jwt: String,
    @SerializedName("message")
    val message: String
)