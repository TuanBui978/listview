package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Screen(Value: Int?, Index: Int?, onOkButtonClick: (Int?, Int?)->Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        var value by remember {
            mutableStateOf(value = Value.toString())
        }
        OutlinedTextField(value = value, onValueChange = {
            value = it
        }, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth())
        TextButton(onClick = { onOkButtonClick(value.toIntOrNull(), Index) }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "OK")
        }
    }
}