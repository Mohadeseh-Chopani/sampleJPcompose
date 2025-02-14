package com.example.jetpackcompose.view

import android.annotation.SuppressLint
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.R
import com.example.jetpackcompose.data.ForecastWeatherData
import com.example.jetpackcompose.data.WeatherData
import com.example.jetpackcompose.network.ApiServiceProvider
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme
import com.example.jetpackcompose.utils.Const
import com.example.jetpackcompose.utils.NetworkState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.factory.KoinViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // اگر لبه به لبه نیاز است
        setContent {
            JetpackComposeTheme {
                MainApp(mainViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text(
                        text = "منوی اصلی",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp)
                    )
                    Divider()

                    NavigationDrawerItem(
                        label = { Text(Const.HOME) },
                        selected = currentRoute(navController) == Const.HOME,
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home Icon") },
                        onClick = {
                            navController.navigate(Const.HOME) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            coroutineScope.launch { drawerState.close() }
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text(Const.BUY) },
                        selected = currentRoute(navController) == Const.BUY,
                        icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Buy Icon") },
                        onClick = {
                            navController.navigate(Const.BUY) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            coroutineScope.launch { drawerState.close() }
                        }
                    )
                }
            }
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState) { snackbarData ->
                        Snackbar(
                            snackbarData = snackbarData,
                            containerColor = Color.Gray,
                            contentColor = Color.Black,
                            actionColor = Color.Red,
                            shape = MaterialTheme.shapes.medium
                        )
                    }
                },
                topBar = {
                    val route = currentRoute(navController)

                    if (route == Const.BUY) {
                        TopAppBar(
                            title = { Text("صفحه خرید") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "Back Icon")
                                }
                            }
                        )
                    } else {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "صفحه اصلی",
                                    textAlign = TextAlign.Center
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Icon drawer", tint = Color.Black)
                                }
                            },
                            modifier = Modifier.background(Color.Red)
                        )
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Const.HOME,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Const.HOME) {
                        HomeScreenContent(snackBarHostState, mainViewModel = mainViewModel)
                    }
                    composable(Const.BUY) {
                        BuyScreenContent()
                    }
                }
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val entry = navController.currentBackStackEntryAsState().value
    return entry?.destination?.route
}

@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@Composable
fun HomeScreenContent(snackbarHostState: SnackbarHostState, mainViewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var textFieldValue by remember { mutableStateOf("") }
    val weatherData by mainViewModel.weatherData.collectAsState()

        LaunchedEffect(Unit) {
            mainViewModel.loadData(ApiServiceProvider.API_KEY, "تهران")
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("نام کاربر") },
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "متن شما: $textFieldValue",
                    actionLabel = "باشه",
                    duration = SnackbarDuration.Short
                )
            }
        }) {
            Text("نشان دادن Snackbar")
        }

//        LazyRow(modifier = Modifier.padding(8.dp)
//            .clipToBounds()) { items(20) {
//            ImageCard(painterResource(R.drawable.ic_launcher_foreground), "title")
//        } }

        Spacer(modifier = Modifier.height(16.dp))

        when (weatherData) {
            is NetworkState.Loading -> {
                CircularProgressIndicator(color = Color.DarkGray)
            }
            is NetworkState.Success -> {
                val successState = (weatherData as NetworkState.Success<ForecastWeatherData>).data
                val weatherList = successState.result.list.size ?: 0

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val data = successState.result.list
                    items(weatherList) {
                        val linkUrl: String = ApiServiceProvider.BASE_URL + "?token="+
                                ApiServiceProvider.API_KEY + "&action=icon&id=" +
                                data.get(it).weather.get(0).icon

                        ImageCard(url = linkUrl, title = "hjhgjg")
                        Log.d("MOX", "HomeScreenContent: "+data.get(0).weather.get(0).icon )
                    }
                }
            }
            is NetworkState.Error -> {
//                Log.e("MOX", "HomeScreenContent: ${(weatherState as NetworkState.Error<ForecastWeatherData>).error.message}")
            }
        }


    }
}

@Composable
fun BuyScreenContent() {
//   ImageCard(painterResource(R.drawable.ic_launcher_foreground), "test Image")
}

@Composable
fun JetpackComposeTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}


@Composable
fun ImageCard(url: String, title: String) {

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .offset(x = 5.dp, y = 5.dp)
//            .padding(2.dp)
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .offset(x = 10.dp, y = 10.dp)
                .height(160.dp)
                .padding(0.dp, 2.dp, 0.dp, 2.dp),
            border = BorderStroke(1.dp, color = Color.DarkGray),
            shape = RoundedCornerShape(15.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(url),
                contentDescription = title,
                contentScale = ContentScale.Crop,
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomCenter,
            )
            {
                Text("hifshhsfkh", style = TextStyle(color = Color.Black, fontSize = 14.sp),
                    modifier = Modifier.align(Alignment.BottomCenter))
            }
        }

        Text(
            title, style = TextStyle(fontSize = 16.sp), modifier = Modifier
                .offset(x = 150.dp, y = 60.dp)
        )
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeTheme {
        val snackbarHostState = remember { SnackbarHostState() }

//        Greeting("Android test")
//        ImageCard(painterResource(R.drawable.ic_launcher_foreground), "this is a test")
        BuyScreenContent()
//        HomeScreenContent(snackbarHostState)
    }
}