package com.bandme.bandmeappmobile.ui.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bandme.bandmeappmobile.ui.screen.*
import com.bandme.bandmeappmobile.ui.screen.dashboard.DashboardScreen
import com.bandme.bandmeappmobile.ui.screen.login.LoginEmailScreen
import com.bandme.bandmeappmobile.ui.screen.login.SelectUserTypeScreen
import com.bandme.bandmeappmobile.ui.screen.login.ValidateCodeRegisterScreen
import com.bandme.bandmeappmobile.ui.screen.login.WelcomeScreen
import com.bandme.bandmeappmobile.ui.viewModel.LoginViewModel

sealed class NavRoutes(val route: String) {
    object Welcome : NavRoutes("welcome")
    object LoginEmail : NavRoutes("loginEmail")
    object LoginPassword : NavRoutes("loginPassword")
    object SelectUserType : NavRoutes("selectUserType")
    object ValidateResetCode : NavRoutes("validateResetCode")
    object ValidateResetEmail : NavRoutes("validateResetEmail")
    object ValidateCodeRegister : NavRoutes("ValidateCodeRegister")
    object Dashboard: NavRoutes("Dashboard")
}

fun NavGraphBuilder.BandmeNavigator(
    route: String,
    navController: NavController,
    viewModel: LoginViewModel,
    googleSignIn: () -> Unit,
    spotifySignIn: () -> Unit
) {
    navigation(
        route = route,
        startDestination = NavRoutes.Welcome.route
    ){
        composable(NavRoutes.Welcome.route){
            WelcomeScreen(
                viewModel = viewModel ,
                goToEmailSignIn = { navController.navigate(NavRoutes.LoginEmail.route) },
                googleSignIn = googleSignIn,
                spotifySignIn = spotifySignIn,
                onSocialMediaRegister = { navController.navigate(NavRoutes.SelectUserType.route) },
                onSocialMediaLogin = { navController.navigate(NavRoutes.Dashboard.route) }
            )
        }

        composable(NavRoutes.LoginEmail.route){
            LoginEmailScreen(
                viewModel = viewModel,
                onBackPress = { navController.navigateUp() },
                onNavigateToSuccess = { navController.navigate(NavRoutes.LoginPassword.route) },
                onNavigateToFinishRegiter = { navController.navigate(NavRoutes.ValidateCodeRegister.route) }
            )
        }

        composable(NavRoutes.LoginPassword.route){
            LoginPasswordScreen(
                viewModel = viewModel,
                onNavigateToDashboard = {navController.navigate(NavRoutes.Dashboard.route)},
                onNavigateToSelectUserType = { navController.navigate(NavRoutes.SelectUserType.route) },
                onBackPress = { navController.navigateUp() }
            )
        }

        composable(NavRoutes.ValidateResetEmail.route){
            ValidateResetEmailScreen(
                viewModel = viewModel,
                onNavigateToSuccess = { navController.navigate(NavRoutes.ValidateResetCode.route) },
                onBackPress = { navController.navigateUp() }
            )
        }

        composable(NavRoutes.ValidateResetCode.route){
            ValidateResetCodeScreen(
                viewModel = viewModel,
                onNavigateToSuccess = { navController.navigate(NavRoutes.Dashboard.route) },
                onBackPress = { navController.navigateUp() }
            )
        }

        composable(NavRoutes.ValidateCodeRegister.route){
            ValidateCodeRegisterScreen(
                viewModel = viewModel,
                onNavigateToSuccess = { navController.navigate(NavRoutes.Dashboard.route) },
                onBackPress = { navController.navigateUp() }
            )
        }

        composable(NavRoutes.SelectUserType.route){
            SelectUserTypeScreen(
                viewModel = viewModel,
                onNavigateToSuccess = { navController.navigate(NavRoutes.ValidateCodeRegister.route) },
                onBackPress = { navController.navigateUp() },
            )
        }

        composable(NavRoutes.Dashboard.route){
            DashboardScreen()
        }
    }
}