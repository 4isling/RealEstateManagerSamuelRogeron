package com.example.realestatemanagersamuelrogeron.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Bathtub
import androidx.compose.material.icons.rounded.BeachAccess
import androidx.compose.material.icons.rounded.Bed
import androidx.compose.material.icons.rounded.CropFree
import androidx.compose.material.icons.rounded.DensitySmall
import androidx.compose.material.icons.rounded.Euro
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.FireHydrantAlt
import androidx.compose.material.icons.rounded.House
import androidx.compose.material.icons.rounded.Landscape
import androidx.compose.material.icons.rounded.LocalAirport
import androidx.compose.material.icons.rounded.LocalHospital
import androidx.compose.material.icons.rounded.LocalMall
import androidx.compose.material.icons.rounded.LocalPolice
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.Park
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Pool
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Terrain
import androidx.compose.material.icons.rounded.Train

object RemIcon {
    //NavIcons
    val Add = Icons.Rounded.Add
    val ArrowBack = Icons.AutoMirrored.Rounded.ArrowBack
    val Map = Icons.Rounded.Map
    val Setting = Icons.Rounded.Settings
    val List = Icons.Rounded.DensitySmall
    val Search = Icons.Rounded.Search
    // HouseIcons
    val Rooms = Icons.Rounded.House
    val Bathroom = Icons.Rounded.Bathtub
    val Bed = Icons.Rounded.Bed
    val Location = Icons.Rounded.Place
    val Surface = Icons.Rounded.CropFree
    //Other
    val Remove = Icons.Default.RemoveCircleOutline
    val Edit = Icons.Default.Edit
    val Fav = Icons.Rounded.Star
    val Dollar = Icons.Rounded.AttachMoney
    val Euro = Icons.Rounded.Euro
    val Filter = Icons.Rounded.FilterList

    val Market = Icons.Rounded.LocalMall
    val Train = Icons.Rounded.Train
    val Park = Icons.Rounded.Park
    val Airport = Icons.Rounded.LocalAirport
    val Sea = Icons.Rounded.BeachAccess
    val Pool = Icons.Rounded.Pool
    val School = Icons.Rounded.School
    val Restaurant = Icons.Rounded.Restaurant
    val Police = Icons.Rounded.LocalPolice
    val Firefighter = Icons.Rounded.FireHydrantAlt
    val Money = Icons.Rounded.Money
    val Mountain = Icons.Rounded.Terrain
    val Landscape = Icons.Rounded.Landscape
    val Hospital = Icons.Rounded.LocalHospital

    val iconMapping = mapOf(
        0 to Rooms,
        1 to Landscape,
        2 to Bathroom,
        3 to Bed,
        4 to Location,
        5 to Market,
        6 to Restaurant,
        7 to Train,
        8 to Park,
        9 to Airport,
        10 to Sea,
        11 to Pool,
        12 to School,
        13 to Police,
        14 to Firefighter,
        15 to Money,
        16 to Dollar,
        17 to Euro,
        18 to Mountain,
        19 to Hospital
    )
}