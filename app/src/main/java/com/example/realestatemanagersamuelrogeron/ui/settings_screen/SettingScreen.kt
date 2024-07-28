@file:OptIn(ExperimentalLayoutApi::class)

package com.example.realestatemanagersamuelrogeron.ui.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.InterestPointsBox
import com.example.realestatemanagersamuelrogeron.ui.settings_screen.viewmodel.SettingViewModel
import com.example.realestatemanagersamuelrogeron.ui.settings_screen.viewmodel.SettingViewState
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.RemIcon
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: SettingViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass,

    ) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(text = "Setting")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            paddingValues
            SettingScreenContent(
                viewState = uiState,
                onAddInterestPoint = viewModel::onInterestPointAdded,
                onDeleteInterestPoint = viewModel::onInterestPointDeleted,
                onCurrencyChange = viewModel::onCurrencyChange,
                windowSizeClass = windowSizeClass,
            )
        }
    )
}

@Composable
fun SettingScreenContent(
    viewState: SettingViewState,
    windowSizeClass: WindowSizeClass,
    onDeleteInterestPoint: (EstateInterestPoints) -> Unit = {},
    onAddInterestPoint: (String, Int) -> Unit,
    onCurrencyChange: (Boolean) -> Unit,
) {
    when (viewState) {

        is SettingViewState.Loading -> {
            CircularProgressIndicator()
        }

        is SettingViewState.Success -> {
            val showDeleteInterestPointDialog = remember {
                mutableStateOf<EstateInterestPoints?>(null)
            }
            val showAddInterestPointDialog = remember {
                mutableStateOf(false)
            }

            var selectedCurrency by remember {
                mutableStateOf("Dollar")
            }

            Column(modifier = Modifier.fillMaxSize()) {
                // Euro Section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                        .fillMaxHeight(0.4f)

                ) {
                    Checkbox(
                        checked = viewState.selectedCurrency,
                        onCheckedChange = {
                            onCurrencyChange(true)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Euro",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )

                    Checkbox(
                        checked = !viewState.selectedCurrency,
                        onCheckedChange = {
                            onCurrencyChange(false)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Dollar",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }

                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    InterestPointsBox(
                        modifier = Modifier.fillMaxWidth(),
                        editEnable = true,
                        interestPointsList = viewState.interestPoints,
                        onClickRemove = {
                            showDeleteInterestPointDialog.value = it
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(modifier = Modifier,
                        onClick = {
                            showAddInterestPointDialog.value = true
                        }
                    ) {
                        Text(text = "Add New Interest Point")
                    }
                }

                if (showDeleteInterestPointDialog.value != null) {
                    AlertDialog(
                        onDismissRequest = {
                            showDeleteInterestPointDialog.value = null
                        },
                        confirmButton = {
                            Button(onClick = {
                                onDeleteInterestPoint(showDeleteInterestPointDialog.value!!)
                            }) {
                                Text(text = "Yes")
                            }
                        },
                        dismissButton = {
                            Button(onClick = {
                                showDeleteInterestPointDialog.value = null
                            }) {
                                Text(text = "Cancel")
                            }
                        }
                    )
                }

                if (showAddInterestPointDialog.value) {
                    val allIconCodes = RemIcon.iconMapping.keys.toList()
                    val (newPointName, setNewPointName) = remember { mutableStateOf("") }
                    val (selectedIconCode, setSelectedIconCode) = remember { mutableIntStateOf(0) }
                    AlertDialog(
                        onDismissRequest = {
                            showAddInterestPointDialog.value = false
                        },
                        confirmButton = {
                            onAddInterestPoint(newPointName, selectedIconCode)
                        },
                        text = {
                            Column {
                                FlowRow {
                                    allIconCodes.forEach {
                                        iconCode ->
                                        Icon(
                                            imageVector = RemIcon.iconMapping[iconCode]!!,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .size(40.dp)
                                                .clickable {
                                                    setSelectedIconCode(iconCode)
                                                }
                                                .background(if (iconCode == selectedIconCode) MaterialTheme.colorScheme.primary else Color.Transparent)
                                        )
                                    }
                                    TextField(
                                        value = newPointName,
                                        onValueChange = setNewPointName,
                                        label = {
                                            Text("New Interest Point")
                                        },
                                        singleLine = true
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }

        is SettingViewState.Error -> {
            val state = viewState as SettingViewState.Error
            Text(text = "Error: ${state.exception}")
        }
    }
}

@ExperimentalMaterial3WindowSizeClassApi
@Preview
@Composable
fun SettingScreenPreview() {
    AppTheme {
        SettingScreenContent(
            viewState = SettingViewState.Success(
                predefinedInterestPoints,
                selectedCurrency = false
            ),
            onDeleteInterestPoint = {},
            onAddInterestPoint = { _, _ -> },
            onCurrencyChange = {},
            windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(360.dp,640.dp)),

            )
    }
}
