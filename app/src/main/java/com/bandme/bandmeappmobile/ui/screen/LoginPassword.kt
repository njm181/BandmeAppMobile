package com.bandme.bandmeappmobile.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bandme.bandmeappmobile.R
import com.bandme.bandmeappmobile.ui.theme.BandmeAppMobileTheme
import com.bandme.bandmeappmobile.ui.theme.Gray600
import com.bandme.bandmeappmobile.ui.utils.BaseAlertDialog
import com.bandme.bandmeappmobile.ui.utils.ValidateLoginState
import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel

@Composable
fun LoginPasswordScreen(
    viewModel: LoginViewModel
) {
    //agregar reset password

    var isWrongPassword by remember { mutableStateOf(false) }
    var isFailure by remember { mutableStateOf(true) }

    var result = viewModel.validateLoginStateFlow.collectAsState()
    result.value.let { response ->
        println("RESPUESTA ===> : $response")
        when(response){
            is ValidateLoginState.Loading, ValidateLoginState.Initial -> {
                //progressbar
                println("======= LOADING ========")
            }
            is ValidateLoginState.Success -> {
                println("======= SUCESS ========")
                //maybe true or false
                if (response.userValidated){
                    //if true, go to dashboard
                    isWrongPassword = false
                    println("LOGIN SUCCESSED==========>GO TO DASHBOARD")
                } else {
                    //else false, show error for wrong password
                    isWrongPassword = true
                }

            }
            is ValidateLoginState.Failure -> {
                //if is failure, should be shown a toast or modal with the error message
                println("======= FAILURE ========")
                isFailure = true
            }
            else -> {}
        }
    }

    LoginPasswordContent(
        isNewUser = viewModel.isNewUser.collectAsState().value,
        onRegisterPassword = { viewModel.setRegisterPassword(it) },
        onValidateUserLogin = { viewModel.validateUserLogin(it) },
        isWrongPassword = isWrongPassword,
        isFailure = isFailure

    )
}

@Composable
fun LoginPasswordContent(
    maxLength: Int = 6,
    isNewUser: Boolean = false,
    onRegisterPassword: (String) -> Unit = {},
    isWrongPassword: Boolean = false,
    onValidateUserLogin: (String) -> Unit = {},
    isFailure: Boolean = false
) {
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var repeatedPassword by remember { mutableStateOf(TextFieldValue("")) }
    var showPassword by remember { mutableStateOf(false) }
    var showRepeatedPassword by remember { mutableStateOf(false) }
    var comparePassword by remember { mutableStateOf(false) }
    var enableButton by remember { mutableStateOf(false) }

    var openDialog by remember { mutableStateOf(isFailure) }

    BaseAlertDialog(
        isVisible = openDialog,
        isFailure = isFailure,
        onDismissAction = {
            openDialog = !openDialog
            println("=============> CONFIRM ACTION: GO TO WELCOME <=============")
                          },
        title = "Atención",
        description = "No se pudó validar el inicio de sesión. Por favor intente más tarde.",
        affirmativeTitle = "Cerrar",
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            if (isNewUser)
                Text(
                    text = "La longitud de la contraseña debe ser de 6 caracteres",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { newText ->
                        if(newText.text.length <= maxLength){
                            password = newText
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        if (!isWrongPassword){
                            Text(text = "Ingrese su contraseña")
                        } else {
                            Text(text = "Verifique la contraseña ingresada")
                        }
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    isError = isWrongPassword,
                    singleLine = true,
                    trailingIcon = {
                        var icon = if (showPassword){
                            painterResource(id = R.drawable.ic_visibility_24)
                        }else{
                            painterResource(id = R.drawable.ic_visibility_off_24)
                        }
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                icon,
                                contentDescription = "Visibility"
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (isNewUser){
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = repeatedPassword,
                        onValueChange = { newText ->
                            if(newText.text.length <= maxLength){
                                repeatedPassword = newText
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Repita su contraseña")
                        },
                        visualTransformation = if (showRepeatedPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        singleLine = true,
                        trailingIcon = {
                            var icon = if (showRepeatedPassword){
                                painterResource(id = R.drawable.ic_visibility_24)
                            }else{
                                painterResource(id = R.drawable.ic_visibility_off_24)
                            }
                            IconButton(onClick = { showRepeatedPassword = !showRepeatedPassword }) {
                                Icon(
                                    icon,
                                    contentDescription = "Visibility"
                                )
                            }
                        }
                    )
                }
            }else{
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = "Olvidaste tu contraseña?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Gray600,
                        modifier = Modifier.clickable { println("CLICK OLVIDASTE LA PASSWORD") }
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            if (comparePassword)
                Text(
                    text = "Las contraseñas no coinciden",
                    color = MaterialTheme.colors.onError,
                    fontSize = 14.sp
                )
        }

        enableButton = if (isNewUser){
            password.text.length == 6 && repeatedPassword.text.length == 6
        }else{
            password.text.length == 6
        }

        Button(
            onClick = {
                if (isNewUser){
                    comparePassword = password.text != repeatedPassword.text
                    if (comparePassword){
                        onRegisterPassword(password.text)
                    }
                }else{
                    onValidateUserLogin(password.text)
                }
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

@Preview(name = "default theme pass")
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginPasswordContentPreview() {
    BandmeAppMobileTheme {
        LoginPasswordContent()
    }
}