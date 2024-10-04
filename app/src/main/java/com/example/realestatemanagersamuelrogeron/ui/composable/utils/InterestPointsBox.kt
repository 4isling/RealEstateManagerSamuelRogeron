package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestPointsBox(
    modifier: Modifier,
    interestPointsList: List<EstateInterestPoints>,
    editEnable: Boolean = false,
    onClickRemove: (EstateInterestPoints) -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier.wrapContentSize(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        FlowRow(
            modifier = modifier.padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            interestPointsList.forEach { interestPoint ->
                InterestPointItem(
                    iconCode = interestPoint.iconCode,
                    text = interestPoint.interestPointsName,
                    editEnable = editEnable,
                    onClickRemove = {
                        onClickRemove(interestPoint)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun InterestPointsBoxPreview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            InterestPointsBox(
                modifier = Modifier,
                interestPointsList = predefinedInterestPoints,
            )
        }
    }
}