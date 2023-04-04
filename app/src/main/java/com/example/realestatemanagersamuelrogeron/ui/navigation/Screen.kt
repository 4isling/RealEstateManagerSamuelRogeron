package com.example.realestatemanagersamuelrogeron.ui.navigation

const val ROOT_GRAPH_ROUTE = "root"
const val LIST_GRAPH_ROUTE = "list"
const val DETAIL_GRAPH_ROUTE = "detail"
const val ADD_GRAPH_ROUTE = "add"

sealed class Screen(val route: String){
    object EstateList: Screen(route = "list_screen")
    object EstateDetail: Screen(route = "detail_screen")
    object AddEstate: Screen(route = "add_screen")

    fun  withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}