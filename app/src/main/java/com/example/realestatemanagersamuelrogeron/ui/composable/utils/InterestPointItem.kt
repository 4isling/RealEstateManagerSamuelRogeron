package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.RemIcon

@Composable
fun InterestPointItem(
    iconCode: Int,
    text: String,
    editEnable: Boolean = false,
    onClickRemove: () -> Unit = {},
) {
    Card(
        modifier = Modifier.padding(4.dp)
            .wrapContentWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(4.dp), // Set the desired elevation value here
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .padding(4.dp)
                .wrapContentWidth(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Icon(
                    imageVector = RemIcon.iconMapping[iconCode]!!,
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = " $text ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                if (editEnable) {
                    Icon(
                        imageVector = RemIcon.Remove,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onClickRemove() },
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun InterestPointItem(
    interestPoints: EstateInterestPoints,
    editEnable: Boolean = false,
    onClickRemove: () -> Unit = {},
) {
    Card(
        modifier = Modifier.padding(4.dp)
            .wrapContentWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(4.dp), // Set the desired elevation value here
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .padding(4.dp)
                .wrapContentWidth(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Icon(
                    imageVector = RemIcon.iconMapping[interestPoints.iconCode]!!,
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = " ${interestPoints.interestPointsName} ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                if (editEnable) {
                    Icon(
                        imageVector = RemIcon.Remove,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onClickRemove() },
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun InterestPointItemPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
                .height(360.dp)
                .width(360.dp)
        ) {
            FlowRow(
                modifier = Modifier.padding(8.dp),
                content = {
                    InterestPointItem(2, text = "bathroom")
                    InterestPointItem(
                        iconCode = 3,
                        text = "bedroom",
                        editEnable = true,
                        onClickRemove = {})
                })

        }
    }
}