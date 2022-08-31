package com.bandme.bandmeappmobile.ui.utils

import com.bandme.bandmeappmobile.data.dto.login.response.UserDataSocialMedia

sealed class ValidateEmailState(
    val email: String = "",
    val isLoading: Boolean = false,
    val isEmailValidated: Boolean = false,
    val message: String = ""
){
    object Initial: ValidateEmailState(email = "")
    object Loading: ValidateEmailState(isLoading = true)
    class Success(existEmail: Boolean) : ValidateEmailState(isEmailValidated = existEmail)
    class Failure(errorMessage: String) : ValidateEmailState(message = errorMessage)
}

sealed class ValidateLoginState(
    val userValidated: Boolean = false,
    val isLoading: Boolean = false,
    val message: String = ""
){
    object Initial: ValidateLoginState()
    object Loading: ValidateLoginState(isLoading = true)
    class Success(isValidated: Boolean) : ValidateLoginState(userValidated = isValidated)
    class Failure(errorMessage: String) : ValidateLoginState(message = errorMessage)
}

sealed class ValidateEmailResetPasswordState(
    val emailValid: Boolean = false,
    val isLoading: Boolean = false,
    val sentEmail: Boolean = false,
    val message: String = ""
){
    object Initial: ValidateEmailResetPasswordState()
    object Loading: ValidateEmailResetPasswordState(isLoading = true)
    class Success(isValidated: Boolean, wasSentEmail: Boolean) : ValidateEmailResetPasswordState(emailValid = isValidated, sentEmail = wasSentEmail)
    class Failure(errorMessage: String) : ValidateEmailResetPasswordState(message = errorMessage)
}

sealed class ValidateCodeResetPasswordState(
    val isValid: Boolean = false,
    val isLoading: Boolean = false,
    val jwt: String = "",
    val message: String = ""
){
    object Initial: ValidateCodeResetPasswordState()
    object Loading: ValidateCodeResetPasswordState(isLoading = true)
    class Success(isValidated: Boolean, token: String) : ValidateCodeResetPasswordState(isValid = isValidated, jwt = token)
    class Failure(errorMessage: String) : ValidateCodeResetPasswordState(message = errorMessage)
}

sealed class ValidateResetPasswordState(
    val wasUpdated: Boolean = false,
    val isLoading: Boolean = false,
    val jwt: String = "",
    val message: String = ""
){
    object Initial: ValidateResetPasswordState()
    object Loading: ValidateResetPasswordState(isLoading = true)
    class Success(updated: Boolean, token: String) : ValidateResetPasswordState(wasUpdated = updated, jwt = token)
    class Failure(errorMessage: String) : ValidateResetPasswordState(message = errorMessage)
}

sealed class ValidateLoginGoogleState(
    val existEmail: Boolean? = false,
    val message: String? = "",
    val jwt: String? = "",
    val isLoading: Boolean = false,
    val userData: UserDataSocialMedia? = null
    ){
    object Initial: ValidateLoginGoogleState()
    object Loading: ValidateLoginGoogleState(isLoading = true)
    class Success(emailValidated: Boolean?, messageValidated: String?, data: UserDataSocialMedia?, token: String?)
        : ValidateLoginGoogleState(
        existEmail = emailValidated,
        message = messageValidated, jwt = token, userData = data)
    class SuccessIsLogin(token: String?): ValidateLoginGoogleState(jwt = token)
    class SuccessIsRegister(data: UserDataSocialMedia?): ValidateLoginGoogleState(userData = data)
    class Failure(errorMessage: String) : ValidateLoginGoogleState(message = errorMessage)
}

sealed class LogOutUserState{
    object Initial: LogOutUserState()
    object LogOut: LogOutUserState()
}

sealed class CreateAccountState(
    accountCreated: Boolean = false,
    message: String = "",
    emailSaved: String = "",
    isLoading: Boolean = false
){
    object Initial: CreateAccountState()
    object Loading: CreateAccountState(isLoading = true)
    class Success(created: Boolean, email: String): CreateAccountState(accountCreated = created, emailSaved = email)
    class Failure(errorMessage: String): CreateAccountState(message = errorMessage)

}