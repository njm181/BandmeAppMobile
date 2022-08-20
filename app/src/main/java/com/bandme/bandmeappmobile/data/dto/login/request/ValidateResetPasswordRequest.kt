package com.bandme.bandmeappmobile.data.dto.login.request

import com.google.gson.annotations.SerializedName

data class ValidateResetPasswordRequest(
    @SerializedName("newPassword")
    val newPassword: String
)