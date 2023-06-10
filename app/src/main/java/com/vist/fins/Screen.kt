package com.vist.fins

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object HomeScreen: Screen("home_screen")
    object ProfileScreen: Screen("profile_screen")
    object FinancialCategoryListScreen: Screen("financial_category_list_screen")
    object DetailScreen: Screen("detail_screen")
    object CreateNewFinancialOperationScreen: Screen("create_new_financial_operation_screen")
    object CreateCategoryScreen: Screen("create_category_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
