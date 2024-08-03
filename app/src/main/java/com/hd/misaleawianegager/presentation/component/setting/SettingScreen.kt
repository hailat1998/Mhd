package com.hd.misaleawianegager.presentation.component.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.Divider
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SettingScreen(
    showModalBottomSheet: MutableState<Boolean>,
    onEvent: (SettingEvent) -> Unit,
    theme: State<String?>,
    font: State<String?> ,
   ) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState ,
        onDismissRequest = {
            showModalBottomSheet.value = !showModalBottomSheet.value
        },
        shape = RectangleShape,

        dragHandle = {
            Row {
                Text(
                    text = "Setting", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp)
                )
                Spacer(modifier = Modifier.weight(0.8f))
                Icon(Icons.Default.Close, null, modifier = Modifier
                    .clickable { showModalBottomSheet.value = false }
                    .padding(top = 20.dp, end = 20.dp))
            }
        },
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.2f))
                .heightIn(max = 350.dp),
            contentAlignment = Alignment.Center

        ) {

            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
            ) {
                item { ThemeContent(theme = theme, onEvent) }
                item{ Divider(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer))}
                item{ FontContent(font = font, onEvent) }
                item{ Divider(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer))}
                item{ FontSizeContent(onEvent) }
                item{ Divider(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer))}
                item{ LetterSpaceContent(onEvent) }
                  }
            }
        }
    }







@Composable
fun ThemeContent(  theme: State<String?> ,onEvent: (SettingEvent) -> Unit) {
    Box(Modifier.padding(start = 20.dp)) {

        Column {
            Text(text = "Theme", style = MaterialTheme.typography.headlineMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                    .clickable { onEvent.invoke(SettingEvent.Theme("system")) },
            ) {
                RadioButton(
                    selected = theme.value == "system",
                    onClick = { onEvent.invoke(SettingEvent.Theme("system")) },

                )
                Text(text = "System", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 14.dp))

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                    .clickable { onEvent.invoke(SettingEvent.Theme("dark")) },
            ) {
                RadioButton(
                    selected = theme.value == "dark",
                    onClick = { onEvent.invoke(SettingEvent.Theme("dark")) },

                )
                Text(text = "Dark", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 14.dp) )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                    .clickable { onEvent.invoke(SettingEvent.Theme("light")) },

            ) {
                RadioButton(
                    selected = theme.value == "light",
                    onClick = { onEvent.invoke(SettingEvent.Theme("light")) },

                )
                Text(text = "Light", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 14.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontContent( font: State<String?> , onEvent: (SettingEvent) -> Unit){
    val fontList = listOf(
        "abyssinica_gentium", "andikaafr_r", "charterbr_roman", "desta_gentium", "gfzemen_regular",
        "jiret", "nyala", "washrasb", "wookianos", "yebse", "serif", "Default"
    )

    Box(Modifier.padding(20.dp)) {
        var expanded by remember { mutableStateOf(false) }
        Column {
            Text(text = "FontFamily", style = MaterialTheme.typography.headlineMedium)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .height(60.dp)
                    .shadow(0.dp, RoundedCornerShape(10.dp))
            ) {
                TextField(
                    value = font.value!!,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.menuAnchor(),
                    textStyle = MaterialTheme.typography.titleMedium,
                    shape = RoundedCornerShape(20.dp),
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

    Box(Modifier.padding(20.dp)) {
        Column {
            Text(text = "FontSize", style = MaterialTheme.typography.headlineMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Chip(
                    onClick = { onEvent.invoke(SettingEvent.FontSize(2)) },
                    colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.primaryContainer),
                    shape =  MaterialTheme.shapes. small. copy(CornerSize(percent = 20)),
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    Text(text = "A+", style = MaterialTheme.typography.titleLarge)
                }
                Chip(
                    onClick = { onEvent.invoke(SettingEvent.FontSize(-2)) },
                    colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.primaryContainer),
                    shape =  MaterialTheme.shapes. small. copy(CornerSize(percent = 20),),
                    modifier = Modifier.padding(start = 20.dp)
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
    Box(Modifier.padding(20.dp)) {
        Column {
            Text(
                text = "LetterSpace",
                style = MaterialTheme.typography.headlineMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Chip(onClick = { onEvent.invoke(SettingEvent.LetterSpace(1.0)) },
                    colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.padding(end = 20.dp)) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.space_bar_24px),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surfaceContainerLow,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = "+", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(top =13.dp ))
                    }
                }
                Chip(onClick = { onEvent.invoke(SettingEvent.LetterSpace(1.0)) },
                    colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.padding(start = 20.dp)) {
                    Row(modifier = Modifier.clickable {
                        onEvent.invoke(SettingEvent.LetterSpace(-1.0))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.space_bar_24px),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surfaceContainerLow,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = "-", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(top =13.dp ))
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