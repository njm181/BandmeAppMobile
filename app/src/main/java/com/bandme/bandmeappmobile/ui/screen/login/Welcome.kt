package com.bandme.bandmeappmobile.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.bandme.bandmeappmobile.R
import com.bandme.bandmeappmobile.ui.theme.BandmeAppMobileTheme
import com.bandme.bandmeappmobile.ui.utils.ValidateLoginGoogleState
import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel

@Composable
fun WelcomeScreen(
    googleSignIn: () -> Unit = {},
    spotifySignIn: () -> Unit = {},
    goToEmailSignIn: () -> Unit,
    onSocialMediaLogin: () -> Unit,
    onSocialMediaRegister: () -> Unit,
    viewModel: LoginViewModel
) {

    val googleResponse = viewModel.validateLoginGoogleStateFlow.collectAsState()
    when(googleResponse.value){
        is ValidateLoginGoogleState.SuccessIsRegister -> {
            //go to select user type
            println("GOOGLE TIENE CUENTA ===> GO TO SELECT USER TYPE")
        }
        is ValidateLoginGoogleState.SuccessIsLogin -> {
            //go to dashboard
            println("GOOGLE TIENE CUENTA ===> GO TO DASHBOARD")
        }
        is ValidateLoginGoogleState.Failure -> {
            //show modal error
        }
        else -> {/*to do nothing*/}
    }

    WelcomeContent(
        googleSignIn = { googleSignIn() },
        spotifySignIn = { spotifySignIn() },
        goToEmailSignIn = { goToEmailSignIn() }
    )
}

@Composable
fun WelcomeContent(
    googleSignIn: () -> Unit = {},
    spotifySignIn: () -> Unit = {},
    goToEmailSignIn: () -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        Column(
            Modifier
                .weight(2f)) {
            lottieAnim()

        }
        Column(Modifier.weight(1f)) {
            Button(
                onClick = { goToEmailSignIn() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Iniciar con Email")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { googleSignIn() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text(text = "Iniciar con Google")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { spotifySignIn() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text(text = "Iniciar con Spotify")
            }
        }
    }
}

@Composable
fun lottieAnim() {
    // remember lottie composition, which
    // accepts the lottie composition result
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            // here `code` is the file name of lottie file
            // use it accordingly
            .RawRes(R.raw.player)
    )
    // to control the animation
    val progress by animateLottieCompositionAsState(
        // pass the composition created above
        composition,
        // Iterates Forever
        iterations = LottieConstants.IterateForever,
        // pass isPlaying we created above,
        // changing isPlaying will recompose
        // Lottie and pause/play
        isPlaying = true,
        // pass speed we created above,
        // changing speed will increase Lottie
        //speed = speed,
        // this makes animation to restart
        // when paused and play
        // pass false to continue the animation
        // at which is was paused
        restartOnPlay = false
    )
    LottieAnimation(composition = composition, progress = progress)
}

@Preview(name = "default welcome")
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WelcomeContentPreview() {
    BandmeAppMobileTheme {
        WelcomeContent({})
    }
}