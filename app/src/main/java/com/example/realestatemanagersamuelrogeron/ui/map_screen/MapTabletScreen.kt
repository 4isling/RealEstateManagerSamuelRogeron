package com.example.realestatemanagersamuelrogeron.ui.map_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.EstateItem
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapTabletScreen(
    estates: List<EstateWithDetails>,
    isEuro: Boolean,
    estateSelected: EstateWithDetails?,
    onEstateMakerClick: (Long) -> Unit = {},
    onEstateItemClick: (Long) -> Unit = {},
    onClickMap: () -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxSize().testTag("box googlemap"),
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    uiSettings = MapUiSettings(zoomControlsEnabled = false),
                    properties = MapProperties(
                        // mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.style_json)
                    ),
                    onMapClick = {
                        onClickMap()
                    }
                    /*cameraPositionState = CameraPositionState(
                        CameraPosition(
                            LatLng(22.5726, 88.3639), 12f, 0f, 0f
                        )
                    ),*/
                ) {
                    estates.forEach {
                        val id = it.estate.estateId
                        val markerState = rememberMarkerState(
                            position = LatLng(
                                it.estate.lat!!,
                                it.estate.lng!!
                            )
                        )
                        Marker(
                            state = markerState,
                            icon = setCustomMapIcon(
                                it.estate.title,
                                if (!isEuro) {
                                    it.estate.price.toString() + if (it.estate.typeOfOffer == "Rent") {
                                        " $/m"
                                    } else {
                                        " $"
                                    }
                                } else {
                                    val price = (it.estate.price * 0.92).toInt()
                                    price.toString() + if (it.estate.typeOfOffer == "Rent") {
                                        " €/m"
                                    } else {
                                        " €"
                                    }
                                }
                            ),
                            onClick = {
                                onEstateMakerClick(id)
                                false
                            },
                        )
                    }
                }
                if (estateSelected != null) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                    ) {
                        EstateItem(
                            entry = estateSelected,
                            onEstateItemClick = onEstateItemClick,
                            modifier = Modifier.wrapContentSize(),
                            isEuro = isEuro,
                        )
                    }
                }
            }
        }
    }
}
