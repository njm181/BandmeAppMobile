package com.bandme.bandmeappmobile.ui.utils

sealed class ValidateEmailState(
    val email: String = "",
    val isLoading: Boolean = false,
    val isEmailValidated: Boolean = false,
    val message: String = ""
){
    object Initial: ValidateEmailState(email = "")
    object Loading: ValidateEmailState(isLoading = true)
    class Success(private val existEmail: Boolean) : ValidateEmailState(isEmailValidated = existEmail)
    class Failure(private val errorMessage: String) : ValidateEmailState(message = errorMessage)

}