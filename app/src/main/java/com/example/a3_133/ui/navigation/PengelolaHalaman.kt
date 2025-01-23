package com.example.a3_133.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a3_133.ui.view.merk.DestinasiEntry
import com.example.a3_133.ui.view.kategori.DestinasiEntryKategori
import com.example.a3_133.ui.view.pemasok.DestinasiEntryPemasok
import com.example.a3_133.ui.view.merk.DestinasiHome
import com.example.a3_133.ui.view.kategori.DestinasiHomeKategori
import com.example.a3_133.ui.view.pemasok.DestinasiHomePemasok
import com.example.a3_133.ui.view.kategori.EntryKategoriScreen
import com.example.a3_133.ui.view.merk.EntryMerkScreen
import com.example.a3_133.ui.view.pemasok.EntryPemasokScreen
import com.example.a3_133.ui.view.kategori.KategoriHomeScreen
import com.example.a3_133.ui.view.merk.MerkHomeScreen
import com.example.a3_133.ui.view.pemasok.PemasokHomeScreen
import com.example.a3_133.ui.view.kategori.UpdateKategoriView
import com.example.a3_133.ui.view.merk.UpdateMerkView
import com.example.a3_133.ui.view.pemasok.UpdatePemasokView

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = DestinasiHomeKategori.route,
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

        composable(DestinasiHomePemasok.route) {
            PemasokHomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryPemasok.route)},
                navigateToUpdate = { id ->
                    navController.navigate("update_pemasok/$id") // Navigasi ke update
                }
            )
        }

        composable(DestinasiEntryPemasok.route) {
            EntryPemasokScreen(navigateBack = {
                navController.navigate(DestinasiHomePemasok.route) {
                    popUpTo(DestinasiHomePemasok.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = "update_pemasok/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            UpdatePemasokView(
                id = id,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(DestinasiHomeKategori.route) {
            KategoriHomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryKategori.route)},
                navigateToUpdate = { id ->
                    navController.navigate("update_kategori/$id") // Navigasi ke update
                }
            )
        }

        composable(DestinasiEntryKategori.route) {
            EntryKategoriScreen(navigateBack = {
                navController.navigate(DestinasiHomeKategori.route) {
                    popUpTo(DestinasiHomeKategori.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = "update_kategori/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            UpdateKategoriView(
                id = id,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}