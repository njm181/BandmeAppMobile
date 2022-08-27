package com.bandme.bandmeappmobile.data.utils

import com.bandme.bandmeappmobile.data.dto.login.response.*
import com.bandme.bandmeappmobile.domain.entity.*

fun ValidateLoginResponse.toDomainUserValidateLogin() : UserValidateLogin {
    return UserValidateLogin(
        isAuthenticated = isAuthenticated,
        email = user_data?.userAuthenticated?.email,
        jwt = user_data?.userAuthenticated?.jwt
    )
}

fun ValidateEmailResetPasswordResponse.toDomainValidateEmailResetPassword(): ValidateEmailResetPassword {
    return ValidateEmailResetPassword(
        emailValid = emailValid,
        message = message,
        sentEmail = sentEmail
    )
}

fun ValidateCodeResetPasswordResponse.toDomainValidateCodeResetPassword(): ValidateCodeResetPassword{
    return ValidateCodeResetPassword(
        isValid = isValid,
        jwt = jwt,
        message = message
    )
}

fun ValidateResetPasswordResponse.toDomainValidateResetPassword(): ValidateResetPassword {
    return ValidateResetPassword(
        jwt = jwt,
        message = message,
        wasUpdated = wasUpdated
    )
}

fun ValidateGoogleResponse.toDomainValidateGoogle(): ValidateGoogle {
    return ValidateGoogle(
        exist_email = exist_email,
        jwt = jwt,
        message = message,
        user_data = user_data
    )
}