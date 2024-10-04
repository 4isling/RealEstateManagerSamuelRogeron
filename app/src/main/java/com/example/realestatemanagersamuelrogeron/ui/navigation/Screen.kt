package com.example.realestatemanagersamuelrogeron.ui.navigation

const val ROOT_GRAPH_ROUTE = "root"
const val TABLET_GRAPH_ROUTE = "root"

sealed class Screen(val route: String){

    object EstateList : Screen(route = "$ROOT_GRAPH_ROUTE/list_screen")
    object EstateDetail : Screen(route = "$ROOT_GRAPH_ROUTE/estate_detail_screen")
    object AddEstate : Screen(route = "$ROOT_GRAPH_ROUTE/add_screen")
    object Map : Screen(route = "$ROOT_GRAPH_ROUTE/map_screen")
    object Settings : Screen(route = "$ROOT_GRAPH_ROUTE/settings_screen")
    object Edit : Screen(route = "$ROOT_GRAPH_ROUTE/edit")

    fun  withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
sealed class TabletScreen(val route: String){
    object AddEstate : Screen(route = "$TABLET_GRAPH_ROUTE/add_screen")
    object Settings : Screen(route = "$TABLET_GRAPH_ROUTE/settings_screen")
    object TabletLayout : Screen(route = "$TABLET_GRAPH_ROUTE/tablet")
    object EstateList : Screen(route = "$TABLET_GRAPH_ROUTE/list_screen")
    object EstateDetail : Screen(route = "$TABLET_GRAPH_ROUTE/estate_detail_screen")
    object Map : Screen(route = "$TABLET_GRAPH_ROUTE/tablet/map_screen")
    fun  withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}