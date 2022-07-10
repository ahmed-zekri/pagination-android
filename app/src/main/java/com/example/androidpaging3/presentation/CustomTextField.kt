package com.example.androidpaging3.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField(
    labelText: String,
    modifier: Modifier = Modifier,
    dividerColor: Color,
    dividerThickness: Dp = 1.dp,
    spacer: Dp,
    textStyle: TextStyle, receivedValue: String.() -> Unit
) {
    var value by remember { mutableStateOf(TextFieldValue("")) }
    val dividerState = remember {
        mutableStateOf(true)
    }
    BasicTextField(
        value = value,
        onValueChange = {
            value = it
            receivedValue(it.text)
        },
        modifier = modifier
            .onFocusChanged {
                dividerState.value = !it.isFocused
            },
        decorationBox = { innerTextField ->
            var mainModifier: Modifier = modifier
            if (!dividerState.value) {
                mainModifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = Color.Black
                    )
                    .padding(8.dp)
            }

            Column(
                modifier = mainModifier,
                content = {
                    Text(text = labelText, style = textStyle)
                    Spacer(modifier = Modifier.size(spacer))
                    innerTextField()
                    if (dividerState.value) {
                        Divider(
                            thickness = dividerThickness, color = dividerColor,
                            modifier = mainModifier
                        )
                    }
                }
            )
        }
    )
}
