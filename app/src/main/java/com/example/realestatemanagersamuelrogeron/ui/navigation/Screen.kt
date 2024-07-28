package com.example.realestatemanagersamuelrogeron.ui.navigation

const val ROOT_GRAPH_ROUTE = "root"
const val LIST_GRAPH_ROUTE = "list"
const val DETAIL_GRAPH_ROUTE = "detail"
const val ADD_GRAPH_ROUTE = "add"

sealed class Screen(val route: String){


    object Main : Screen(route = "main_screen")
    object EstateList : Screen(route = "main_screen/list_screen")
    object EstateDetail : Screen(route = "main_screen/estate_detail_screen")
    object AddEstate : Screen(route = "main_screen/add_screen")
    object Map : Screen(route = "main_screen/map_screen")
    object Settings : Screen(route = "main_screen/settings_screen")

    fun  withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}