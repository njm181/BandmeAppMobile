package com.bandme.bandmeappmobile.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandme.bandmeappmobile.domain.useCase.ValidateEmailUseCase
import com.bandme.bandmeappmobile.ui.utils.ValidateEmailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel (private val validateEmailUseCase: ValidateEmailUseCase): ViewModel() {

    private val _validateEmailStateFlow = MutableStateFlow<ValidateEmailState>(value = ValidateEmailState.Initial)
    val validateEmailStateFlow: StateFlow<ValidateEmailState> = _validateEmailStateFlow


    fun validateExistEmail(email: String){
        validateEmail(email = email)
    }

    private fun validateEmail(email: String){
        viewModelScope.launch {
            _validateEmailStateFlow.value = ValidateEmailState.Loading
            val result = validateEmailUseCase.invoke(email = email)
            if (result != null){
               _validateEmailStateFlow.value = ValidateEmailState.Success(existEmail = result)
            } else {
                _validateEmailStateFlow.value = ValidateEmailState.Failure(errorMessage = "No pudimos validar tu email, vuelve a intentarlo más tarde.")
            }
        }
    }
}