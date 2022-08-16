package com.bandme.bandmeappmobile.data.dto.login.response

import com.google.gson.annotations.SerializedName

data class ValidateLoginResponse(
    @SerializedName("isAuthenticated")
    val isAuthenticated: Boolean = false,
    @SerializedName("user_data")
    val user_data: UserData? = null
)