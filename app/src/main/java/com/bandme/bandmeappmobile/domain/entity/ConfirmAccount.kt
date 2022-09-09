package com.bandme.bandmeappmobile.domain.entity

import com.google.gson.annotations.SerializedName

data class ConfirmAccount(
    @SerializedName("isConfirm")
    val isConfirm: Boolean,
    @SerializedName("jwt")
    val jwt: String,
    @SerializedName("message")
    val message: String
)
