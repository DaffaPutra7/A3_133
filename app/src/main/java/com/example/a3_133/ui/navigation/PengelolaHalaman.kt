package com.example.a3_133.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a3_133.ui.home.DestinasiHomeScreen
import com.example.a3_133.ui.home.HomeScreen
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
import com.example.a3_133.ui.view.merk.DestinasiDetailMerk
import com.example.a3_133.ui.view.merk.DetailMerkView
import com.example.a3_133.ui.view.merk.UpdateMerkView
import com.example.a3_133.ui.view.pemasok.DestinasiDetailPemasok
import com.example.a3_133.ui.view.pemasok.DetailPemasokView
import com.example.a3_133.ui.view.pemasok.UpdatePemasokView
import com.example.a3_133.ui.view.produk.DestinasiDetailProduk
import com.example.a3_133.ui.view.produk.DestinasiEntryProduk
import com.example.a3_133.ui.view.produk.DestinasiHomeProduk
import com.example.a3_133.ui.view.produk.DetailProdukView
import com.example.a3_133.ui.view.produk.EntryProdukScreen
import com.example.a3_133.ui.view.produk.HomeProdukScreen
import com.example.a3_133.ui.view.produk.UpdateProdukView

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = DestinasiHomeScreen.route,
        modifier = Modifier
    ) {
        // Home
        composable(DestinasiHomeScreen.route) {
            HomeScreen(
                onHalamanStart = {
                    navController.navigate(DestinasiHomeProduk.route)
                },
                modifier = Modifier
            )
        }

        // Merk
        composable(DestinasiHome.route) {
            MerkHomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntry.route)},
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailMerk.route}/$id")
                },
                navigateToUpdate = { id ->
                    navController.navigate("update_merk/$id") // Navigasi ke update
                },
                navigateBack = {navController.navigate(DestinasiHomeProduk.route) {
                    popUpTo(DestinasiHomeProduk.route) {
                        inclusive = true
                    }
                } }
            )
        }

        composable(
            route = "${DestinasiDetailMerk.route}/{id}",
            arguments = listOf(navArgument(name = "id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            DetailMerkView(
                idMerk = id,
                navigateBack = {
                    navController.navigateUp()
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

        // Pemasok
        composable(DestinasiHomePemasok.route) {
            PemasokHomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryPemasok.route)},
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailPemasok.route}/$id")
                },
                navigateToUpdate = { id ->
                    navController.navigate("update_pemasok/$id") // Navigasi ke update
                },
                navigateBack = {navController.navigate(DestinasiHomeProduk.route) {
                    popUpTo(DestinasiHomeProduk.route) {
                        inclusive = true
                    }
                } }
            )
        }

        composable(
            route = "${DestinasiDetailPemasok.route}/{id}",
            arguments = listOf(navArgument(name = "id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            DetailPemasokView(
                idPemasok = id,
                navigateBack = {
                    navController.navigateUp()
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

        // Kategori
        composable(DestinasiHomeKategori.route) {
            KategoriHomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryKategori.route)},
                navigateToUpdate = { id ->
                    navController.navigate("update_kategori/$id") // Navigasi ke update
                },
                navigateBack = {navController.navigate(DestinasiHomeProduk.route) {
                    popUpTo(DestinasiHomeProduk.route) {
                        inclusive = true
                    }
                } }
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

        // Produk
        composable(DestinasiHomeProduk.route) {
            HomeProdukScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryProduk.route) },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailProduk.route}/$id")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeScreen.route) {
                        popUpTo(DestinasiHomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onProdukClick = {
                    navController.navigate(DestinasiHomeProduk.route)
                },
                onPemasokClick = {
                    navController.navigate(DestinasiHomePemasok.route)
                },
                onMerkClick = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }

        composable(DestinasiEntryProduk.route) {
            EntryProdukScreen(navigateBack = {
                navController.navigate(DestinasiHomeProduk.route) {
                    popUpTo(DestinasiHomeProduk.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = "${DestinasiDetailProduk.route}/{id}",
            arguments = listOf(navArgument(name = "id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            DetailProdukView(
                idProduk = id,
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToEdit = { idProduk ->
                    navController.navigate("update_produk/$idProduk")
                },
                navigateToKategori = {
                    navController.navigate(DestinasiHomeKategori.route)
                }
            )
        }

        composable(
            route = "update_produk/{id}",
            arguments = listOf(navArgument(name = "id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            UpdateProdukView(
                idProduk = id,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}