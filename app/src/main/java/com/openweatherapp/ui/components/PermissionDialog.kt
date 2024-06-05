package com.openweatherapp.ui.components

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDialog(
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOnGrantPermission: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = "Permission required")
        },
        text = {
            Text(
                text = if(isPermanentlyDeclined) {
                    "It seems you permanently declined precise location permission. " +
                            "You can go to the app settings to grant it."
                } else {
                    "This app needs access to your precise location"
                }
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (isPermanentlyDeclined) {
                        onGoToAppSettingsClick()
                    } else {
                        onOnGrantPermission()
                    }
                }
            ) {
                Text(
                    text = if(isPermanentlyDeclined) {
                        "Go to settings"
                    } else {
                        "Grant permission"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(
                    text = "Dismiss",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    )
}

