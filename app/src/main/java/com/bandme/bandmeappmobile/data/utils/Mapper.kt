package com.bandme.bandmeappmobile.data.utils

import com.bandme.bandmeappmobile.data.dto.login.response.ValidateLoginResponse
import com.bandme.bandmeappmobile.domain.entity.UserValidateLogin

fun ValidateLoginResponse.toDomainUserValidateLogin() : UserValidateLogin {
    return UserValidateLogin(
        isAuthenticated = isAuthenticated,
        email = user_data?.userAuthenticated?.email,
        jwt = user_data?.userAuthenticated?.jwt
    )
}