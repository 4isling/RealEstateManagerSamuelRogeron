package com.example.realestatemanagersamuelrogeron.ui.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.data.event.AddScreenEvent
import com.example.realestatemanagersamuelrogeron.data.state.AddEstateState
import com.example.realestatemanagersamuelrogeron.ui.composable.add_screen.AddListOfPictures
import com.example.realestatemanagersamuelrogeron.ui.composable.add_screen.InterestPointsTextField
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.AddEstateViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun AddEstateScreen(
    navController: NavController,
    viewModel: AddEstateViewModel = hiltViewModel()
) {
    val pTitle by viewModel.estateTitle.collectAsState("")
    val pAddress by viewModel.estateAddress.collectAsState("")
    val pZipCode by viewModel.estateZipCode.collectAsState("")
    val pCity by viewModel.estateCity.collectAsState("")
    val pDescriptions by viewModel.estateDescriptions.collectAsState("")

    val pType by viewModel.estateType.collectAsState(initial = "")
    val pOffer by viewModel.estateOffer.collectAsState("")
    val pRent by viewModel.estateRent.collectAsState("")
    val pSell by viewModel.estateSellingPrice.collectAsState("")
    val pEtages by viewModel.estateEtages.collectAsState("")
    val pSurface by viewModel.estateSurface.collectAsState("")
    val pNbRoom by viewModel.estateNbRooms.collectAsState("0")

    val titleFilled by viewModel.titleIsNotEmpty
    val addressFilled by viewModel.addressIsNotEmpty
    val zipCodeFilled by viewModel.zipCodeIsNotEmpty
    val cityFilled by viewModel.cityIsNotEmpty
    val typeFilled by viewModel.typeIsNotEmpty
    val descriptionFilled by viewModel.descriptionsIsNotEmpty
    val sellPricefilled by viewModel.sellingPriceIsNotEmpty
    val rentFilled by viewModel.rentIsNotEmpty
    val offerFilled by viewModel.offerIsNotEmpty
    val etageFilled by viewModel.etageIsNotEmpty
    val interestPointsFilled by viewModel.onInterestPointsIsNotEmpty
    val surfaceFilled by viewModel.surfaceIsNotEmpty
    val enableSave by viewModel.enableSave

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
                .safeContentPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /**
             * ListOf uri
             */
            item {
                AddListOfPictures(viewModel)
            }
            /**
             * nbRoom selection
             */
            item {
                Surface(
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        val pCount: MutableState<Int> = remember {
                            mutableStateOf(value = 0)
                        }
                        Text(text = "Rooms:")
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            IconButton(
                                onClick = {
                                    pCount.value--
                                    viewModel.onRoomValueChange(pCount.value.toString())
                                },
                                Modifier.size(32.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_remove_circle_24),
                                    contentDescription = "remove"
                                )
                            }
                            Text(
                                text = pNbRoom,
                                modifier = Modifier.padding(8.dp)
                            )
                            IconButton(
                                onClick = {
                                    pCount.value++
                                    viewModel.onRoomValueChange(pCount.value.toString())
                                },
                                Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.AddCircle,
                                    contentDescription = "add"
                                )
                            }
                        }
                    }
                }
            }
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = pTitle,
                    onValueChange = {
                        viewModel.onTitleValueChange(it)
                    },
                    placeholder = {
                        Text(text = "Title")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                )
            }



            item {
                OutlinedTextField(
                    value = pAddress,
                    onValueChange = {
                        viewModel.onAddressValueChange(it)
                    },
                    placeholder = {
                        Text(text = "Address")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location Icon"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    isError = addressFilled
                )
            }
            item {
                Row(modifier = Modifier.fillMaxWidth()) {

                    OutlinedTextField(
                        value = pCity,
                        onValueChange = {
                            viewModel.onCityValueChange(it)
                        },
                        placeholder = {
                            Text(text = "City")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location icon"
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = pZipCode,
                        onValueChange = {
                            viewModel.onZipCodeValueChange(it)
                        },
                        placeholder = {
                            Text("ZipCode")
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true
                    )
                }
            }
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    //
                    // Declaring a boolean value to store
                    // the expanded state of the Text Field
                    var mExpanded by remember { mutableStateOf(false) }

                    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

                    // Up Icon when expanded and down icon when collapsed
                    val iconDrop = if (mExpanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown

                    Column {

                        // Create an Outlined Text Field
                        // with icon and not expanded
                        OutlinedTextField(
                            value = pType,
                            onValueChange = {
                                viewModel.onTypeValueChange(it)
                            },
                            modifier = Modifier
                                .clickable { mExpanded = !mExpanded }
                                .onGloballyPositioned { coordinates ->
                                    // This value is used to assign to
                                    // the DropDown the same width
                                    mTextFieldSize = coordinates.size.toSize()
                                },
                            placeholder =
                            {
                                Text("Type of Estate")
                            },
                            trailingIcon = {
                                Icon(iconDrop, "contentDescription")
                            },
                            enabled = false,
                            isError = !typeFilled

                        )
                        val choices = listOf("House", "Apartment", "Garage", "Land")
                        // Create a drop-down menu with list of cities,
                        // when clicked, set the Text Field text as the choice selected
                        DropdownMenu(
                            expanded = mExpanded,
                            onDismissRequest = { mExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) {
                                    mTextFieldSize.width.toDp()
                                })
                        ) {
                            choices.forEach { uChoice ->
                                DropdownMenuItem(onClick = {
                                    viewModel.onTypeValueChange(uChoice)
                                    mExpanded = false
                                }) {
                                    Text(text = uChoice)
                                }
                            }
                        }
                    }
                    // Declaring a boolean value to store
                    // the expanded state of the Text Field
                    var mExpanded2 by remember { mutableStateOf(false) }

                    var mTextFieldSize2 by remember { mutableStateOf(Size.Zero) }

                    // Up Icon when expanded and down icon when collapsed
                    val icon = if (mExpanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown

                    Column {
                        // Create an Outlined Text Field
                        // with icon and not expanded
                        OutlinedTextField(
                            value = pOffer,
                            onValueChange = {
                                viewModel.onOfferValueChange(it)
                            },
                            modifier = Modifier
                                .clickable { mExpanded2 = !mExpanded2 }
                                .onGloballyPositioned { coordinates ->
                                    // This value is used to assign to
                                    // the DropDown the same width
                                    mTextFieldSize2 = coordinates.size.toSize()
                                },
                            placeholder = {
                                Text(text = "Offer")
                            },
                            trailingIcon = {
                                Icon(icon, "contentDescription")
                            },
                            enabled = false

                        )

                        /**
                         * Type Of Offer Menu
                         */
                        DropdownMenu(
                            expanded = mExpanded2,
                            onDismissRequest = { mExpanded2 = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { mTextFieldSize2.width.toDp() })
                        ) {
                            val choices = listOf("Rent", "Sell", "Rent or Sell")
                            choices.forEach { uChoice ->
                                DropdownMenuItem(onClick = {
                                    viewModel.onOfferValueChange(uChoice)
                                    mExpanded2 = false
                                }) {
                                    Text(text = uChoice)
                                }
                            }
                        }
                        /**
                         * depending of the type of offer will show different textfield
                         */
                        when (pOffer) {
                            "Rent" -> {
                                OutlinedTextField(
                                    value = pRent,
                                    onValueChange = {
                                        viewModel.onRentValueChange(it)
                                    },
                                    placeholder = {
                                        Text(text = "Rent")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    ),
                                    singleLine = true
                                )
                            }

                            "Sell" -> {
                                OutlinedTextField(
                                    value = pSell,
                                    onValueChange = {
                                        viewModel.onSellingPriceValueChange(it)
                                    },
                                    placeholder = {
                                        Text(text = "Sell at")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    ),
                                    singleLine = true
                                )
                            }

                            "Rent or Sell" -> {
                                Row(Modifier.fillMaxWidth()) {

                                    OutlinedTextField(
                                        value = pSell,
                                        onValueChange = {
                                            viewModel.onSellingPriceValueChange(it)
                                        },
                                        placeholder = {
                                            Text(text = "Sell at")
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Done
                                        ),
                                        singleLine = true

                                    )
                                    OutlinedTextField(
                                        value = pRent,
                                        onValueChange = {
                                            viewModel.onRentValueChange(it)
                                        },
                                        placeholder = {
                                            Text(text = "Rent at")
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Done
                                        ),
                                        singleLine = true
                                    )
                                }
                            }
                        }
                    }
                }
            }
            /**
             * Surface
             */
            item {
                OutlinedTextField(
                    value = pSurface,
                    onValueChange = {
                        viewModel.onSurfaceValueChange(it)
                    },
                    placeholder = {
                        Text(text = "Surface")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
            }
            /**
             * Etages
             */
            item {
                OutlinedTextField(
                    value = pEtages,
                    onValueChange = {
                        viewModel.onEtageValueChange(it)
                    },
                    placeholder = {
                        Text(text = "Etage")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
            }
            /**
             * Description
             */
            item {
                OutlinedTextField(
                    value = pDescriptions,
                    onValueChange = {
                        viewModel.onDescriptionValueChange(it)
                    },
                    placeholder = {
                        Text(text = "Description")
                    }
                )
            }
            /**
             * interestPoints
             */
            item {
                InterestPointsTextField(
                    modifier = Modifier.fillMaxWidth(),
                    viewModel = viewModel
                )
            }
            /**
             * Save button
             */
            item {
                Button(
                    onClick = {
                        Log.i("AddEstateScreen", "button click saveEnable:$enableSave")
                        if (enableSave==true){
                            viewModel.onSaveButtonClick()
                            navController.navigate(Screen.EstateList.route)
                        }
                    }
                ) {
                    Row(modifier = Modifier.clickable {
                        Log.i("AddEstateScreen", "raw click saveEnable:$enableSave")
                        if (enableSave==true){

                            viewModel.onSaveButtonClick()
                            navController.navigate(Screen.EstateList.route)
                        }
                    }
                    ) {
                        Text(text = "Save")
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "SaveEstate btn",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SelectNumberOf(
    what: String,
    count: String,

    ) {
    Surface(
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val pCount: MutableState<Int> = remember {
                mutableStateOf(value = 0)
            }
            Text(text = "$what :")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { pCount.value-- },
                    Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_remove_circle_24),
                        contentDescription = "remove"
                    )
                }
                Text(
                    text = pCount.value.toString(),
                    Modifier.padding(8.dp)
                )
                IconButton(
                    onClick = { pCount.value++ },
                    Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircle,
                        contentDescription = "add"
                    )
                }
            }
        }
    }
}

@Composable
fun AddTextBox(what: String) {
    Row {
        Text(text = "$what :")


    }
}