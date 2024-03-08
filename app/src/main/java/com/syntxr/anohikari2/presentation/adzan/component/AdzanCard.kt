package com.syntxr.anohikari2.presentation.adzan.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syntxr.anohikari2.R

@Composable
fun AdzanCard(
    modifier: Modifier = Modifier,
    shalatName: String,
    shalatTime: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = shalatName,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = shalatTime,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun AdzanLocationCard(
    locality : String,
    currentLocation: String,
    modifier: Modifier = Modifier
){

    var isShowLocationAdvanced by remember {
        mutableStateOf(false)
    }
    
    Card(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = R.string.txt_current_location, locality),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { isShowLocationAdvanced = !isShowLocationAdvanced }
                    ) {
                        Icon(
                            imageVector = if (!isShowLocationAdvanced) Icons.Rounded.KeyboardArrowDown else Icons.Rounded.KeyboardArrowUp,
                            contentDescription = "Show Arrow Down"
                        )
                    }
                }
                
                AnimatedVisibility(visible = isShowLocationAdvanced) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = currentLocation,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

            }
        }
    )
}

@Composable
fun TurnLocationCard(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Rounded.LocationOn,
            contentDescription = "location_icon"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = ""
        )
    }
}


@Preview
@Composable
fun AdzanCardPreview() {
//    AdzanCard(shalatName = "Shubuh", shalatTime = shalatTime.fajr)
}