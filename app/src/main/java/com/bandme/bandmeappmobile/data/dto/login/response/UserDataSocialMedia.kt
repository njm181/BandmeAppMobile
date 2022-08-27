package com.bandme.bandmeappmobile.data.dto.login.response

import com.google.gson.annotations.SerializedName

data class UserDataSocialMedia(
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("profilePhoto")
    val profilePhoto: String?,
    @SerializedName("provider")
    val provider: String?
)