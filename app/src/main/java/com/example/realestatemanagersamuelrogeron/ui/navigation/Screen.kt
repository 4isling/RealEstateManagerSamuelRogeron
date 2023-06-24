package com.example.realestatemanagersamuelrogeron.ui.navigation

const val ROOT_GRAPH_ROUTE = "root"
const val LIST_GRAPH_ROUTE = "list"
const val DETAIL_GRAPH_ROUTE = "detail"
const val ADD_GRAPH_ROUTE = "add"

sealed class Screen(val route: String){
    object EstateList: Screen(route = "root/list_screen")
    object EstateDetail: Screen(route = "root/detail_screen"+ "/{estateId}")
    object AddEstate: Screen(route = "root/add_screen")

    fun  withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}