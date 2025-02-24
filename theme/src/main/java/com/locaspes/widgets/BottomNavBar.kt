//// presentation/components/BottomNavBar.kt
//package com.locaspes.widgets
//
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import com.locaspes.theme.R
//
//sealed class NavItem(
//    val route: String,
//    val icon: Int,
//    val label: Int
//) {
//    object Home : NavItem("home", R.drawable.ic_home, R.string.home)
//    object Messages : NavItem("messages", R.drawable.ic_chat, R.string.messages)
//    object Profile : NavItem("profile", R.drawable.ic_profile, R.string.profile)
//}
//
//@Composable
//fun BottomNavBar(
//    currentRoute: String,
//    onNavigate: (NavItem) -> Unit
//) {
//    NavigationBar(
//        containerColor = MaterialTheme.colorScheme.surface,
//        contentColor = MaterialTheme.colorScheme.onSurface
//    ) {
//        listOf(NavItem.Home, NavItem.Messages, NavItem.Profile).forEach { item ->
//            NavigationBarItem(
//                selected = currentRoute == item.route,
//                onClick = { onNavigate(item) },
//                icon = {
//                    Icon(
//                        painter = painterResource(id = item.icon),
//                        contentDescription = stringResource(id = item.label)
//                    )
//                },
//                label = { Text(stringResource(id = item.label)) }
//            )
//        }
//    }
//}