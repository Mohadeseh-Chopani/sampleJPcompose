package com.example.jetpackcompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme
import com.example.jetpackcompose.utils.Const
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )

                    val navController = rememberNavController()
//                    HomeScreen()
//
                    NavHost(navController = navController, startDestination = Const.HOME) {
                        composable(Const.HOME) { HomeScreen() }
                        composable(Const.BUY) { BuyScreen(navController) }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {

    }
}

@Composable
fun ImageCard(painter: Painter, title: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 10.dp, y = 10.dp)
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .offset(x = 10.dp, y = 10.dp)
                .height(170.dp),
            border = BorderStroke(2.dp, color = Color.Magenta),
            shape = RoundedCornerShape(15.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomCenter
            )
            {
                Text("hifshhsfkh", style = TextStyle(color = Color.Black, fontSize = 14.sp))
            }
        }

        Text(
            "jcjgfffhgf", style = TextStyle(fontSize = 16.sp), modifier = Modifier
                .offset(x = 150.dp, y = 60.dp)
        )
    }
}

@Composable
fun DrawerLayout(function: @Composable () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()

    // ✅ Current Route (to highlight selected item)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Divider()
                NavigationDrawerItem(
                    label = { Text(Const.HOME) },
                    selected = currentRoute == Const.HOME,
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home Icon") },
                    onClick = {
                        navController.navigate(Const.HOME) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        Log.d("MOX", "InputInformation: " + "home selected")
                    }
                )
                NavigationDrawerItem(
                    label = { Text(Const.BUY) },
                    selected = currentRoute == Const.BUY,
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Buy Icon") },
                    onClick = {
                        navController.navigate(Const.BUY) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        Log.d("MOX", "InputInformation: " + "buy selected")
                    }
                )
            }
        }
    ) {
        function()
    }
}

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    var textFieldState by remember { mutableStateOf("nhjhk") }
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()


    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        DrawerLayout {
            Scaffold(modifier = Modifier.fillMaxSize(),
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
                }, topBar = {
                    CenterAlignedTopAppBar(modifier = Modifier.background(Color.Red),
                        title = { },
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Icon drawer")
                            }
                        }
                    )
                })
            { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Const.HOME,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Const.HOME) { InfoScreen(navController) } // ✅ Add this to ensure HOME is a valid destination
                    composable(Const.BUY) { BuyScreen(navController) }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = innerPadding)
                        .padding(20.dp, 30.dp, 20.dp, 20.dp)
                ) {
                    TextField(
                        value = "",
                        onValueChange = { textFieldState = it },
                        label = { Text("") },
                        shape = MaterialTheme.shapes.small,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                message = "This is a message",
                                actionLabel = "OK",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }) {
                        Text("show snakBar")
                    }
                }
            }
        }
    }
}


@Composable
fun BuyScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("صفحه خرید", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun InfoScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("صفحه اصلی", style = MaterialTheme.typography.headlineMedium)
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeTheme {
//        Greeting("Android test")
//        ImageCard(painterResource(R.drawable.ic_launcher_foreground), "this is a test")
        HomeScreen()
    }
}