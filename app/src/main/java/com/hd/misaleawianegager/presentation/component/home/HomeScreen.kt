package com.hd.misaleawianegager.presentation.component.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.utils.compose.TextCard

@Composable
fun HomeContent(homeData: State<List<String>>,
                modifier: Modifier,
                toDetail: ( from: String, text: String, first: String) -> Unit,
                lazyListState: LazyListState,
                arg3: MutableState<String>
                  ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
                LazyColumn(state = lazyListState, modifier = Modifier.padding(8.dp)) {
                    items(items = homeData.value, key = { item -> item }) { it ->
                        TextCard(item = it, from = "ዋና", first = arg3.value, toDetail = toDetail)
                    }
                }
            }
}




@Composable
fun AppInfoDialog(openDialog: MutableState<Boolean>, toBoarding: () -> Unit) {

        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(16.dp),
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bitmap),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "App Information",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            text = {
                SelectionContainer {
                    Column(horizontalAlignment = Alignment.Start) {
                        InfoRow("Version:", "2.9")
                        InfoRow("Developer:", "Haile Temesgen")
                        InfoRow("Email:", "htemesgen400@gmail.com")

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = "Go to Introduction",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier
                                .clickable {
                                    toBoarding.invoke()
                                    openDialog.value = false
                                }
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            },
            confirmButton = { },
            dismissButton = {
                OutlinedButton(
                    onClick = { openDialog.value = false },
                    shape = RoundedCornerShape(8.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary)
                    )
                ) {
                    Text(
                        "OK",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
