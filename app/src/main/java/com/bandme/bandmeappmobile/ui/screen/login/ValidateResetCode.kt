package com.bandme.bandmeappmobile.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bandme.bandmeappmobile.ui.theme.BandmeAppMobileTheme
import com.bandme.bandmeappmobile.ui.theme.Gray700
import com.bandme.bandmeappmobile.ui.utils.ValidateCodeResetPasswordState
import com.bandme.bandmeappmobile.ui.utils.ValidateEmailResetPasswordState
import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel

@Composable
fun ValidateResetCodeScreen(
    viewModel: LoginViewModel? = null,
    onNavigateToSuccess: () -> Unit = {},
    onBackPress: () -> Unit = {}
) {

    var result = viewModel?.validateCodeResetPasswordStateFlow?.collectAsState()
    var validateEmailResult by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    result?.value.let { response ->
        println("RESPUESTA ===> : $response")
        when(response){
            is ValidateCodeResetPasswordState.Loading, ValidateCodeResetPasswordState.Initial -> {
                //progressbar
                println("======= LOADING ========")
            }
            is ValidateCodeResetPasswordState.Success -> {
                println("======= SUCESS ========")
                //maybe true or false
                if (response.isValid){
                    println("JWT GUARDADO ======> ${response.jwt}")
                    /*println("EMAIL GUARDADO ======> ${viewModel?.lastEmailResetPasswordEntered?.value}")
                    validateEmailResult = response.emailValid*/
                    viewModel?.setIsResetPassword(isResetPassword = true)
                    if (isError) isError = false
                    //navigate to success
                } else {
                    isError = true
                    errorMessage = response.message
                }

            }
            is ValidateCodeResetPasswordState.Failure -> {
                //if is failure, should be shown a toast or modal with the error message
                println("======= FAILURE ========")
                isError = true
                errorMessage = response.message
            }
            else -> {}
        }
    }

    ValidateResetCodeContent(
        onValidateResetCode = { viewModel?.validateCodeResetPassword(it) },
        isWrongCode = isError,
        errorMessage = errorMessage
    )
}

@Composable
fun ValidateResetCodeContent(
    isWrongCode: Boolean = false,
    errorMessage: String = "",
    onValidateResetCode: (String) -> Unit = {}
) {
    var code by remember { mutableStateOf(TextFieldValue("")) }
    var enableButton by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween) {
        Column() {
            Text(text = "En el email que ingreso recibirá un código de 4 digitos", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Gray700)

            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = code,
                    onValueChange = { newText ->
                        if(newText.text.length <= 4){
                            code = newText
                        }
                        enableButton = newText.text.length == 4
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Ingrese su código aqui") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    isError = isWrongCode,
                    singleLine = true,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            if(isWrongCode) Text(text = errorMessage, color = MaterialTheme.colors.error)
        }
        Button(
            onClick = {
                onValidateResetCode(code.text)
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

@Preview(name = "default")
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ValidateResetCodeContentPreview() {
    BandmeAppMobileTheme() {
        ValidateResetCodeContent(
            isWrongCode = true,
            errorMessage = "No pudimos validar tu código, vuelve a intentarlo más tarde."
        )
    }
}