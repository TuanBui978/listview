package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, myViewModel: MyViewModel = viewModel()) {
    val uiState = myViewModel.uiState.collectAsState()
    val navControl = rememberNavController()


    NavHost(navController = navControl, startDestination = "Home") {
        composable("Home") {
            Box (Modifier.fillMaxSize()){
                val listIndex = navControl.currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("Index")?.observeAsState()
                val value = navControl.currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("Value")?.observeAsState()
                value?.value?.let {
                    listIndex?.value?.let {
                            index->
                        myViewModel.updateList(it, index)
                    }
                }
                Text(text = uiState.value.num.toString(), fontSize = 50.sp, modifier = Modifier.align(Alignment.Center))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    uiState.value.list.forEachIndexed() {index, it->
                        item {
                            Text(text = it.toString(), fontSize = 20.sp, modifier = Modifier
                                .fillMaxWidth()
                                .combinedClickable(onLongClick = {
                                    myViewModel.deleteList(index)
                                }) {
                                    navControl.navigate("Screen/$it/$index")
                                })
                        }
                    }
                }
                SmallFloatingActionButton(onClick = {
                    myViewModel.insertNum()
                },
                    modifier = Modifier.align(Alignment.BottomEnd)) {
                    Icon( Icons.Default.Add, contentDescription = null)
                }
                SmallFloatingActionButton(onClick = {
                    myViewModel.desertNum()
                },
                    modifier = Modifier.align(Alignment.BottomStart)) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        }
        composable("Screen/{Value}/{index}", arguments = listOf(navArgument("Value"){
            type = NavType.IntType
        }, navArgument("index") {
            type = NavType.IntType
        })) {
            navBackStackEntry ->
            val value1 = navBackStackEntry.arguments?.getInt("Value")
            val value2 = navBackStackEntry.arguments?.getInt("index")
            Screen(Value = value1, Index = value2, onOkButtonClick = {value, index ->
                navControl.previousBackStackEntry?.savedStateHandle?.set("Value", value)
                navControl.previousBackStackEntry?.savedStateHandle?.set("Index", index)
                navControl.popBackStack()
            })
        }
    }



}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}