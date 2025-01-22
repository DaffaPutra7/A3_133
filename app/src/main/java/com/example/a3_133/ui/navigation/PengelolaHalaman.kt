package com.example.a3_133.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a3_133.ui.view.DestinasiEntry
import com.example.a3_133.ui.view.DestinasiHome
import com.example.a3_133.ui.view.EntryMerkScreen
import com.example.a3_133.ui.view.MerkHomeScreen
import com.example.a3_133.ui.view.UpdateMerkView

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {

        composable(DestinasiHome.route) {
            MerkHomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntry.route)},
                navigateToUpdate = { id ->
                    navController.navigate("update_merk/$id") // Navigasi ke update
                }
            )
        }

        composable(DestinasiEntry.route) {
            EntryMerkScreen(navigateBack = {
                navController.navigate(DestinasiHome.route) {
                    popUpTo(DestinasiHome.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = "update_merk/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            UpdateMerkView(
                id = id,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}