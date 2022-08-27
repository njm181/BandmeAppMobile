package com.bandme.bandmeappmobile.data.dto.login.request

import com.google.gson.annotations.SerializedName

data class ValidateGoogleRequest(
    @SerializedName("access_token")
    val accessToken: String,
)
