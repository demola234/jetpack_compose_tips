package com.example.movies

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*

import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.example.movies.ui.components.InputField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetTipApp() {
    val amountState = remember { mutableStateOf("") }
    val validState = remember(amountState.value) { amountState.value.trim().isNotEmpty() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 24.dp),
        topBar = {
            TopAppBar(
                title = { Text(text = "JetTip") },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TopHeader(amount = amountState.value.toDouble())
            Spacer(modifier = Modifier.height(16.dp))
            MainContent(amountState, validState, keyboardController)
        }
    }
}


@Preview(showBackground = false)
@Composable
fun TopHeader(amount: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(16.dp))),
        color = Color(0xFFE9D7F7),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val total = "%.2f".format(amount)
    Text(text ="Total Per Person",
    style = MaterialTheme.typography.bodyLarge.copy(
        color = Color.Black,
        fontWeight = FontWeight.Bold
    )
        )
            Text(text ="$${total}",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}



@Preview
@Composable
fun MainContent(amountState: MutableState<String> = mutableStateOf(""), validState: Boolean = false, keyboardController: SoftwareKeyboardController? = null) {
    Surface  (
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(all = CornerSize(8.dp)),
        border = BorderStroke(width = 2.dp, color = Color.LightGray),
    ) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BillForm() { billAmount ->
            amountState.value = billAmount


        }
    }
    }
}

@Composable
fun BillForm(
     modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},

) {
    val amountState = remember { mutableStateOf("") }
    val validState = remember(amountState.value) { amountState.value.trim().isNotEmpty() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface {
        InputField(
            valueState = amountState,
            labelId = "Enter Amount",
            enabled = true,
            isSingleLine = true,
            onAction = KeyboardActions {
                if (!validState) return@KeyboardActions
                keyboardController?.hide()
                onValueChange(amountState.value.trim())

            }
        )
    }
}

