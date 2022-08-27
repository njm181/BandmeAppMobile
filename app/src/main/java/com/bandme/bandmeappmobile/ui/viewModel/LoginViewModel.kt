package com.bandme.bandmeappmobile.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandme.bandmeappmobile.domain.useCase.login.*
import com.bandme.bandmeappmobile.domain.utils.AppPreferences
import com.bandme.bandmeappmobile.ui.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    private val preferences: AppPreferences,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validateEmailResetPasswordUseCase: ValidateEmailResetPasswordUseCase,
    private val validateCodeResetPasswordUseCase: ValidateCodeResetPasswordUseCase,
    private val validateResetPasswordUseCase: ValidateResetPasswordUseCase,
    private val validateGoogleUseCase: ValidateGoogleUseCase
    ): ViewModel() {


    //region State Flows
    var logOutState by mutableStateOf<LogOutUserState>(LogOutUserState.Initial)
        private set

    private val _lastEmailEntered = MutableStateFlow(value = "nicolasjmolina1@gmail.com")
    val lastEmailEntered: StateFlow<String> = _lastEmailEntered

    private val _lastEmailResetPasswordEntered = MutableStateFlow(value = "nicolasjmolina1@gmail.com")
    val lastEmailResetPasswordEntered: StateFlow<String> = _lastEmailResetPasswordEntered

    fun setLastEmailEntered(email: String){
        _lastEmailEntered.value = email
    }

    private val _googleAccessToken = MutableStateFlow(value = "")
    val googleAccessToken: StateFlow<String> = _googleAccessToken

    fun setGoogleAccessToken(accessToken: String){
        _googleAccessToken.value = accessToken
        googleLogin()
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

    private val _isResetPassword = MutableStateFlow(value = false)
    val isResetPassword: StateFlow<Boolean> = _isResetPassword

    fun setIsResetPassword(isResetPassword: Boolean){
        _isResetPassword.value = isResetPassword
    }

    private val _validateLoginGooleStateFlow = MutableStateFlow<ValidateLoginGoogleState>(value = ValidateLoginGoogleState.Initial)
    val validateLoginGoogleStateFlow: StateFlow<ValidateLoginGoogleState> = _validateLoginGooleStateFlow

    private val _validateEmailStateFlow = MutableStateFlow<ValidateEmailState>(value = ValidateEmailState.Initial)
    val validateEmailStateFlow: StateFlow<ValidateEmailState> = _validateEmailStateFlow

    private val _validateLoginStateFlow = MutableStateFlow<ValidateLoginState>(value = ValidateLoginState.Initial)
    val validateLoginStateFlow: StateFlow<ValidateLoginState> = _validateLoginStateFlow

    private val _validateEmailResetPasswordStateFlow = MutableStateFlow<ValidateEmailResetPasswordState>(value = ValidateEmailResetPasswordState.Initial)
    val validateEmailResetPasswordStateFlow: StateFlow<ValidateEmailResetPasswordState> = _validateEmailResetPasswordStateFlow

    private val _validateCodeResetPasswordStateFlow = MutableStateFlow<ValidateCodeResetPasswordState>(value = ValidateCodeResetPasswordState.Initial)
    val validateCodeResetPasswordStateFlow: StateFlow<ValidateCodeResetPasswordState> = _validateCodeResetPasswordStateFlow

    private val _validateResetPasswordStateFlow = MutableStateFlow<ValidateResetPasswordState>(value = ValidateResetPasswordState.Initial)
    val validateResetPasswordStateFlow: StateFlow<ValidateResetPasswordState> = _validateResetPasswordStateFlow

    //endregion

    //region public functions
    fun validateExistEmail(email: String){
        validateEmail(email = email)
    }

    fun validateUserLogin(password: String){
        validateLogin(password = password)
    }

    fun validateEmailResetPassword(email: String){
        this.validateUserEmailResetPassword(email = email)
    }

    fun validateCodeResetPassword(code: String){
        validateUserCodeResetPassword(code = code)
    }

    fun validateResetPassword(newPassword: String){
        validateUserResetPassword(newPassword = newPassword)
    }

    fun logOut(){
        logOutUser()
    }
    //endregion

    //region private functions
    private fun logOutUser(){
        preferences.saveAuthorization("")
        logOutState = LogOutUserState.LogOut
    }

    private fun googleLogin() {
        viewModelScope.launch {
            _validateLoginGooleStateFlow.value = ValidateLoginGoogleState.Loading
            val result = validateGoogleUseCase.invoke(googleAccessToken.value)
            if (result != null){
                if (result.exist_email == true){
                    preferences.saveAuthorization(result.jwt.orEmpty())
                    _validateLoginGooleStateFlow.value = ValidateLoginGoogleState.SuccessIsLogin(
                        token = result.jwt
                    )
                }else{
                    _validateLoginGooleStateFlow.value = ValidateLoginGoogleState.SuccessIsRegister(
                        data = result.user_data
                    )
                }
            } else {
                _validateLoginGooleStateFlow.value = ValidateLoginGoogleState.Failure(errorMessage = "No pudimos validar tu email, vuelve a intentarlo más tarde.")
            }
        }
    }

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
            if(result != null){
                if (result.isAuthenticated){
                    //todo almacenar el JWT en shared preferences usar KOIN y el email en el stateflow
                    preferences.saveAuthorization(result.jwt.orEmpty())
                    _validateLoginStateFlow.value = ValidateLoginState.Success(isValidated = true)
                }else{
                    _validateLoginStateFlow.value = ValidateLoginState.Success(isValidated = false)
                }
            }else{
                _validateLoginStateFlow.value = ValidateLoginState.Failure(errorMessage = "No pudimos validar tu inicio de sesión, vuelve a intentarlo más tarde.")
            }
        }
    }

    private fun validateUserEmailResetPassword(email: String) {
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

    private fun validateUserCodeResetPassword(code: String) {
        viewModelScope.launch {
            _validateCodeResetPasswordStateFlow.value = ValidateCodeResetPasswordState.Loading
            val result = validateCodeResetPasswordUseCase.invoke(code = code)
            if (result != null && result.isValid){
                preferences.saveAuthorization(result.jwt)
                _validateCodeResetPasswordStateFlow.value = ValidateCodeResetPasswordState.Success(isValidated = result.isValid, token = preferences.getAuthorization())
            } else {
                val message = if (!result?.message.isNullOrEmpty()) result?.message else "No pudimos validar tu código, vuelve a intentarlo más tarde."
                _validateCodeResetPasswordStateFlow.value = ValidateCodeResetPasswordState.Failure(errorMessage = message.orEmpty())
            }
        }
    }

    private fun validateUserResetPassword(newPassword: String) {
        viewModelScope.launch {
            _validateResetPasswordStateFlow.value = ValidateResetPasswordState.Loading
            val result = validateResetPasswordUseCase.invoke(newPassword = newPassword, authorization = preferences.getAuthorization())
            if (result != null && result.wasUpdated){
                //todo almacenar el JWT en shared preferences usar KOIN y el email en el stateflow
                preferences.saveAuthorization(result.jwt)
                _validateResetPasswordStateFlow.value = ValidateResetPasswordState.Success(updated = result.wasUpdated, token = result.jwt)
            } else {
                val message = if (!result?.message.isNullOrEmpty()) result?.message else "No pudimos validar tu nueva clave, vuelve a intentarlo más tarde."
                _validateResetPasswordStateFlow.value = ValidateResetPasswordState.Failure(errorMessage = message.orEmpty())
            }
        }
    }
    //endregion
}