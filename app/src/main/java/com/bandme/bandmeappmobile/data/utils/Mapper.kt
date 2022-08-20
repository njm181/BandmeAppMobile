package com.bandme.bandmeappmobile.data.utils

import com.bandme.bandmeappmobile.data.dto.login.response.ValidateCodeResetPasswordResponse
import com.bandme.bandmeappmobile.data.dto.login.response.ValidateEmailResetPasswordResponse
import com.bandme.bandmeappmobile.data.dto.login.response.ValidateLoginResponse
import com.bandme.bandmeappmobile.data.dto.login.response.ValidateResetPasswordResponse
import com.bandme.bandmeappmobile.domain.entity.UserValidateLogin
import com.bandme.bandmeappmobile.domain.entity.ValidateCodeResetPassword
import com.bandme.bandmeappmobile.domain.entity.ValidateEmailResetPassword
import com.bandme.bandmeappmobile.domain.entity.ValidateResetPassword

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