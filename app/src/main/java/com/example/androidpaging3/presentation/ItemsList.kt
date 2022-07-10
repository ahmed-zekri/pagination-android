package com.example.androidpaging3.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.androidpaging3.data.common.pagingLoadStateItem
import com.example.androidpaging3.data.remote.RetrofitService
import com.example.androidpaging3.data.repositories.MainRepository
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch


@OptIn(FlowPreview::class)
@Composable
fun ItemsList() {
    val coroutineScope = rememberCoroutineScope()
    val hotTypingFlow = remember {
        MutableStateFlow("")
    }
    val items = remember {
        mutableStateOf(
            MainRepository.getInstance(RetrofitService.getInstance()).getAllCars()

        )
    }
    LaunchedEffect(key1 = true) {
        hotTypingFlow.debounce(500).collect {
            items.value =
                MainRepository.getInstance(RetrofitService.getInstance()).getSearchCars(it)
        }


    }

    Column(Modifier.fillMaxSize()) {


        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            InputTextField(
                spacer = 2.dp,
                labelText = "",
                dividerColor = Color.Black,
                textStyle = TextStyle.Default

            ) {
                coroutineScope.launch {
                    if (this@InputTextField.isNotBlank())
                        hotTypingFlow.emit(this@InputTextField)
                }

            }
            val itemsPagingData = items.value.collectAsLazyPagingItems()
            LazyColumn {


                items(itemsPagingData) { item ->
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
                                        append((" " + item?.Category))
                                    }

                                })


                            Text(
                                buildAnnotatedString {
                                    append("It's make is")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                                        append((" " + item?.Make))
                                    }

                                })
                        }
                    }

                }

                pagingLoadStateItem(
                    loadState = itemsPagingData.loadState.append,
                    keySuffix = "append",
                    loading = {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                        }
                    },
                    error = { FaIcon(faIcon = FaIcons.CloudRain, size = 25.dp) },
                )
            }

        }

    }
}