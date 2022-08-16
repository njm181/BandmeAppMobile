package com.bandme.bandmeappmobile.data.dto.login.response

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("userAuthenticated")
    val userAuthenticated: UserAuthenticated
)