package com.bandme.bandmeappmobile.ui.screen.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bandme.bandmeappmobile.R
import com.bandme.bandmeappmobile.ui.theme.BandmeAppMobileTheme
import com.bandme.bandmeappmobile.ui.theme.Gray600

@Composable
fun SelectUserTypeScreen() {
    SelectUserTypeContent()
}

@Composable
fun SelectUserTypeContent() {
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
                UserTypeCard(userType = "Artista", R.drawable.ic_person_48, {}, true)
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
                    UserTypeCard(userType = "Banda", R.drawable.ic_band_48, {}, true)

                }
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    UserTypeCard(userType = "Lugar", R.drawable.ic_location_on_48, {}, true)

                }
            }
        }

        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = true//enableButton
        ) {
            Text(text = "Finalizar")
        }
    }
}

@Preview(name = "default welcome")
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SelectUserTypePreview() {
    BandmeAppMobileTheme {
        SelectUserTypeContent()
    }
}

@Composable
fun UserTypeCard(userType: String, iconId: Int, onClick: () -> Unit, isEnable: Boolean) {
    Button(
        modifier = Modifier
            .width(110.dp)
            .height(110.dp),
        onClick = {},
        elevation = ButtonDefaults.elevation(6.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primaryVariant,
            disabledBackgroundColor = Gray600
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
            Text(text = userType, color = MaterialTheme.colors.onPrimary, fontSize = 13.sp)
        }
    }
}