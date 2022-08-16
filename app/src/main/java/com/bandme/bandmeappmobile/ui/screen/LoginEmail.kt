package com.bandme.bandmeappmobile.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bandme.bandmeappmobile.ui.theme.BandmeAppMobileTheme
import com.bandme.bandmeappmobile.ui.utils.BaseAlertDialog
import com.bandme.bandmeappmobile.ui.utils.ValidateEmailState
import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel

@Composable
fun LoginEmailScreen(
    viewModel: LoginViewModel? = null
) {
    var result = viewModel?.validateEmailStateFlow?.collectAsState()
    var validateEmailResult by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var isFailure by remember { mutableStateOf(false) }

    result?.value.let { response ->
        println("RESPUESTA ===> : $response")
        when(response){
            is ValidateEmailState.Loading, ValidateEmailState.Initial -> {
                //progressbar
                println("======= LOADING ========")
            }
            is ValidateEmailState.Success -> {
                println("======= SUCESS ========")
                //maybe true or false
                if (response.isEmailValidated){
                    println("EMAIL GUARDADO ======> ${viewModel?.lastEmailEntered?.value}")
                    //validateEmailResult = response.isEmailValidated
                    if (isError) isError = false
                    if (isFailure) isFailure = false
                } else {
                    isError = true
                }

            }
            is ValidateEmailState.Failure -> {
                //if is failure, should be shown a toast or modal with the error message
                println("======= FAILURE ========")
                isFailure = true
            }
            else -> {}
        }
    }

    LoginEmailContent(
        onValidateEmail = { viewModel?.validateExistEmail(it) },
        isEmailValidated = validateEmailResult,
        isError = isError,
        isFailure = isFailure
    )
}

@Composable
fun LoginEmailContent(
    onValidateEmail: (String) -> Unit,
    isEmailValidated: Boolean = false,
    isError: Boolean = false,
    isFailure: Boolean = false,
    maxCharacters: Int = 60,
){
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var openDialog by remember { mutableStateOf(isError) }
    var enableButton by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        BaseAlertDialog(
            isVisible = openDialog,
            isFailure = isFailure,
            onDismissAction = { openDialog = !openDialog },
            onAffirmativeAction = { println("CONFIRM ACTION <=============") },
            title = "Atención",
            description = "El email ingresado no existe o es incorrecto." +
                    " \nSi ya esta registrado vuelva a ingresarlo correctamente. " +
                    "\nSi no, puede registrarse ahora mismo.",
            affirmativeTitle = "Registrarme",
            dismissTitle = "Volver a intentar"
        )

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
                label = {
                        if (!isError){
                            Text(text = "Ingrese su email")
                        } else {
                            Text(text = "Verifique el email ingresado")
                        }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = isError,
                singleLine = true,
            )
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


@Preview(name = "default theme")
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginEmailContentPreview() {
    BandmeAppMobileTheme {
        LoginEmailContent(
            onValidateEmail = {},
            isEmailValidated = true,
            isError = false
        )
    }
}