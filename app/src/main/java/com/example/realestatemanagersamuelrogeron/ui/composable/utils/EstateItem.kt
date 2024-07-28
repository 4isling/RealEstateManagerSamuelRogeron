package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EstateItem(
    media: List<EstateMedia>,
    entry: Estate,
    onEstateItemClick: (Long) -> Unit = {},
    modifier: Modifier
) {
    val padding = 4.dp
    Card(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .padding(padding)
            .clickable(enabled = true, onClick = {
                onEstateItemClick(entry.estateId)
            }),
        elevation = CardDefaults.cardElevation(), // Set the desired elevation value here
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                .padding(padding)
                .clickable(enabled = true, onClick = {
                    onEstateItemClick(entry.estateId)
                })
        ) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(padding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .wrapContentHeight()
                        .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                ) {
                    if (media.isNotEmpty()) {
                        MediaCard(
                            filePath = media[0].uri, // Replace with your image resource
                            modifier = Modifier
                                .padding(padding)
                                .height(130.dp)
                                .align(Alignment.Center)
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.no_media_ink),
                            contentDescription = "list media is empty",
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier
                                .size(125.dp)
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                    }
                }
                Spacer(modifier = Modifier.width(padding))
                Column(  //
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    Text(
                        text = entry.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onPrimary,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = padding)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(padding))
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .background(
                                MaterialTheme.colorScheme.onPrimary,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(padding)
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(padding)
                        ) {
                            FlowRow(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = entry.address + ", ",
                                    fontSize = 12.sp,
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                Text(
                                    text = entry.zipCode,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                Text(
                                    text = entry.city + ", ",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                Text(
                                    text = entry.region + ", ",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                Text(
                                    text = entry.country,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }

                            Spacer(modifier = Modifier.height(padding))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .background(
                                            MaterialTheme.colorScheme.secondary,
                                            RoundedCornerShape(8.dp),
                                        ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = " " + entry.surface.toString() + " m² ",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSecondary,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .wrapContentSize()
                                        .background(
                                            MaterialTheme.colorScheme.secondary,
                                            RoundedCornerShape(8.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = " " + entry.price.toString() +  if (entry.typeOfOffer == "Rent") {
                                            " $/m"
                                        } else {
                                            " $"
                                        },
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSecondary,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EstateItem(
    entry: EstateWithDetails,
    onEstateItemClick: (Long) -> Unit = {},
    modifier: Modifier
) {
    val padding = 4.dp
    Card(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .padding(padding)
            .clickable(enabled = true, onClick = {
                onEstateItemClick(entry.estate.estateId)
            }),
        elevation = CardDefaults.cardElevation(), // Set the desired elevation value here
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                .padding(padding)
                .clickable(enabled = true, onClick = {
                    onEstateItemClick(entry.estate.estateId)
                })
        ) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(padding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .wrapContentHeight()
                        .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                ) {
                    if (entry.estatePictures.isNotEmpty()) {
                        MediaCardList(
                            mediaList = entry.estatePictures, // Replace with your image resource
                            modifier = Modifier
                                .padding(padding)
                                .height(130.dp)
                                .align(Alignment.Center)
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.no_media_ink),
                            contentDescription = "list media is empty",
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier
                                .size(125.dp)
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                    }
                }
                Spacer(modifier = Modifier.width(padding))
                Column(  //
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    Text(
                        text = entry.estate.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onPrimary,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = padding)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(padding))
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .background(
                                MaterialTheme.colorScheme.onPrimary,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(padding)
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(padding)
                        ) {
                            FlowRow(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = entry.estate.address + ", ",
                                    fontSize = 12.sp,
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                Text(
                                    text = entry.estate.zipCode,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                Text(
                                    text = entry.estate.city + ", ",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                Text(
                                    text = entry.estate.region + ", ",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                Text(
                                    text = entry.estate.country,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }

                            Spacer(modifier = Modifier.height(padding))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .background(
                                            MaterialTheme.colorScheme.secondary,
                                            RoundedCornerShape(8.dp),
                                        ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = " " + entry.estate.surface.toString() + " m² ",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSecondary,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .wrapContentSize()
                                        .background(
                                            MaterialTheme.colorScheme.secondary,
                                            RoundedCornerShape(8.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = " " + entry.estate.price.toString() + if (entry.estate.typeOfOffer == "Rent") {
                                            " $/m"
                                        } else {
                                             " $"
                                        },
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSecondary,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EstateItemPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
                .height(670.dp)
                .width(360.dp)
        ) {
            EstateItem(
                media = listOf(
                    EstateMedia(
                        0,
                        0,
                        "/storage/self/Pictures/IMG_20230906_164952.jpg",
                        "image/",
                        "Facade"
                    )
                ),
                entry = Estate(
                    estateId = 0,
                    title = "La forge D'Entre Mont",
                    typeOfOffer = "Price",
                    typeOfEstate = "House",
                    etage = "2",
                    address = "24 Route De La Vallée",
                    zipCode = "06910",
                    region = "PACA",
                    country = "France",
                    city = "Pierrefeu",
                    description = "",
                    addDate = 0,
                    sellDate = 0,
                    agent = "",
                    price = 250000,
                    surface = 200,
                    nbRooms = 4,
                    status = true,
                ),
                onEstateItemClick = {},
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
    }
}

fun NavController.safeNavigate(directions: String) {
    currentBackStackEntry?.arguments?.putBoolean("allowRecreation", true)
    try {
        navigate(directions)
    } catch (e: Exception) {
        Log.e("Navigation", e.toString())
    }
}