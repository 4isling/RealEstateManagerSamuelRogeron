package com.example.realestatemanagersamuelrogeron.ui.add_screen.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class InterestPoint( val estateInterestPoints: Long = 0,
                          val interestPointName: String ="",
                          val icon: ImageVector = Icons.Default.Person,) {

}