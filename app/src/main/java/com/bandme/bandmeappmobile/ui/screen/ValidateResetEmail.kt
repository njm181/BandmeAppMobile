package com.bandme.bandmeappmobile.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bandme.bandmeappmobile.ui.theme.BandmeAppMobileTheme
import com.bandme.bandmeappmobile.ui.theme.Gray600
import com.bandme.bandmeappmobile.ui.theme.Gray700
import com.bandme.bandmeappmobile.ui.utils.BaseAlertDialog
import com.bandme.bandmeappmobile.ui.utils.ValidateEmailResetPasswordState
import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel

@Composable
fun ValidateResetEmailScreen(
    viewModel: LoginViewModel? = null,
    onNavigateToSuccess: () -> Unit = {},
    onBackPress: () -> Unit = {}
) {
    var result = viewModel?.validateEmailResetPasswordStateFlow?.collectAsState()
    var validateEmailResult by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    result?.value.let { response ->
        println("RESPUESTA ===> : $response")
        when(response){
            is ValidateEmailResetPasswordState.Loading, ValidateEmailResetPasswordState.Initial -> {
                //progressbar
                println("======= LOADING ========")
            }
            is ValidateEmailResetPasswordState.Success -> {
                println("======= SUCESS ========")
                //maybe true or false
                if (response.emailValid && response.sentEmail){
                    println("EMAIL GUARDADO ======> ${viewModel?.lastEmailResetPasswordEntered?.value}")
                    validateEmailResult = response.emailValid
                    if (isError) isError = false
                    //navigate to success
                } else {
                    isError = true
                    errorMessage = response.message
                }

            }
            is ValidateEmailResetPasswordState.Failure -> {
                //if is failure, should be shown a toast or modal with the error message
                println("======= FAILURE ========")
                isError = true
                errorMessage = response.message
            }
            else -> {}
        }
    }

    ValidateResetEmailContent(
        onValidateEmail = { viewModel?.validateResetPassword(it) },
        isError = isError,
        errorMessage = errorMessage
    )
}

@Composable
fun ValidateResetEmailContent(
    onValidateEmail: (String) -> Unit,
    isError: Boolean = false,
    maxCharacters: Int = 60,
    errorMessage: String = "",
){
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var enableButton by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(text = "Reiniciar contraseña", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Gray700)
            Spacer(modifier = Modifier.height(4.dp))
            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { newText ->
                        if (newText.text.length <= maxCharacters){
                            email = newText
                            enableButton = email.text.contains("@") && email.text.isNotEmpty()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Ingrese su email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = isError,
                    singleLine = true,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            if(isError) Text(text = errorMessage, color = MaterialTheme.colors.error)
        }
        Button(
            onClick = {
                onValidateEmail(email.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = enableButton
        ) {
            Text(text = "Continuar")
        }
    }
}


@Preview(name = "default theme reset")
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ValidateResetEmailContentPreview() {
    BandmeAppMobileTheme {
        ValidateResetEmailContent(
            onValidateEmail = {},
            isError = true,
            errorMessage = "No pudimos validar tu email, vuelve a intentarlo más tarde."
        )
    }
}