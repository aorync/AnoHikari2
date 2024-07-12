package com.syntxr.anohikari3.presentation.home.jozz.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syntxr.anohikari3.presentation.read.AYA_BY_JOZZ_SORA
import com.syntxr.anohikari3.ui.theme.AnoHikariTheme
import com.syntxr.anohikari3.ui.theme.montserratFontFamily
import com.syntxr.anohikari3.ui.theme.novaMonoFontFamily
import com.syntxr.anohikari3.ui.theme.ubuntuMonoFontFamily
import com.syntxr.anohikari3.ui.theme.uthmanHafsFontFamily

@Composable
fun JozzItem(
    jozzNo: Int,
    soras: List<String?>,
    sorasNo: List<Int?>,
    ayasNo: List<Int?>,
    modifier: Modifier = Modifier,
    navigation: (soraNo: Int?, jozzNo: Int?, indexType: Int, scrollPos: Int?) -> Unit,
) {
    Card(
        colors = CardDefaults
            .cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
    ) {
        Column(
            modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Juz $jozzNo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    fontFamily = montserratFontFamily

                )
                Text(
                    text = "Read Juz",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline,
                    fontFamily = ubuntuMonoFontFamily
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (soras.isNotEmpty() && sorasNo.isNotEmpty()) {
                for (index in soras.indices) {
                    Spacer(modifier = Modifier.height(4.dp))
                    JozzSorasItem(
                        modifier = modifier.clickable {
                            navigation(
                                sorasNo[index],
                                null,
                                AYA_BY_JOZZ_SORA,
                                ayasNo[index]?.minus(1)
                            )
                        },
                        sora = soras[index] ?: "",
                        soraNo = sorasNo[index] ?: 0
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
fun JozzSorasItem(
    sora: String,
    soraNo: Int,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        colors = CardDefaults
            .cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
        border = BorderStroke(0.4.dp, MaterialTheme.colorScheme.onSurface),
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(46.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$soraNo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontFamily = novaMonoFontFamily,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = sora,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontFamily = uthmanHafsFontFamily,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ItemJozzPreview() {
    AnoHikariTheme(
        darkTheme = true
    ) {
//        JozzItem()
    }
}