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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.pseandroid2.dailydata.R


@Composable
fun InputElement(
    height : Dp = 60.dp,
    icon : ImageVector = ImageVector.vectorResource(R.drawable.blank_icon),
    Content : @Composable () -> Unit
){
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ){
        Column(
            modifier = Modifier.width(height),
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .size(height)
                    .wrapContentSize(Alignment.Center)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground,
                )
            }
        }
        Content()
    }
}

@Composable
fun WallpaperElement(
    color : Color,
    label: String,
    onClick: () -> Unit,
    height: Dp = 60.dp
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ){
        Column(modifier = Modifier
            .size(height)
            .wrapContentSize(Alignment.Center)) {
            Box(
                modifier = Modifier
                    .size((height / 2))
                    .clip(CircleShape)
                    .background(color = color)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clickable(onClick = onClick),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = label)
        }
    }
}

@Composable
fun ButtonElement(
    icon : ImageVector = ImageVector.vectorResource(R.drawable.blank_icon),
    label: String,
    onClick: () -> Unit,
    height: Dp = 60.dp
) {
    InputElement(
        height = height,
        icon = icon
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clickable(onClick = onClick),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = label)
        }
    }
}

@Composable
fun ListInput(
    label : String,
    mainIcon : ImageVector = ImageVector.vectorResource(R.drawable.blank_icon),
    elementIcon : ImageVector = Icons.Default.Close,
    onClick : () -> Unit,
    onClickItem : (id : Int) -> Unit,
    elements : List<String> = listOf(),
    height: Dp = 60.dp
) {
    InputElement(height = height, icon = mainIcon) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            elements.forEachIndexed { index, it ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = it)
                    Box(
                        modifier = Modifier
                            .size(height)
                            .wrapContentSize(Alignment.Center)
                            .clickable(onClick = { onClickItem(index) })
                    ) {
                        Icon(
                            imageVector = elementIcon,
                            contentDescription = label,
                            tint = MaterialTheme.colors.onBackground,
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .clickable(onClick = onClick),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = label)
            }
        }
    }
}


@Composable
fun TextInput(
    placeholder: String,
    value : String,
    onValueChange : (String) -> Unit,
    height : Dp = 60.dp,
    icon : ImageVector = ImageVector.vectorResource(R.drawable.blank_icon),
    singleLine: Boolean = true
) {
    InputElement(height = height, icon = icon) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                enabled = true,
                readOnly = false,
                textStyle = TextStyle(),
                singleLine = singleLine,
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (value.isEmpty()) {
                            Text(text = placeholder)
                        }
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
fun EnumDropDownMenu(
    suggestions : List<String>,
    value : String,
    onClick : (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero)}
    val icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    Column() {
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Gray)
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = value)
                Icon(
                    imageVector = icon,
                    contentDescription = ""
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current){textFieldSize.width.toDp()})
        ) {
            suggestions.forEachIndexed { index, label ->
                DropdownMenuItem(
                    onClick = {
                        onClick(index)
                        expanded = !expanded
                    }
                ) {
                    Text(text = label)
                }
            }
        }
    }
}