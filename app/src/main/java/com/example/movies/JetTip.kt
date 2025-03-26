package com.example.movies

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movies.ui.components.InputField
import com.example.movies.ui.widget.RoundIconButton
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetTipApp() {
    // App theme colors
    val primaryColor = Color(0xFF6200EE)
    val secondaryColor = Color(0xFFBB86FC)
    val backgroundColor = Color(0xFFF5F5F5)

    val amountState = remember { mutableStateOf("") }
    val tipPercentState = remember { mutableFloatStateOf(15f) }
    val splitCountState = remember { mutableIntStateOf(1) }
    val validState = remember(amountState.value) { amountState.value.trim().isNotEmpty() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Calculate tip and total per person
    val tipAmount = remember(amountState.value, tipPercentState.floatValue) {
        if (amountState.value.isNotEmpty() && validState) {
            val amount = amountState.value.toDouble()
            val tipPercent = tipPercentState.floatValue
            (amount * tipPercent / 100).toDouble()
        } else {
            0.0
        }
    }

    val totalPerPerson = remember(amountState.value, tipAmount, splitCountState.intValue) {
        if (amountState.value.isNotEmpty() && validState && splitCountState.intValue > 0) {
            val amount = amountState.value.toDouble()
            (amount + tipAmount) / splitCountState.intValue
        } else {
            0.0
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "JetTip",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor
                ),
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TopHeader(amount = totalPerPerson)

            MainContent(
                amountState = amountState,
                tipPercentState = tipPercentState,
                splitCountState = splitCountState,
                tipAmount = tipAmount,
                validState = validState,
                keyboardController = keyboardController
            )
        }
    }
}

@Composable
fun TopHeader(amount: Double = 0.0) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFBB86FC)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Per Person",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$${String.format("%.2f", amount)}",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
    }
}

@Composable
fun MainContent(
    amountState: MutableState<String>,
    tipPercentState: MutableState<Float>,
    splitCountState: MutableState<Int>,
    tipAmount: Double,
    validState: Boolean = false,
    keyboardController: SoftwareKeyboardController? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BillForm(
                amountState = amountState,
                tipPercentState = tipPercentState,
                splitCountState = splitCountState,
                tipAmount = tipAmount,
                validState = validState,
                onValueChange = { billAmount ->
                    amountState.value = billAmount
                }
            )
        }
    }
}

@Composable
fun BillForm(
    amountState: MutableState<String>,
    tipPercentState: MutableState<Float>,
    splitCountState: MutableState<Int>,
    tipAmount: Double,
    validState: Boolean,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Bill amount input
        InputField(
            valueState = amountState,
            labelId = "Enter Bill Amount",
            enabled = true,
            isSingleLine = true,
            onAction = KeyboardActions {
                if (!validState) return@KeyboardActions
                keyboardController?.hide()
                onValueChange(amountState.value.trim())
            }
        )

        // Tip section
        TipSection(
            tipPercentState = tipPercentState,
            tipAmount = tipAmount
        )

        // Split section
        SplitSection(splitCountState = splitCountState)
    }
}

@Composable
fun TipSection(
    tipPercentState: MutableState<Float>,
    tipAmount: Double
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tip",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "$${String.format("%.2f", tipAmount)}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${tipPercentState.value.toInt()}%",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Tip Percentage",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Tip slider
        Slider(
            value = tipPercentState.value,
            onValueChange = {
                tipPercentState.value = it
            },
            valueRange = 0f..30f,
            steps = 30,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF6200EE),
                activeTrackColor = Color(0xFF6200EE),
                inactiveTrackColor = Color(0xFFDDDDDD)
            )
        )
    }
}

@Composable
fun SplitSection(
    splitCountState: MutableState<Int>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Split",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundIconButton(
                imageVector = Icons.Default.Remove,
                backgroundColor = Color(0xFFE0E0E0),
                tint = Color(0xFF6200EE),
                onClick = {
                    if (splitCountState.value > 1) {
                        splitCountState.value -= 1
                    }
                }
            )

            Text(
                text = "${splitCountState.value}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            RoundIconButton(
                imageVector = Icons.Default.Add,
                backgroundColor = Color(0xFF6200EE),
                tint = Color.White,
                onClick = {
                    splitCountState.value += 1
                }
            )
        }
    }
}