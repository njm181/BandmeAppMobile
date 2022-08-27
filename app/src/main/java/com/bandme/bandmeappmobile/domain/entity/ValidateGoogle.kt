package com.bandme.bandmeappmobile.domain.entity

import com.bandme.bandmeappmobile.data.dto.login.response.UserDataSocialMedia
import com.google.gson.annotations.SerializedName

data class ValidateGoogle(
    @SerializedName("exist_email")
    val exist_email: Boolean?,
    @SerializedName("jwt")
    val jwt: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("user_data")
    val user_data: UserDataSocialMedia?
)
