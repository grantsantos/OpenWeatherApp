package com.openweatherapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FeelsLikeItem(modifier: Modifier = Modifier, feelsLike: Double?) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = "$feelsLike",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.W600,
                        fontStyle = FontStyle.Normal
                    )
                )
                Text(
                    text = "°C",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        fontStyle = FontStyle.Normal,
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "Feels Like",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400,
                    fontStyle = FontStyle.Normal
                )
            )
        }
    }

}

@Preview
@Composable
private fun FeelsLikeItemPreview() {
    FeelsLikeItem(feelsLike = 24.00)
}

/*
@Preview
@Composable
private fun FeelsLikeItemPreview()
    //FeelsLikeItem(feelsLike = 24.00)
}*/
