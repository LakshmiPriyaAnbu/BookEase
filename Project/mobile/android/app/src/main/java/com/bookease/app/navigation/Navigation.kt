package com.bookease.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import com.bookease.app.data.models.*
import com.bookease.app.ui.components.ToastHost
import com.bookease.app.ui.components.ToastType
import com.bookease.app.ui.components.rememberToastState
import com.bookease.app.ui.screens.booking.BookingScreen
import com.bookease.app.ui.screens.booking.BookingViewModel
import com.bookease.app.ui.screens.bookings.BookingsScreen
import com.bookease.app.ui.screens.bookings.BookingsViewModel
import com.bookease.app.ui.screens.confirmation.ConfirmationScreen
import com.bookease.app.ui.screens.contact.ContactScreen
import com.bookease.app.ui.screens.detail.ServiceDetailScreen
import com.bookease.app.ui.screens.detail.ServiceDetailViewModel
import com.bookease.app.ui.screens.explore.ExploreScreen
import com.bookease.app.ui.screens.explore.ExploreViewModel
import com.bookease.app.ui.screens.home.HomeScreen
import com.bookease.app.ui.screens.howitworks.HowItWorksScreen
import com.bookease.app.ui.screens.login.LoginScreen
import com.bookease.app.ui.screens.pricing.PricingScreen
import com.bookease.app.ui.screens.profile.ProfileScreen
import com.bookease.app.ui.screens.services.ServicesMarketingScreen
import com.bookease.app.ui.theme.BeColor
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

// ── Routes ─────────────────────────────────────────────────────────────────────

sealed class Tab(val route: String, val label: String, val icon: ImageVector) {
    object Home     : Tab("home",     "Home",     Icons.Default.Home)
    object Explore  : Tab("explore",  "Explore",  Icons.Default.Explore)
    object Bookings : Tab("bookings", "Bookings", Icons.Default.CalendarMonth)
    object Profile  : Tab("profile",  "Profile",  Icons.Default.Person)
}

private val tabs = listOf(Tab.Home, Tab.Explore, Tab.Bookings, Tab.Profile)

// Shared ViewModel-like holders passed as state between composables
class AppState {
    var selectedService: Service? = null
    var confirmedBooking: Booking? = null
}

// ── Root Tab Host ──────────────────────────────────────────────────────────────

@Composable
fun BookEaseApp() {
    val appState      = remember { AppState() }
    val rootNavController = rememberNavController()
    val toastState    = rememberToastState()

    val showToast: (String, ToastType) -> Unit = { message, type ->
        toastState.showToast(message, type)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Full-screen routes that sit outside the tab bar
        NavHost(
            navController    = rootNavController,
            startDestination = "tabs"
        ) {
            composable("tabs") {
                TabScaffold(
                    appState       = appState,
                    showToast      = showToast,
                    onServiceClick = { service ->
                        appState.selectedService = service
                        rootNavController.navigate("detail")
                    },
                    onNavigateTo   = { route ->
                        rootNavController.navigate(route)
                    }
                )
            }

            composable("detail") {
                val service = appState.selectedService ?: return@composable
                val vm = remember { ServiceDetailViewModel(service) }
                ServiceDetailScreen(
                    vm       = vm,
                    onBack   = { rootNavController.popBackStack() },
                    onBookNow = {
                        rootNavController.navigate("booking")
                    }
                )
            }

            composable("booking") {
                val service = appState.selectedService ?: return@composable
                val vm = remember { BookingViewModel(service) }
                BookingScreen(
                    vm          = vm,
                    onBack      = { rootNavController.popBackStack() },
                    onConfirmed = { booking ->
                        appState.confirmedBooking = booking
                        rootNavController.navigate("confirmation") {
                            popUpTo("tabs") { inclusive = false }
                        }
                    }
                )
            }

            composable("confirmation") {
                val booking = appState.confirmedBooking ?: return@composable
                ConfirmationScreen(
                    booking      = booking,
                    onBackToHome = {
                        rootNavController.navigate("tabs") {
                            popUpTo("tabs") { inclusive = true }
                        }
                    }
                )
            }

            // ── Marketing / utility screens ──────────────────────────────────

            composable("services_marketing") {
                ServicesMarketingScreen(
                    onBookService = { service ->
                        appState.selectedService = service
                        rootNavController.navigate("detail")
                    }
                )
            }

            composable("how_it_works") {
                HowItWorksScreen(
                    onGetStarted = {
                        rootNavController.navigate("services_marketing")
                    }
                )
            }

            composable("pricing") {
                PricingScreen(vm = viewModel())
            }

            composable("contact") {
                ContactScreen(
                    showToast = showToast,
                    vm        = viewModel()
                )
            }

            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        rootNavController.navigate("tabs") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    showToast = showToast,
                    vm        = viewModel()
                )
            }
        }

        // Toast overlay
        ToastHost(
            toastState = toastState,
            modifier   = Modifier
                .align(Alignment.BottomEnd)
                .padding(28.dp)
        )
    }
}

// ── Tab Scaffold ───────────────────────────────────────────────────────────────

@Composable
private fun TabScaffold(
    appState: AppState,
    showToast: (String, ToastType) -> Unit,
    onServiceClick: (Service) -> Unit,
    onNavigateTo: (String) -> Unit
) {
    val tabNavController = rememberNavController()
    val backStackEntry   by tabNavController.currentBackStackEntryAsState()
    val currentRoute     = backStackEntry?.destination?.route

    Scaffold(
        containerColor = BeColor.surface100,
        bottomBar = {
            NavigationBar(
                containerColor = BeColor.surfaceWhite,
                tonalElevation = androidx.compose.ui.unit.Dp(0f)
            ) {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute == tab.route,
                        onClick  = {
                            tabNavController.navigate(tab.route) {
                                popUpTo(tabNavController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState    = true
                            }
                        },
                        icon  = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor      = BeColor.primary,
                            selectedTextColor      = BeColor.primary,
                            indicatorColor         = BeColor.primarySoft,
                            unselectedIconColor    = BeColor.ink300,
                            unselectedTextColor    = BeColor.ink300
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController    = tabNavController,
            startDestination = Tab.Home.route,
            modifier         = Modifier.padding(innerPadding)
        ) {
            composable(Tab.Home.route) {
                HomeScreen(
                    onServiceClick   = onServiceClick,
                    onSeeAllServices = {
                        tabNavController.navigate(Tab.Explore.route)
                    },
                    onCategoryClick = { _ ->
                        tabNavController.navigate(Tab.Explore.route)
                    },
                    onNavigateTo = onNavigateTo
                )
            }

            composable(Tab.Explore.route) {
                ExploreScreen(
                    onServiceClick = onServiceClick,
                    vm             = viewModel()
                )
            }

            composable(Tab.Bookings.route) {
                BookingsScreen(vm = viewModel())
            }

            composable(Tab.Profile.route) {
                ProfileScreen(
                    onMyBookingsClick = {
                        tabNavController.navigate(Tab.Bookings.route)
                    }
                )
            }
        }
    }
}
