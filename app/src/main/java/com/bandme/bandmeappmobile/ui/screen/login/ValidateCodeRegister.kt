package com.bandme.bandmeappmobile.ui.screen.login

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
import com.bandme.bandmeappmobile.ui.theme.Gray700
import com.bandme.bandmeappmobile.ui.utils.ConfirmAccountState
import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel

@Composable
fun ValidateCodeRegisterScreen(
    viewModel: LoginViewModel,
    onNavigateToSuccess: () -> Unit,
    onBackPress: () -> Boolean
) {
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var codeResult = viewModel.confirmAccountStateFlow.collectAsState()
    codeResult.value.let {
        when(it){
            is ConfirmAccountState.Initial -> {
                //progressbar
                println("======= LOADING ========")
            }
            is ConfirmAccountState.Success -> {
                println("======= SUCESS ========")
                //maybe true or false
                if (it.confirmedAccount){
                    if (isError) isError = false
                    LaunchedEffect(Unit){
                        onNavigateToSuccess()
                    }
                } else {
                    isError = true
                    errorMessage = it.message
                }
            }
            is ConfirmAccountState.Failure -> {
                //if is failure, should be shown a toast or modal with the error message
                println("======= FAILURE ========")
                isError = true
                errorMessage = it.message
            }
            else -> {}
        }
    }
    
    ValidateCodeRegisterContent(
        isWrongCode = isError,
        errorMessage = errorMessage,
        onValidateCode = { viewModel.confirmUserAccount(it) }
    )
}

@Composable
fun ValidateCodeRegisterContent(
    isWrongCode: Boolean = false,
    errorMessage: String = "",
    onValidateCode: (String) -> Unit = {}
) {
    var code by remember { mutableStateOf(TextFieldValue("")) }
    var enableButton by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween) {
        Column() {
            Text(text = "Recibirá un código de 4 digitos en su email para activar su cuenta", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Gray700)

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
                onValidateCode(code.text)
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
fun ValidateCodeRegisterPreview() {
    BandmeAppMobileTheme {
        ValidateCodeRegisterContent()
    }
}