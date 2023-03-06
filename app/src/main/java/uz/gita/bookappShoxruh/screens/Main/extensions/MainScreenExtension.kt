package uz.gita.bookappShoxruh.screens.Main.extensions

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab


@Composable
fun RowScope.TabNavigationItem(tab: List<Tab>, selectedTab: Tab, selectedTabChange: (Tab) -> Unit) {
    var tabNavigator = LocalTabNavigator.current
    tabNavigator.current = selectedTab
    tab.forEach {
        BottomNavigationItem(selectedContentColor = MaterialTheme.colorScheme.primary,
            unselectedContentColor = Color.Gray,
            selected = tabNavigator.current == tab,
            onClick = {
                tabNavigator.current = it
                selectedTabChange(it)
            },
            icon = {
                val tabColor =
                    if (selectedTab.options.index == it.options.index || selectedTab.options.index.toInt() == 0 && it.options.index.toInt() == 0) Blue else Color.Gray

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = it.icon!!,
                        contentDescription = it.title,
                        modifier = Modifier.size(30.dp),
                        tint = tabColor
                    )
                    Text(text = it.title, color = tabColor, fontSize = 10.sp)
                }

            })
    }

}

@Composable
@Preview(showBackground = true)
fun prev() {

}