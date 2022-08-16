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