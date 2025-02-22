/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package com.pseandroid2.dailydata.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopNavigationBar(
    items: List<String>,
    indexCurrentTab : Int,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {

    TabRow(selectedTabIndex = indexCurrentTab, modifier = modifier) {
        items.forEachIndexed { index, item ->
            val selected = (indexCurrentTab == index)
            Tab(
                selected = selected,
                onClick = { onItemClick(index) },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

data class TopNavItem(
    val name: String,
    val route: String
)