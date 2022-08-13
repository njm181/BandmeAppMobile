package com.bandme.bandmeappmobile.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bandme.bandmeappmobile.ui.theme.BandmeAppMobileTheme

@Composable
fun BaseAlertDialog(
    isVisible: Boolean = false,
    onDismissAction: () -> Unit,
    onAffirmativeAction: () -> Unit,
    title: String = "",
    isFailure: Boolean = false,
    description: String = "",
    affirmativeTitle: String = "",
    dismissTitle: String = ""
) {
    if (isVisible) {
        androidx.compose.material.AlertDialog(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = { onDismissAction() },
            title = { Text(text = title, fontSize = 16.sp) },
            text = { Text(
                text = if (isFailure){
                    "Ocurrió un error inesperado, por favor vuelve a intentarlo más tarde."
                }else{
                     description
                     },
                textAlign = TextAlign.Justify,
                fontSize = 14.sp)
                   },
            dismissButton = {
                Button(
                    onClick = { onDismissAction() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (isFailure) "Intentar más tarde" else dismissTitle)
                }
            },
            confirmButton = {
                if (!isFailure){
                    Button(
                        onClick = {
                            onAffirmativeAction()
                            onDismissAction()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = affirmativeTitle)
                    }
                }else{
                    null
                }
            },
        )
    }
}

@Preview
@Composable
fun BaseAlertDialogPreview() {
    BandmeAppMobileTheme() {
        BaseAlertDialog(
            isVisible = true,
            onDismissAction = { /*TODO*/ },
            onAffirmativeAction = { /*TODO*/ },
            title = "Atención",
            description = "El email ingresado no existe o es incorrecto." +
                    " \nSi ya esta registrado vuelva a ingresarlo correctamente. " +
                    "\nSi no, puede registrarse ahora mismo.",
            affirmativeTitle = "Registrarme",
            dismissTitle = "Volver a intentar"
        )
    }

}