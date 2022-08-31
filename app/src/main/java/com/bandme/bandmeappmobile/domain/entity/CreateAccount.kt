package com.bandme.bandmeappmobile.domain.entity

import com.google.gson.annotations.SerializedName

data class CreateAccount(
    @SerializedName("email")
    val email: String,
    @SerializedName("accountCreated")
    val accountCreated: Boolean,
    @SerializedName("message")
    val message: String,
)
