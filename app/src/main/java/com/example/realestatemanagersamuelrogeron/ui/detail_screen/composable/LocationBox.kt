package com.example.realestatemanagersamuelrogeron.ui.detail_screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.MediaCard
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme

@Composable
fun LocationBox(
    address: String,
    city: String,
    region: String,
    country: String,
    modifier: Modifier,
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .shadow(
                elevation = 8.dp
            )

    ) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, top = 10.dp)
        ) {
            BasicText(
                text = address,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            BasicText(
                text = city,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer

                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            BasicText(
                text = region,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            BasicText(
                text = country,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier
                    .graphicsLayer {
                        translationY = 24f
                    }
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 198.dp, top = 13.dp)
                .size(width = 132.dp, height = 96.dp)
                .background(
                    color = Color(0xB28644),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
        ) {
            MediaCard(
                filePath = " static map",
                modifier = Modifier //size
            )
        }
    }
}

@Preview
@Composable
fun LocationBoxPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
                .height(640.dp)
                .width(360.dp)
                .padding(all = 16.dp)
        ) {
            LocationBox(
                address = "21 Rue De France",
                city = "Nice",
                region = "Provence Alpes Cote D'Azur",
                country = "France",
                modifier = Modifier.fillMaxWidth().height(150.dp)
            )
        }
    }
}