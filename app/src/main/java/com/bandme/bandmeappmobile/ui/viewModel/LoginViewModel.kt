package com.bandme.bandmeappmobile.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandme.bandmeappmobile.domain.useCase.login.ValidateEmailResetPasswordUseCase
import com.bandme.bandmeappmobile.domain.useCase.login.ValidateEmailUseCase
import com.bandme.bandmeappmobile.domain.useCase.login.ValidateLoginUseCase
import com.bandme.bandmeappmobile.ui.utils.ValidateEmailResetPasswordState
import com.bandme.bandmeappmobile.ui.utils.ValidateEmailState
import com.bandme.bandmeappmobile.ui.utils.ValidateLoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validateEmailResetPasswordUseCase: ValidateEmailResetPasswordUseCase
    ): ViewModel() {

    //region State Flows

    private val _lastEmailEntered = MutableStateFlow(value = "nicolasjmolina1@gmail.com")
    val lastEmailEntered: StateFlow<String> = _lastEmailEntered

    private val _lastEmailResetPasswordEntered = MutableStateFlow(value = "nicolasjmolina1@gmail.com")
    val lastEmailResetPasswordEntered: StateFlow<String> = _lastEmailResetPasswordEntered

    fun setLastEmailEntered(email: String){
        _lastEmailEntered.value = email
    }

    private val _registerPassword = MutableStateFlow(value = "")
    val registerPassword: StateFlow<String> = _registerPassword

    fun setRegisterPassword(password: String){
        _registerPassword.value = password
    }

    private val _isNewUser = MutableStateFlow(value = false)
    val isNewUser: StateFlow<Boolean> = _isNewUser

    fun setIsNewUser(isNew: Boolean){
        _isNewUser.value = isNew
    }

    private val _validateEmailStateFlow = MutableStateFlow<ValidateEmailState>(value = ValidateEmailState.Initial)
    val validateEmailStateFlow: StateFlow<ValidateEmailState> = _validateEmailStateFlow

    private val _validateLoginStateFlow = MutableStateFlow<ValidateLoginState>(value = ValidateLoginState.Initial)
    val validateLoginStateFlow: StateFlow<ValidateLoginState> = _validateLoginStateFlow

    private val _validateEmailResetPasswordStateFlow = MutableStateFlow<ValidateEmailResetPasswordState>(value = ValidateEmailResetPasswordState.Initial)
    val validateEmailResetPasswordStateFlow: StateFlow<ValidateEmailResetPasswordState> = _validateEmailResetPasswordStateFlow

    //endregion

    //region public functions
    fun validateExistEmail(email: String){
        validateEmail(email = email)
    }

    fun validateUserLogin(password: String){
        validateLogin(password = password)
    }

    fun validateResetPassword(email: String){
        validateEmailResetPassword(email = email)
    }

    //endregion

    //region private functions
    private fun validateEmail(email: String){
        viewModelScope.launch {
            _validateEmailStateFlow.value = ValidateEmailState.Loading
            val result = validateEmailUseCase.invoke(email = email)
            if (result != null){
                if (result) _lastEmailEntered.value = email
               _validateEmailStateFlow.value = ValidateEmailState.Success(existEmail = result)
            } else {
                _validateEmailStateFlow.value = ValidateEmailState.Failure(errorMessage = "No pudimos validar tu email, vuelve a intentarlo más tarde.")
            }
        }
    }

    private fun validateLogin(password: String) {
        viewModelScope.launch {
            _validateLoginStateFlow.value = ValidateLoginState.Loading
            val result = validateLoginUseCase.invoke(email = lastEmailEntered.value, password = password)
            if(false){//result != null
                if (result!!.isAuthenticated){
                    //todo almacenar el JWT en shared preferences usar KOIN y el email en el stateflow
                    _validateLoginStateFlow.value = ValidateLoginState.Success(isValidated = true)
                }else{
                    _validateLoginStateFlow.value = ValidateLoginState.Success(isValidated = false)
                }
            }else{
                _validateLoginStateFlow.value = ValidateLoginState.Failure(errorMessage = "No pudimos validar tu inicio de sesión, vuelve a intentarlo más tarde.")
            }
        }
    }

    private fun validateEmailResetPassword(email: String) {
        viewModelScope.launch {
            _validateEmailResetPasswordStateFlow.value = ValidateEmailResetPasswordState.Loading
            val result = validateEmailResetPasswordUseCase.invoke(email = email)
            if (result != null && result.emailValid && result.sentEmail){
                _lastEmailResetPasswordEntered.value = email
                _validateEmailResetPasswordStateFlow.value = ValidateEmailResetPasswordState.Success(isValidated = result.emailValid, wasSentEmail = result.sentEmail)
            } else {
                val message = if (!result?.message.isNullOrEmpty()) result?.message else "No pudimos validar tu email, vuelve a intentarlo más tarde."
                _validateEmailResetPasswordStateFlow.value = ValidateEmailResetPasswordState.Failure(errorMessage = message.orEmpty())
            }
        }
    }

    //endregion
}