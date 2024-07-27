package com.hd.misaleawianegager.presentation.component.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SettingScreen(
    showModalBottomSheet: MutableState<Boolean>,
    onEvent: (SettingEvent) -> Unit,
    theme: State<String?>,
    font: State<String?> ) {
    ModalBottomSheet(
        onDismissRequest = {
            showModalBottomSheet.value = !showModalBottomSheet.value
        },
        dragHandle = {
            Row {
                Text(
                    text = "Setting", style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.weight(0.8f))
                Icon(Icons.Default.Close, null, modifier = Modifier
                    .clickable { showModalBottomSheet.value = false }
                    .padding(10.dp))
            }
        },
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.2f)),
            contentAlignment = Alignment.TopCenter

        ) {
            Box(modifier = Modifier.heightIn(max = 350.dp)) {
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
            ) {
                item {

                            ThemeContent(theme = theme, onEvent)

                            FontContent(font = font, onEvent)

                            FontSizeContent(onEvent)

                            LetterSpaceContent(onEvent)

                            LineHeightContent(onEvent)

                        }
                  }
            }
        }
    }
}



@Composable
fun ThemeContent(  theme: State<String?> ,onEvent: (SettingEvent) -> Unit){
    Text(text = "Theme", style = MaterialTheme.typography.headlineMedium)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
    ) {
        RadioButton(
            selected = theme.value == "system",
            onClick = { onEvent.invoke(SettingEvent.Theme("system")) })
        Text(text = "System", style = MaterialTheme.typography.titleMedium)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        RadioButton(
            selected = theme.value == "dark",
            onClick = { onEvent.invoke(SettingEvent.Theme("dark")) })
        Text(text = "Dark", style = MaterialTheme.typography.titleMedium)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        RadioButton(
            selected = theme.value == "light",
            onClick = { onEvent.invoke(SettingEvent.Theme("light")) })
        Text(text = "Light", style = MaterialTheme.typography.titleMedium)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontContent( font: State<String?> , onEvent: (SettingEvent) -> Unit){
    val fontList = listOf(
        "abyssinica_gentium", "andikaafr_r", "charterbr_roman", "desta_gentium", "gfzemen_regular",
        "jiret", "nyala", "washrasb", "wookianos", "yebse", "serif", "Default"
    )

    Box {
        var expanded by remember { mutableStateOf(false) }
        Column {
            Text(text = "FontFamily", style = MaterialTheme.typography.headlineMedium)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = font.value!!,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.menuAnchor(),
                    textStyle = MaterialTheme.typography.titleMedium
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    fontList.forEach { fontName ->
                        DropdownMenuItem(
                            text = { Text(text = fontName) },
                            onClick = {
                                onEvent(SettingEvent.FontFamily(fontName))
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FontSizeContent(onEvent: (SettingEvent) -> Unit){
    Box {
        Column {
            Text(text = "FontSize", style = MaterialTheme.typography.headlineMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Chip(
                    onClick = { onEvent.invoke(SettingEvent.FontSize(2)) },
                    colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.background),
                    shape =  MaterialTheme.shapes. small. copy(CornerSize(percent = 20))
                ) {
                    Text(text = "A+", style = MaterialTheme.typography.titleLarge)
                }
                Chip(
                    onClick = { onEvent.invoke(SettingEvent.FontSize(-2)) },
                    colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.background),
                    shape =  MaterialTheme.shapes. small. copy(CornerSize(percent = 20))
                ) {
                    Text(text = "A-", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LetterSpaceContent(onEvent: (SettingEvent) -> Unit){
    Box {
        Column {
            Text(
                text = "LetterSpace",
                style = MaterialTheme.typography.headlineMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Chip(onClick = { onEvent.invoke(SettingEvent.LetterSpace(1.0)) },
                    colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.background)) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.space_bar_24px),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = "+", style = MaterialTheme.typography.headlineSmall)
                    }
                }
                Chip(onClick = { onEvent.invoke(SettingEvent.LetterSpace(1.0)) },
                    colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.background)) {
                    Row(modifier = Modifier.clickable {
                        onEvent.invoke(SettingEvent.LetterSpace(-1.0))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.space_bar_24px),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = "-", style = MaterialTheme.typography.headlineSmall)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LineHeightContent(onEvent: (SettingEvent) -> Unit) {
    Box {
        Column {
            Text(
                text = "LineHeight",
                style = MaterialTheme.typography.headlineMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Chip(
                    onClick = { onEvent.invoke(SettingEvent.LetterSpace(1.0)) },
                    colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.background)
                ) {

                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.height_24px),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = "+", style = MaterialTheme.typography.headlineSmall)
                    }
                }

                Chip(
                    onClick = { onEvent.invoke(SettingEvent.LetterSpace(1.0)) },
                    colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.background)
                ) {

                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.height_24px),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = "-", style = MaterialTheme.typography.headlineSmall)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Sf(){

}