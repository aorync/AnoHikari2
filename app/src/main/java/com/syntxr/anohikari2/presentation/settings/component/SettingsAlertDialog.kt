package com.syntxr.anohikari2.presentation.settings.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syntxr.anohikari2.ui.theme.AnoHikariTheme

@Composable
fun SettingsAlertDialog(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    currentSelected: String,
    actions: List<Action>,
    confirmButtonText: String = "",
    dismissButtonText: String = "",
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        icon = {
            Icon(imageVector = icon, contentDescription = "")
        },
        title = {
                Text(text = title)
        },
        onDismissRequest = onDismissClick,
        confirmButton = {
            if (confirmButtonText.isNotEmpty())
                TextButton(onClick = onConfirmClick) {
                    Text(text = confirmButtonText)
                }
        },
        dismissButton = {
            if (dismissButtonText.isNotEmpty())
                TextButton(onClick = onDismissClick) {
                    Text(text = dismissButtonText)
                }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                content = {
                    items(actions) { action ->
                        ActionItem(
                            text = action.text,
                            onClick = action.onClick,
                            buttonColors = if (currentSelected == action.text) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun ActionItem(
    text: String,
    onClick: () -> Unit,
    buttonColors: Color = MaterialTheme.colorScheme.surface,
) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColors,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

data class Action(
    val text: String,
    val onClick: () -> Unit,
)

@Preview()
@Composable
fun ItemAlertPreview() {
    AnoHikariTheme {
    }
}