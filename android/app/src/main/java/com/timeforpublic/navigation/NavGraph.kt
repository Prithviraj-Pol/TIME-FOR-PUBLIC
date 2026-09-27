package com.timeforpublic.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.timeforpublic.domain.model.UserRole
import com.timeforpublic.feature.auth.AuthViewModel
import com.timeforpublic.feature.auth.LoginScreen
import com.timeforpublic.feature.home.HomeScreen
import com.timeforpublic.feature.home.HomeViewModel
import com.timeforpublic.feature.officer.OfficerDashboardScreen
import com.timeforpublic.feature.officer.OfficerViewModel
import com.timeforpublic.feature.splash.SplashScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            val authViewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = { role ->
                    if (role == UserRole.OFFICER) {
                        navController.navigate(Routes.OFFICER_DASHBOARD) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Routes.CITIZEN_HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Routes.CITIZEN_HOME) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToSchemes = { /* Phase 3: navigate to Schemes */ },
                onNavigateToSchemeDetail = { schemeId -> /* Phase 3 */ },
                onNavigateToDocuments = { /* Phase 4 */ },
                onNavigateToOffices = { /* Phase 3 */ },
                onNavigateToAi = { /* Phase 4 */ },
                onNavigateToOfficerMode = {
                    navController.navigate(Routes.OFFICER_DASHBOARD)
                },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.CITIZEN_HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.OFFICER_DASHBOARD) {
            val officerViewModel: OfficerViewModel = hiltViewModel()
            OfficerDashboardScreen(
                viewModel = officerViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
