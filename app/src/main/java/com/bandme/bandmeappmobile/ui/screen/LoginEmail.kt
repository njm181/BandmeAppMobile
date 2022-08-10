package com.bandme.bandmeappmobile.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bandme.bandmeappmobile.ui.theme.BandmeAppMobileTheme

@Composable
fun LoginEmail() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Ingrese su email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )
        }
    }
}

@Preview(name = "default")
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginEmailPreview() {
    BandmeAppMobileTheme {
        LoginEmail()
    }
}