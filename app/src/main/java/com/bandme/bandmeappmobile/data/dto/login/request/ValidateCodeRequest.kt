package com.bandme.bandmeappmobile.data.dto.login.request

import com.google.gson.annotations.SerializedName

data class ValidateCodeRequest(
    @SerializedName("code")
    val code: String,
)
