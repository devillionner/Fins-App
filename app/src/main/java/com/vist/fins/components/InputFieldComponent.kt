package com.vist.fins.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vist.fins.ui.theme.Dark_inputs_bg
import com.vist.fins.ui.theme.Dark_text_bg_rounded


// USE in app
@Composable
fun InputField(
    name: String,
    label: String = "Label",
    keyboardType: KeyboardType,
    onValChange: ((String) -> Unit)?,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val focusManager = LocalFocusManager.current

    if (onValChange != null) {
        InputFieldComponent(
            text = name,
            onChange = onValChange,
            label = label,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            visualTransformation = visualTransformation
        )
    }
}

@Composable
fun InputFieldBG(
    name: String,
    keyboardType: KeyboardType,
    onValChange: ((String) -> Unit)?,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val focusManager = LocalFocusManager.current

    if (onValChange != null) {
        InputFieldBGComponent(
            text = name,
            onChange = onValChange,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            visualTransformation = visualTransformation
        )
    }
}

// Do NOT USE in app
@Composable
fun InputFieldComponent(
    text: String,
    onChange: (String) -> Unit,
    modifier: Modifier,
    singleLine: Boolean = true,
    label: String = "Some val",
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation
) {
    OutlinedTextField(
        value = text,
        onValueChange = onChange,
        modifier = modifier,
        singleLine = singleLine,
        label = {
            if (label != "") {
                Text(text = label)
            }
        },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = 1,
        textStyle = TextStyle(fontSize = 26.sp),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = visualTransformation
    )
}

@Composable
fun InputFieldBGComponent(
    text: String,
    onChange: (String) -> Unit,
    modifier: Modifier,
    singleLine: Boolean = true,
    label: String = "Some val",
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation
) {
    TextField(
        value = text,
        onValueChange = onChange,
        modifier = modifier,
        singleLine = singleLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = 1,
        textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Normal),
        shape = RoundedCornerShape(10.dp),
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Dark_inputs_bg,
            focusedIndicatorColor =  Color.Transparent, //hide the indicator
            unfocusedIndicatorColor = Color.Transparent
        )
    )

}