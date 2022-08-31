package com.bandme.bandmeappmobile.data.dto.login.response

import com.google.gson.annotations.SerializedName

data class CreateAccountResponse(
    @SerializedName("accountCreated")
    val accountCreated: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
)