package com.syntxr.anohikari2.presentation.home.sora.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syntxr.anohikari2.R
import com.syntxr.anohikari2.data.source.local.qoran.entity.Sora
import com.syntxr.anohikari2.ui.theme.AnoHikariTheme
import com.syntxr.anohikari2.ui.theme.montserratFontFamily
import com.syntxr.anohikari2.ui.theme.novaMonoFontFamily
import com.syntxr.anohikari2.ui.theme.ubuntuMonoFontFamily

@Composable
fun SoraItem(
    sora: Sora,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier,
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
                    .size(56.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${sora.soraNo}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontFamily = novaMonoFontFamily,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = sora.soraEn ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontFamily = novaMonoFontFamily,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = sora.soraId ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    fontFamily = ubuntuMonoFontFamily,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = sora.soraPlace ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = stringResource(id = R.string.txt_total_aya, sora.ayas ?: 0),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    fontFamily = ubuntuMonoFontFamily,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }
    }
}

@Preview
@Composable
fun ItemSoraPreview() {
    AnoHikariTheme {
//        SoraItem()
    }
}