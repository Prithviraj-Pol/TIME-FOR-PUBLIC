package com.timeforpublic.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Policy
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(Routes.CITIZEN_HOME, "Home", Icons.Default.Home)
    object Schemes : BottomNavItem(Routes.SCHEMES, "Schemes", Icons.Default.Policy)
    object AiAssistant : BottomNavItem(Routes.AI_ASSISTANT, "AI Helpdesk", Icons.Default.AutoAwesome)
    object Offices : BottomNavItem(Routes.OFFICES, "Offices", Icons.Default.AccountBalance)
    object Documents : BottomNavItem(Routes.DOCUMENTS, "Documents", Icons.Default.Description)

    companion object {
        val items = listOf(Home, Schemes, AiAssistant, Offices, Documents)
    }
}
