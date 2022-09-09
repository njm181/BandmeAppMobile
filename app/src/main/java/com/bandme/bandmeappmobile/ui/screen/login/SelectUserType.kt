package com.bandme.bandmeappmobile.ui.screen.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bandme.bandmeappmobile.R
import com.bandme.bandmeappmobile.ui.theme.BandmeAppMobileTheme
import com.bandme.bandmeappmobile.ui.theme.Gray600
import com.bandme.bandmeappmobile.ui.utils.BaseAlertDialog
import com.bandme.bandmeappmobile.ui.utils.CreateAccountState
import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel

enum class UserType {
    BANDA,
    ARTISTA,
    LUGAR
}

@Composable
fun SelectUserTypeScreen(
    viewModel: LoginViewModel,
    onNavigateToSuccess: () -> Unit,
    onBackPress: () -> Unit
) {

    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var createResult = viewModel.createAccountStateFlow.collectAsState()
    createResult.value.let {
        when(it){
            is CreateAccountState.Initial -> {
                //progressbar
                println("======= LOADING ========")
            }
            is CreateAccountState.Success -> {
                println("======= SUCCESS ========")
                if (it.accountCreated){
                    if (isError) isError = false
                    LaunchedEffect(Unit){
                        onNavigateToSuccess()
                    }
                }else{
                    isError = true
                    errorMessage = it.message
                }
            }
            is CreateAccountState.Failure -> {
                isError = true
                errorMessage = it.message
            }
            else -> {}
        }
    }

    BaseAlertDialog(
        show = viewModel!!.showDialog,
        isFailure = isError,
        onDismissAction = {
            viewModel.setShowDialogVisibility(false)
            println("=============> FAILURE <=============")
            //todo mandar a welcome y borrar stack
                          },
        onAffirmativeAction = {
            println("CONFIRM ACTION <=============")
            viewModel.setIsNewUser(true)
            onNavigateToSuccess()
        },
        title = "Atención",
        affirmativeTitle = "Registrarme",
        description = errorMessage,
        dismissTitle = "Cerrar",
    )

    SelectUserTypeContent(
        onUserTypeChange = { viewModel.setUserType(it) },
        userSelected = viewModel.userTypeState,
        onFinishRegister = { viewModel.createUserAccount() }
    )
}

@Composable
fun SelectUserTypeContent(
    onUserTypeChange: (String) -> Unit,
    userSelected: String,
    onFinishRegister: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column() {
            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                UserTypeCard(
                    userType = UserType.ARTISTA,
                    iconId = R.drawable.ic_person_48,
                    onUserTypeChange = onUserTypeChange,
                    userSelected = userSelected
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    UserTypeCard(
                        userType = UserType.BANDA,
                        iconId = R.drawable.ic_band_48,
                        onUserTypeChange = onUserTypeChange,
                        userSelected = userSelected
                    )

                }
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    UserTypeCard(
                        userType = UserType.LUGAR,
                        iconId = R.drawable.ic_location_on_48,
                        onUserTypeChange = onUserTypeChange,
                        userSelected = userSelected
                    )

                }
            }
        }

        Button(
            onClick = { onFinishRegister() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !userSelected.isNullOrEmpty()
        ) {
            Text(text = "Finalizar")
        }
    }
}

@Preview(name = "default")
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SelectUserTypePreview() {
    BandmeAppMobileTheme {
        SelectUserTypeContent({}, "ARTISTA", {})
    }
}

@Composable
fun UserTypeCard(userType: UserType, iconId: Int, onUserTypeChange: (String) -> Unit, userSelected: String) {
    Button(
        modifier = Modifier
            .width(110.dp)
            .height(110.dp),
        onClick = {
                  onUserTypeChange(userType.toString())
        },
        elevation = ButtonDefaults.elevation(6.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (userSelected == userType.toString()) MaterialTheme.colors.primaryVariant else Gray600
        ),
        shape = RoundedCornerShape(16)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(id = iconId), contentDescription = "icon user type")
            Spacer(Modifier.height(4.dp))
            Text(text = userType.toString(), color = MaterialTheme.colors.onPrimary, fontSize = 13.sp)
        }
    }
}