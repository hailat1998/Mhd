package com.hd.misaleawianegager.presentation.component.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.utils.compose.Chip
import com.hd.misaleawianegager.utils.compose.ChipDefaultsM3
import com.hd.misaleawianegager.utils.compose.Divider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    showModalBottomSheet: MutableState<Boolean>,
    onEvent: (SettingEvent) -> Unit,
    theme: State<String?>,
    font: State<String?> ,
    showOthers: State<Boolean>
   ) {

    val localFont = FontFamily.Default
    val textStyle = TextStyle(fontFamily = localFont)

    CompositionLocalProvider(LocalTextStyle provides textStyle) {
        ModalBottomSheet(
            onDismissRequest = { showModalBottomSheet.value = false },
            containerColor = MaterialTheme.colorScheme.background,
            dragHandle = null,
            shape = RectangleShape
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .heightIn(max = 500.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
                    Row {
                        Text(
                            text = "Setting",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(10.dp).background(MaterialTheme.colorScheme.surface)
                        )
                        Spacer(modifier = Modifier.weight(0.8f))
                        Icon(Icons.Default.Close, null, modifier = Modifier
                            .clickable { showModalBottomSheet.value = false }
                            .padding(10.dp))
                    }
                    LazyColumn(
                        modifier = Modifier
                            .background(Color.Transparent)
                    ) {
                        item { ThemeContent(theme = theme, onEvent) }
                        item { Divider(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer)) }
                        item { FontContent(font = font, onEvent, showOthers) }
                        if (showOthers.value) {
                            item { Divider(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer)) }
                            item { FontSizeContent(onEvent) }
                            item { Divider(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer)) }
                            item { LetterSpaceContent(onEvent) }
                        }
                    }
                }
            }
        }
    }
}


    @Composable
    fun ThemeContent(theme: State<String?>, onEvent: (SettingEvent) -> Unit) {
        Box(Modifier.padding(start = 8.dp)) {
            Column {
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Row {
                    Row(
                        modifier = Modifier
                            .clickable { onEvent.invoke(SettingEvent.Theme("system")) },
                    ) {
                        RadioButton(
                            selected = theme.value == "system",
                            onClick = { onEvent.invoke(SettingEvent.Theme("system")) },

                            )
                        Text(
                            text = "System", style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 14.dp)
                        )

                    }
                    Row(
                        modifier = Modifier
                            .clickable { onEvent.invoke(SettingEvent.Theme("dark")) }
                    ) {
                        RadioButton(
                            selected = theme.value == "dark",
                            onClick = { onEvent.invoke(SettingEvent.Theme("dark")) },

                            )
                        Text(
                            text = "Dark", style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 14.dp)
                        )

                    }

                    Row(
                        modifier = Modifier
                            .clickable { onEvent.invoke(SettingEvent.Theme("light")) }

                        ) {
                        RadioButton(
                            selected = theme.value == "light",
                            onClick = { onEvent.invoke(SettingEvent.Theme("light")) },

                            )
                        Text(
                            text = "Light", style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 14.dp)
                        )
                    }
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontContent(font: State<String?>, onEvent: (SettingEvent) -> Unit, showOthers: State<Boolean>){
    val fontList = listOf(
        "abyssinica_gentium", "andikaafr_r", "charterbr_roman", "desta_gentium", "gfzemen_regular",
        "jiret", "nyala", "washrasb", "wookianos", "yebse", "serif", "Default"
    )
    Box(Modifier.padding(8.dp).background(MaterialTheme.colorScheme.surface)) {
        var expanded by remember { mutableStateOf(false) }
        Column(Modifier.padding(bottom= if(showOthers.value)0.dp else 48.dp)) {
            Text(text = "FontFamily",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 12.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .height(60.dp)
                    .shadow(0.dp, RoundedCornerShape(10.dp))
            ) {
                BasicTextField(
                    value = font.value!!,
                    onValueChange = {},
                    readOnly = true,
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        baselineShift = BaselineShift.None
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                innerTextField()
                            }
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        }
                    }
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

@Composable
fun FontSizeContent(onEvent: (SettingEvent) -> Unit){
    Box(Modifier.padding(8.dp).background(MaterialTheme.colorScheme.surface)) {
        Column {
            Text(text = "FontSize", style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold))
            Row(
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface),
            ) {
                Chip(
                    onClick = { onEvent.invoke(SettingEvent.FontSize(2)) },
                    colors = ChipDefaultsM3.chipColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape =  MaterialTheme.shapes. small. copy(CornerSize(percent = 20)),
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    Box(modifier = Modifier.size(30.dp), contentAlignment = Alignment.Center) {
                        Text(text = "A+", style = MaterialTheme.typography.titleLarge)
                    }
                }
                Chip(
                    onClick = { onEvent.invoke(SettingEvent.FontSize(-2)) },
                    colors = ChipDefaultsM3.chipColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape =  MaterialTheme.shapes. small. copy(CornerSize(percent = 20),),
                    modifier = Modifier.padding(start = 20.dp)
                ) {
                    Box(modifier = Modifier.size(30.dp), contentAlignment = Alignment.Center) {
                        Text(text = "A-", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }
    }
}

@Composable
fun LetterSpaceContent(onEvent: (SettingEvent) -> Unit){
    Box(Modifier.padding(8.dp).background(MaterialTheme.colorScheme.surface)) {
        Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
            Text(
                text = "Word Space",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 45.dp),
            ) {
                Chip(
                    onClick = { onEvent.invoke(SettingEvent.LetterSpace(1.0)) },
                    colors = ChipDefaultsM3.chipColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.padding(end = 20.dp, top = 15.dp)
                ) {
                    Box(
                        modifier = Modifier.size(45.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.space_bar_24px),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.surfaceContainerLow,
                                modifier = Modifier.size(30.dp).padding(top = 5.dp)
                            )
                            Text(
                                text = "+",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }
                Chip(
                    onClick = { onEvent.invoke(SettingEvent.LetterSpace(-1.0)) },
                    colors = ChipDefaultsM3.chipColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.padding(start = 20.dp, top = 15.dp)
                ) {
                    Box(
                        modifier = Modifier.size(45.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.space_bar_24px),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.surfaceContainerLow,
                                modifier = Modifier.size(30.dp).padding(top = 5.dp)
                            )
                            Text(
                                text = "-",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

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
                    colors = ChipDefaultsM3.chipColors(containerColor = MaterialTheme.colorScheme.background)
                ) {

                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.height_24px),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = "+", style = MaterialTheme.typography.headlineMedium)
                    }
                }

                Chip(
                    onClick = { onEvent.invoke(SettingEvent.LetterSpace(1.0)) },
                    colors = ChipDefaultsM3.chipColors(containerColor = MaterialTheme.colorScheme.background)
                ) {

                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.height_24px),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = "-", style = MaterialTheme.typography.headlineMedium)
                    }
                }
            }
        }
    }
}

