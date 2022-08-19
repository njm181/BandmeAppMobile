package com.bandme.bandmeappmobile.data.dto.login.response

import com.google.gson.annotations.SerializedName

data class ValidateEmailResetPasswordResponse(
    @SerializedName("emailValid")
    val emailValid: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("sentEmail")
    val sentEmail: Boolean
)