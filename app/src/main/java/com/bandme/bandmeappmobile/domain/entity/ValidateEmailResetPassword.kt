package com.bandme.bandmeappmobile.domain.entity

import com.google.gson.annotations.SerializedName

data class ValidateEmailResetPassword(
    @SerializedName("emailValid")
    val emailValid: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("sentEmail")
    val sentEmail: Boolean
)
