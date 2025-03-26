@file:Suppress("DEPRECATION")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.movies.ui.theme.MoviesTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesTheme {
                JetTipApp()
            }
        }

    }
}


@Composable
fun AuthenticationScreen() {
    var textState by remember { mutableStateOf("Hello") }
    var numberCount by remember { mutableIntStateOf(0) }


    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    val coroutineScope = rememberCoroutineScope()

    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Properties")
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.14f)
                    .padding(16.dp)
                    .background(
                        Color.Blue,
                        shape = RoundedCornerShape(16.dp)
                    )


            ) {
                Row {
                    Text(
                        text = textState,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                    if (textState != "Hello") {
                        Text(
                            text = numberCount.toString(),
                            color = Color.Yellow,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }


                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd),
                    onClick = {
                        textState = "Hello Clicked"
                        numberCount += 1
                        Log.d("Counter", "$numberCount")
                    },
                ) {
                    Text(text = "Click me")
                }

            }

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    coroutineScope.launch {
                        isRefreshing = true
                        delay(1500)
                        numberCount = 0
                        textState = "Hello"
                        isRefreshing = false
                    }
                }
            ) {
                if (!isRefreshing) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp)

                    ) {
                        items(50) { index ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color.White,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clickable {
                                        Log.d("Counter", "Clicked")
                                        showBottomSheet = true
                                    }
                            ) {
                                Row {
                                    Box(Modifier.fillMaxWidth(0.2f)) {
                                        Image(

                                            painter = painterResource(id = R.drawable.ic_launcher_background),
                                            contentDescription = "image",
                                            modifier = Modifier
                                                .width(150.dp)
                                                .clip(shape = RoundedCornerShape(16.dp))

                                        )
                                    }


                                    Column {
                                        Text(
                                            text = "Hello",
                                            color = Color.Black,
                                            modifier = Modifier.padding(
                                                horizontal = 16.dp,
                                                vertical = 5.dp
                                            )
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.LocationOn,
                                                contentDescription = "Location",
                                                tint = Color.Gray,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "New York",
                                                color = Color.Gray
                                            )
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(
                                                horizontal = 16.dp,
                                                vertical = 5.dp
                                            )
                                        ) {
                                            Text(
                                                "$156, 00${index}", style = TextStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 15.sp,
                                                )
                                            )
                                        }

                                    }
                                }

                            }
                        }
                    }
                } else {
                    LoadingScreen()
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = bottomSheetState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .padding(bottom = 32.dp)
                    ) {
                        Text(
                            text = "Bottom Sheet Title",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "This is the content of the bottom sheet. You can add any composables here.",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                showBottomSheet = false
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }

        }}
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = Color.Blue,
            strokeWidth = 4.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationPreview() {
    MoviesTheme {
        AuthenticationScreen()
    }
}


