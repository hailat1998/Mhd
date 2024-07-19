package com.hd.misaleawianegager.presentation.component.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SettingScreen(showModalBottomSheet: MutableState<Boolean>) {
    ModalBottomSheet(onDismissRequest = { showModalBottomSheet.value = !showModalBottomSheet.value }) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(10.dp), contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn {
                item {
                    Text(text = "Theme", fontSize = 26.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        RadioButton(selected = true, onClick = { /*TODO*/ })
                        Text(text = "System")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        RadioButton(selected = false, onClick = { /*TODO*/ })
                        Text(text = "Dark")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        RadioButton(selected = false, onClick = { /*TODO*/ })
                        Text(text = "Light")
                    }

                    Text(text = "FontSize", fontSize = 26.sp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Chip(onClick = {}, colors = ChipDefaults.chipColors(backgroundColor = Color.Black)) {
                            Text(text = "A+", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                        }
                        Chip(onClick = {}, colors = ChipDefaults.chipColors(backgroundColor = Color.Black)) {
                            Text(text = "A-", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                        }
                    }

                    Text(text = "LetterSpace", fontSize = 26.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.space_bar_24px),
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Text(text = "+")
                        }

                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.space_bar_24px),
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Text(text = "-")
                        }
                    }

                    Text(text = "LetterHeight", fontSize = 26.sp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.height_24px),
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Text(text = "+")
                        }

                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.height_24px),
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Text(text = "-")
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
    MisaleawiAnegagerTheme {
    }
}