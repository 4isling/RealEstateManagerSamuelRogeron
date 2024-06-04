package com.example.realestatemanagersamuelrogeron.ui.detail_screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme

@Composable
fun DescriptionBox(
    description: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = description,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
    }
}

@Preview
@Composable
fun DescriptionBoxPreview(){
    AppTheme {
        Surface {
            DescriptionBox(description = "Spacious Living Areas: The villa features expansive open-plan living and dining areas with floor-to-ceiling windows, allowing natural light to flood the space and offering uninterrupted sea views.\n" +
                    "\n" +
                    "Gourmet Kitchen: A state-of-the-art kitchen equipped with top-of-the-line appliances, sleek cabinetry, and a large island, perfect for culinary enthusiasts and entertaining guests.\n" +
                    "\n" +
                    "Luxurious Bedrooms: Five spacious bedrooms, each with en-suite bathrooms, walk-in closets, and private terraces. The master suite includes a lavish bathroom with a soaking tub and a balcony with spectacular ocean views.\n" +
                    "\n" +
                    "Outdoor Oasis: The outdoor area is an entertainer’s dream, featuring a large infinity pool, a jacuzzi, a fully equipped outdoor kitchen, and multiple lounging areas. Beautifully landscaped gardens surround the property, providing privacy and a serene atmosphere.\n" +
                    "\n" +
                    "Exclusive Amenities: The villa includes a home theater, a private gym, a wine cellar, and a rooftop terrace with a lounge area and 360-degree views. Smart home technology ensures convenience and security throughout the property.\n" +
                    "\n" +
                    "Prime Location: Situated in one of Marbella’s most prestigious neighborhoods, the villa is just a short drive from world-class golf courses, fine dining, high-end shopping, and vibrant nightlife.\n" +
                    "\n" +
                    "This luxurious villa offers an unparalleled lifestyle, combining comfort, sophistication, and the ultimate in seaside living. It is a rare opportunity to own a piece of paradise in one of the most sought-after destinations in the world.", modifier = Modifier.wrapContentSize())
        }
    }
}