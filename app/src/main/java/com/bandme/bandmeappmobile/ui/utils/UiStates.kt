package com.bandme.bandmeappmobile.ui.utils

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