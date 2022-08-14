package com.bandme.bandmeappmobile.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.bandme.bandmeappmobile.ui.utils.BaseAlertDialog
import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel

@Composable
fun LoginPasswordScreen(
    viewModel: LoginViewModel
) {
    //si isNewUser == true, deberia guardar la password en el state flow y continuar a seleccionar el tipo de usuario
    //si isNewUser == false deberia hacer la pegada al servicio de login, ahi obtengo la respuesta
    //y manipulo si isError by service por contrasena incorrecta
    //agregar reset password

    LoginPasswordContent(
        isNewUser = viewModel.isNewUser.collectAsState().value,
        onRegisterPassword = { viewModel.setRegisterPassword(it) }

    )
}

@Composable
fun LoginPasswordContent(
    maxLength: Int = 6,
    isNewUser: Boolean = false,
    onRegisterPassword: (String) -> Unit = {},
    isWrongPassword: Boolean = false
) {
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var repeatedPassword by remember { mutableStateOf(TextFieldValue("")) }
    var showPassword by remember { mutableStateOf(false) }
    var showRepeatedPassword by remember { mutableStateOf(false) }
    var comparePassword by remember { mutableStateOf(false) }
    var enableButton by remember { mutableStateOf(false) }


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

@Preview(name = "default theme password")
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginPasswordContentPreview() {
    BandmeAppMobileTheme {
        LoginPasswordContent()
    }
}