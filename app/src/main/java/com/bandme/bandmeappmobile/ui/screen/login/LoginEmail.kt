package com.bandme.bandmeappmobile.ui.screen.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
    viewModel: LoginViewModel? = null,
    onNavigateToSuccess: () -> Unit = {},
    onBackPress: () -> Unit = {},
    onNavigateToFinishRegiter: () -> Unit = {}
) {
    var result = viewModel?.validateEmailStateFlow?.collectAsState()
    var isError by remember { mutableStateOf(false) }
    var isFailure by remember { mutableStateOf(false) }

    var openDialog by rememberSaveable { mutableStateOf(false) }


    result?.value.let { response ->
        println("RESPUESTA ===> : $response")
        when(response){
            is ValidateEmailState.Loading, ValidateEmailState.Initial -> {
                //progressbar
                println("======= LOADING ========")
            }
            is ValidateEmailState.SuccessLogin -> {
                println("======= SUCESS LOGIN========")
                println("EMAIL GUARDADO ======> ${viewModel?.lastEmailEntered?.value}")
                //validateEmailResult = response.isEmailValidated
                if (isError) isError = false
                if (isFailure) isFailure = false
                LaunchedEffect(Unit){
                    onNavigateToSuccess()
                }
            }
            is ValidateEmailState.SuccessRegister -> {
                isError = true
            }
            is ValidateEmailState.SuccessFinishRegister -> {
                onNavigateToFinishRegiter()
            }
            is ValidateEmailState.Failure -> {
                println("======= FAILURE ========")
                isFailure = true
            }
            else -> {}
        }
    }

    BaseAlertDialog(
        show = viewModel!!.showDialog,
        isFailure = isFailure,
        onDismissAction = { viewModel.setShowDialogVisibility(false) },
        onAffirmativeAction = {
            println("CONFIRM ACTION <=============")
            viewModel.setIsNewUser(true)
            onNavigateToSuccess()
        },
        title = "Atención",
        description = "El email ingresado no existe o es incorrecto." +
                " \nSi ya esta registrado vuelva a ingresarlo correctamente. " +
                "\nSi no, puede registrarse ahora mismo.",
        affirmativeTitle = "Registrarme",
        dismissTitle = "Volver a intentar"
    )

    LoginEmailContent(
        onValidateEmail = { viewModel.validateExistEmail(it) },
        isError = isError,
        setIsNewUser = { viewModel.setIsNewUser(true) },
        isFailure = isFailure,
        openDialog = openDialog,
        onChangeOpenDialog = { openDialog = it }
    )

}

@Composable
fun LoginEmailContent(
    onValidateEmail: (String) -> Unit,
    isError: Boolean = false,
    maxCharacters: Int = 60,
    setIsNewUser: (Boolean) -> Unit?,
    isFailure: Boolean,
    openDialog: Boolean,
    onChangeOpenDialog: (Boolean) -> Unit
){
    var email by remember { mutableStateOf(TextFieldValue("")) }//NO HABRE EL DIALOGGGGGG
    var enableButton by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {


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
            isError = false,
            setIsNewUser = {},
            isFailure = false,
            openDialog = false,
            onChangeOpenDialog = {}
        )
    }
}