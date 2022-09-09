package com.bandme.bandmeappmobile.data.dto.login.request

import com.google.gson.annotations.SerializedName

data class ConfirmAccountRequest(
    @SerializedName("code")
    val code: String,
)
