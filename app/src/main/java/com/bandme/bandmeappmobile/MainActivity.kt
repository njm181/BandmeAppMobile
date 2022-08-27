package com.bandme.bandmeappmobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bandme.bandmeappmobile.ui.screen.WelcomeScreen
import com.bandme.bandmeappmobile.ui.theme.BandmeAppMobileTheme
import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModel()
    private val RC_SIGN_IN = 0
    private lateinit var googleSignInClient: GoogleSignInClient
    private val EMAIL = "email"

    private val REQUEST_CODE = 1000
    private val SCOPES = "user-read-recently-played,user-library-modify,user-read-email,user-read-private"
    private val clientId = "28f2c16b039c4ef68fbbb9ef095a6c42"
    private val redirectUri = "http://com.bandme.bandmeappmobile/callback"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BandmeAppMobileTheme(darkTheme = false) {
                //LoginEmailScren(viewModel = loginViewModel)
                //LoginPasswordScreen(viewModel = loginViewModel)
                //ValidateResetEmailScreen(viewModel = loginViewModel)
                //ValidateResetCodeScreen(viewModel = loginViewModel)
                WelcomeScreen(
                    googleSignIn = { googleSignIn() },
                    spotifySignIn = { spotifySignIn() }
                )
            }
        }
    }

    private fun spotifySignIn(){
        val builder =
            AuthorizationRequest.Builder(clientId, AuthorizationResponse.Type.TOKEN, redirectUri)

        builder.setScopes(arrayOf(SCOPES))
        val request = builder.build()

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            handleSignData(data)
        }
        // Check if result comes from the correct activity
        if (requestCode === REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, data)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    Toast.makeText(this, "ACCESS TOKEN: ${response.accessToken}", Toast.LENGTH_SHORT).show()
                }
                AuthorizationResponse.Type.ERROR -> {
                    Toast.makeText(this, "ACCESS TOKEN: ${response.error}", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //response.error
                    Toast.makeText(this, "ELSE", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    //todo llevarme google a un viewmodel
    //region Google Sign In
    private fun isUserGoogleSignedIn(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        return account != null
    }

    private fun getGoogleSinginClient() : GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("193465647450-44nmir3ssk2s214mp27i5m6ej1bbhpsl.apps.googleusercontent.com")
            .requestProfile()
            .build()
        return GoogleSignIn.getClient(this, gso);
    }

    private fun googleSignIn(){
        if (!isUserGoogleSignedIn()){
            val signInIntent = getGoogleSinginClient().signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        } else {
            val account = GoogleSignIn.getLastSignedInAccount(this)
            Toast.makeText(this, "Usuario ya logueado: ${account?.idToken}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun handleSignData(data: Intent?) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnCompleteListener {
                println("isSuccessful ${it.isSuccessful}")
                if (it.isSuccessful){
                    // user successfully logged-in
                    loginViewModel.setGoogleAccessToken(it.result?.idToken.orEmpty())
                    println("ID TOKEN --> ${it.result?.idToken}")
                    println("ACCOUNT --> ${it.result?.account}")
                    println("DISPLAYNAME --> ${it.result?.displayName}")
                    println("EMAIL --> ${it.result?.email}")
                } else {
                    // authentication failed
                    println("exception ${it.exception}")
                }
            }
    }


    fun signOut(){
        googleSignInClient?.signOut()?.addOnCompleteListener(this
        ) { Toast.makeText(this@MainActivity, "Signed Out", Toast.LENGTH_SHORT).show() }
    }

    //endregion Google Sign In

}
