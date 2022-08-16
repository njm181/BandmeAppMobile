package com.bandme.bandmeappmobile.data.dto.login.request

import com.google.gson.annotations.SerializedName

data class ValidateEmailRequest(
    @SerializedName("email")
    val email: String,
)
