package com.bandme.bandmeappmobile.data.dto.login.response

import com.google.gson.annotations.SerializedName

data class Payload(
    @SerializedName("user_data")
    val user_data: UserDataCreateAccount
)