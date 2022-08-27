package com.bandme.bandmeappmobile.data.dto.login.response

import com.google.gson.annotations.SerializedName

data class ValidateGoogleResponse(
    @SerializedName("exist_email")
    val exist_email: Boolean?,
    @SerializedName("jwt")
    val jwt: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("user_data")
    val user_data: UserDataSocialMedia?
)