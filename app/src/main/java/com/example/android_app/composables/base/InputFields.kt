package com.example.android_app.composables.base

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.android_app.utils.copy
import com.example.android_app.utils.getNumberOfDaysInMonth
import java.io.Serializable
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateInputField(date: LocalDate, onValueChanged: (LocalDate) -> Unit) {
    val space: @Composable () -> Unit = { Spacer(modifier = Modifier.size(8.dp)) }

    Column {
        DropdownChoice(
            value = date.year,
            options = (1970..LocalDate.now().year).toList(),
            onChoiceChange = { onValueChanged(date.copy(year = it)) },
            description = "Year"
        )
        space()

        DropdownChoice(
            value = date.monthValue,
            options = (1..12).toList(),
            onChoiceChange = { onValueChanged(date.copy(month = it)) },
            description = "Month"
        )
        space()

        val daysInMonth: Int = getNumberOfDaysInMonth(date.monthValue, date.year)
        DropdownChoice(
            value = date.dayOfMonth,
            options = (1..daysInMonth).toList(),
            onChoiceChange = { onValueChanged(date.copy(day = it)) },
            description = "Day"
        )
        space()

    }
}

@Composable
fun <T> DropdownChoice(
    value: T,
    options: List<T>,
    onChoiceChange: (T) -> Unit,
    description: String,
    modifier: Modifier = Modifier
) where T : Serializable {
    val expanded = rememberSaveable { mutableStateOf(false) }
    val selectedOption = rememberSaveable { mutableStateOf<T>(value) }
    Column(modifier) {
        Row(horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()) {
            OutlinedButton(onClick = { expanded.value = true }) {
                Text("${description}: ${selectedOption.value}")
            }
        }
        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    selectedOption.value = option
                    onChoiceChange(option)
                }) {
                    Text(option.toString())
                }
                Divider()
            }
        }
    }


}