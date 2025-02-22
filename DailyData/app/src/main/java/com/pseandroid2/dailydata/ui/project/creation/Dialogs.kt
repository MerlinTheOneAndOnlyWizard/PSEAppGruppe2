package com.pseandroid2.dailydata.ui.project.creation

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pseandroid2.dailydata.ui.composables.EnumDropDownMenu
import com.pseandroid2.dailydata.util.ui.DataType
import com.pseandroid2.dailydata.util.ui.Graphs
import com.pseandroid2.dailydata.util.ui.Wallpapers
import java.lang.NumberFormatException
import java.util.Calendar


@Preview(showBackground = true)
@Composable
fun Prev() {
    Card(modifier = Modifier
        .clickable { }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .size(40.dp)
                .wrapContentSize(Alignment.CenterStart)) {
                Box(
                    modifier = Modifier
                        .size((20.dp))
                        .clip(CircleShape)
                        .background(color = Color.Green)
                )
            }
            Text(text = "Green")
        }
    }
}

@Composable
fun AppDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    Content : @Composable () -> Unit
) {
    if(isOpen) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = 8.dp
            ) {
                Surface(modifier = Modifier.padding(16.dp)) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun WallpaperDialog(
    isOpen : Boolean,
    onDismissRequest: () -> Unit,
    onWallpaperClick : (Wallpapers) -> Unit
) {
    AppDialog(
        isOpen = isOpen,
        onDismissRequest = onDismissRequest
    ) {
        Column(modifier = Modifier.width(200.dp)) {
            for (wallpaper in Wallpapers.values()) {
                Card(modifier = Modifier
                    .clickable { onWallpaperClick(wallpaper) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier
                            .size(40.dp)
                            .wrapContentSize(Alignment.CenterStart)) {
                            Box(
                                modifier = Modifier
                                    .size((25.dp))
                                    .clip(CircleShape)
                                    .background(color = wallpaper.value)
                            )
                        }
                        Text(text = wallpaper.representation)
                    }
                }
            }
        }
    }
}

@Composable
fun TableDialog(
    isOpen : Boolean,
    onDismissRequest: () -> Unit,
    onClick : (String, String, DataType) -> Unit,
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {

        var name by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf(false) }
        var unit by remember { mutableStateOf("") }
        var unitError by remember { mutableStateOf(false) }
        val suggestions = DataType.values().toList().map { it.representation }
        var dataType by remember { mutableStateOf(suggestions.first()) }

        Column(
            modifier = Modifier
                .width(300.dp)
        ) {
            OutlinedTextField(
                label = { Text("Name") },
                value = name,
                isError = nameError,
                onValueChange = {
                    name = it
                    nameError = false
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(
                label = { Text("Unit") },
                value = unit,
                isError = unitError,
                onValueChange = {
                    unit = it
                    unitError = false
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            EnumDropDownMenu(
                suggestions = suggestions,
                value = dataType,
                onClick = { dataType = suggestions[it] }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = {
                    if(unit.isBlank()) {
                        unitError = true
                    }
                    if(name.isBlank()) {
                        nameError = true
                    }
                    if(!unitError && !nameError) {
                        onClick(name, unit, DataType.fromString(dataType))
                    }
                }) {
                    Text(text = "OK")
                }
            }
        }
    }
}


@Composable
fun ButtonDialog(
    isOpen: Boolean,
    buttons : List<String>,
    onDismissRequest: () -> Unit,
    onClick: (String, Int, String) -> Unit
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        var name by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf(false) }
        var value by remember { mutableStateOf("") }
        var valueError by remember { mutableStateOf(false) }

        var column by remember { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .width(300.dp)
        ) {
            OutlinedTextField(
                label = { Text("Name") },
                value = name,
                isError = nameError,
                onValueChange = {
                    name = it
                    nameError = false
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(
                label = { Text("Unit") },
                value = value,
                isError = valueError,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = {
                    value = it
                    valueError = false
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            EnumDropDownMenu(
                suggestions = buttons,
                value = buttons[column],
                onClick = {
                    column = it
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = {
                    try {
                        value.toInt()
                    } catch (e : NumberFormatException) {
                        valueError = true
                    }
                    if(name.isBlank()) {
                        nameError = true
                    }
                    if(!valueError && !nameError) {
                        onClick(name, column, value)
                    }
                }) {
                    Text(text = "OK")
                }
            }
        }
    }
}

@Composable
fun NotificationDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (String, String) -> Unit
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        var message by remember { mutableStateOf("") }
        var messageError by remember { mutableStateOf(false) }

        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        var time by remember { mutableStateOf("$hour:$minute") }
        val timePickerDialog = TimePickerDialog( context,
            {_, hour : Int, minute: Int ->
                time = "$hour:$minute"
            }, hour, minute, true
        )
        Column(
            modifier = Modifier.width(300.dp)
        ) {
            OutlinedTextField(
                label = { Text("Name") },
                value = message,
                isError = messageError,
                onValueChange = {
                    message = it
                    messageError = false
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedButton(
                onClick = { timePickerDialog.show() },
                modifier = Modifier.fillMaxWidth(),

                ) {
                Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Left, text = "Selected Time: $time")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = {
                    if(message.isBlank()) {
                        messageError = true
                    } else {
                        onClick(message, time)
                    }
                }) {
                    Text(text = "OK")
                }
            }
        }
    }
}

@Composable
fun GraphDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (Graphs) -> Unit
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.width(200.dp)) {
            Graphs.values().toList().forEachIndexed { index, graphs ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .clickable { onClick(graphs) },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = graphs.representation)
                }
                if (index <  Graphs.values().lastIndex)
                    Divider()
            }
        }
    }
}

@Composable
fun BackDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onOkClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.width(300.dp)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "All edited data will be lost")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = onOkClick
                ) {
                    Text(text = "OK")
                }
                TextButton(
                    onClick = onCancelClick
                ) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}