package com.hd.misaleawianegager.presentation.component.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hd.misaleawianegager.presentation.theme.MisaleawiAnegagerTheme
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.presentation.theme.Font

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SettingScreen(
    showModalBottomSheet: MutableState<Boolean>,
    onEvent: (SettingEvent) -> Unit,
    theme: State<String?>,
    font: State<Int?>,
                 ) {

    ModalBottomSheet(onDismissRequest = { showModalBottomSheet.value = !showModalBottomSheet.value }) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(10.dp).background(MaterialTheme.colorScheme.background.copy(alpha = 0.2f)), contentAlignment = Alignment.TopCenter

        ) {
            LazyColumn(modifier = Modifier
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))) {
                item {
                    Text(text = "Theme", style = MaterialTheme.typography.headlineMedium )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
                    ) {
                        RadioButton(selected = theme.value == "system", onClick = { onEvent.invoke(SettingEvent.Theme("system")) })
                        Text(text = "System", style = MaterialTheme.typography.headlineSmall)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        RadioButton(selected = theme.value == "dark", onClick = { onEvent.invoke(SettingEvent.Theme("dark")) })
                        Text(text = "Dark", style = MaterialTheme.typography.headlineSmall)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        RadioButton(selected = theme.value == "light", onClick = { onEvent.invoke(SettingEvent.Theme("light")) })
                        Text(text = "Light" , style = MaterialTheme.typography.headlineSmall)
                    }


                    Text(text = "Font", style = MaterialTheme.typography.headlineMedium)
                    var expanded by remember { mutableStateOf(false) }
                    var selected by remember { mutableStateOf(font.value) }
                    Box {
                        Column {
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded }) {
                                selected?.let {
                                    var font = ""
                                    Font.entries.forEach{ it ->
                                        if(it.ordinal == selected){
                                          font = it.name
                                        }
                                    }
                                    TextField(
                                        value = font,
                                        onValueChange = {},
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded
                                            )
                                        },
                                        modifier = Modifier.menuAnchor()
                                    )
                                }
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }) {
                                    Font.entries.forEach { it ->
                                        DropdownMenuItem(
                                            text = { Text(text = it.name) },
                                            onClick = { selected = it.ordinal
                                                        onEvent(SettingEvent.FontFamily(it.ordinal))
                                                        expanded = false
                                            })
                                    }
                                }
                            }
                        }
                    }


                    Text(text = "FontSize",style = MaterialTheme.typography.headlineMedium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Chip(onClick = {onEvent.invoke(SettingEvent.FontSize(+3))}, colors = ChipDefaults.chipColors(backgroundColor = Color.Black)) {
                            Text(text = "A+", style = MaterialTheme.typography.displayLarge)
                        }
                        Chip(onClick = {onEvent.invoke(SettingEvent.FontSize(-3))}, colors = ChipDefaults.chipColors(backgroundColor = Color.Black)) {
                            Text(text = "A-", style = MaterialTheme.typography.displayLarge)
                        }
                    }



                    Text(text = "LetterSpace", style = MaterialTheme.typography.headlineMedium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(modifier = Modifier.clickable{
                            onEvent.invoke(SettingEvent.LetterSpace(+1.0))
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.space_bar_24px),
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Text(text = "+" ,style = MaterialTheme.typography.headlineSmall)
                        }
                        Row(modifier = Modifier.clickable{
                            onEvent.invoke(SettingEvent.LetterSpace(-1.0))
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.space_bar_24px),
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Text(text = "-", style = MaterialTheme.typography.headlineSmall)
                        }
                    }



                    Text(text = "LineHeight",style = MaterialTheme.typography.headlineMedium)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(modifier = Modifier.clickable{
                            onEvent.invoke(SettingEvent.LineHeight(+2))
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.height_24px),
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Text(text = "+", style = MaterialTheme.typography.headlineSmall)
                        }

                        Row(modifier = Modifier.clickable{
                            onEvent.invoke(SettingEvent.LineHeight(-2))
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.height_24px),
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Text(text = "-", style = MaterialTheme.typography.headlineSmall)
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun Sf(){
    //MisaleawiAnegagerTheme {
    //}
}