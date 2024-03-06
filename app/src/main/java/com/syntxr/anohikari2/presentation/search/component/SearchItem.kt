package com.syntxr.anohikari2.presentation.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.syntxr.anohikari2.ui.theme.montserratFontFamily
import com.syntxr.anohikari2.ui.theme.rubikFontFamily
import com.syntxr.anohikari2.utils.AppGlobalState
import com.syntxr.anohikari2.utils.Converters
import com.syntxr.anohikari2.utils.toAnnotatedString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchItem(
    ayaText: String,
    translation: AnnotatedString,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ){
            if (AppGlobalState.isTajweed){
                Text(
                    text = Converters.applyTajweed(LocalContext.current, ayaText).toAnnotatedString(
                        MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = modifier.align(Alignment.End),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Normal,
                    fontFamily = rubikFontFamily,
                )
            }else {
                Text(
                    text = ayaText,
                    modifier = modifier.align(Alignment.End),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Normal,
                    fontFamily = rubikFontFamily,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = translation,
                modifier = modifier.align(Alignment.Start),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                fontFamily = montserratFontFamily,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}