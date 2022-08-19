package com.bandme.bandmeappmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bandme.bandmeappmobile.ui.screen.LoginPasswordScreen
import com.bandme.bandmeappmobile.ui.screen.ValidateResetEmailScreen
import com.bandme.bandmeappmobile.ui.theme.BandmeAppMobileTheme
import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BandmeAppMobileTheme {
                //LoginEmailScren(viewModel = loginViewModel)
                //LoginPasswordScreen(viewModel = loginViewModel)
                ValidateResetEmailScreen(viewModel = loginViewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BandmeAppMobileTheme {
        Greeting("Android")
    }
}