package com.example.androidpaging3


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.androidpaging3.data.common.pagingLoadStateItem
import com.example.androidpaging3.data.remote.RetrofitService
import com.example.androidpaging3.data.repositories.MainRepository
import com.example.androidpaging3.ui.theme.AndroidPaging3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPaging3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val items = MainRepository(RetrofitService.getInstance()).getAllCars()
                        .collectAsLazyPagingItems()
                    LazyColumn {
                        pagingLoadStateItem(
                            loadState = items.loadState.prepend,
                            keySuffix = "prepend",
                            loading = { CircularProgressIndicator() },
                            error = { },
                        )

                        items(items) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                                    .clickable { },
                                elevation = 10.dp
                            ) {
                                Column(
                                    modifier = Modifier.padding(15.dp)
                                ) {
                                    Text(
                                        buildAnnotatedString {
                                            append(" Car brand")
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.W900,
                                                    color = Color(0xFF4552B8)
                                                )
                                            ) {
                                                append((" " + item?.Model))
                                            }
                                        }
                                    )
                                    Text(
                                        buildAnnotatedString {
                                            append("The car was manufactured in ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                                                append(" " + item?.Year.toString())
                                            }

                                        }
                                    )

                                    Text(
                                        buildAnnotatedString {
                                            append("The car category is ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                                                append(item?.Category ?: "")
                                            }

                                        })


                                    Text(
                                        buildAnnotatedString {
                                            append("It's make is")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                                                append(item?.Make ?: "")
                                            }

                                        })
                                }
                            }

                        }

                        pagingLoadStateItem(
                            loadState = items.loadState.append,
                            keySuffix = "append",
                            loading = {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                                }
                            },
                            error = { },
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidPaging3Theme {
        Greeting("Android")
    }
}