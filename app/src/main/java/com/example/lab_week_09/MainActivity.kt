package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import com.example.lab_week_09.ui.theme.OnBackgroundItemText
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.PrimaryTextButton

data class Student(
    var name: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    App(navController = navController)
                }
            }
        }
    }
}

@Composable
fun App(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Home { listDataString ->
                navController.navigate("resultContent/?listData=$listDataString")
            }
        }

        composable(
            route = "resultContent/?listData={listData}",
            arguments = listOf(
                navArgument("listData") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val listDataString = backStackEntry.arguments?.getString("listData").orEmpty()
            ResultContent(listDataString = listDataString)
        }
    }
}

@Composable
fun Home(
    navigateFromHomeToResult: (String) -> Unit
) {
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    var inputField by remember {
        mutableStateOf(Student(""))
    }

    HomeContent(
        listData = listData,
        inputField = inputField,
        onInputValueChange = { newValue ->
            inputField = inputField.copy(name = newValue)
        },
        onButtonClick = {
            listData.add(inputField)
            inputField = Student("")
        },
        navigateFromHomeToResult = {
            // Untuk sekarang, kirim join string (belum pakai Moshi)
            val joined = listData.joinToString(separator = ",") { it.name }
            navigateFromHomeToResult(joined)
        }
    )
}

@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    navigateFromHomeToResult: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OnBackgroundTitleText(
            text = stringResource(id = R.string.enter_item)
        )

        TextField(
            value = inputField.name,
            onValueChange = { newValue ->
                onInputValueChange(newValue)
            }
        )

        // Row: Submit & Finish
        Column {
            PrimaryTextButton(
                text = stringResource(id = R.string.button_click),
                onClick = onButtonClick
            )

            PrimaryTextButton(
                text = stringResource(id = R.string.button_navigate),
                onClick = navigateFromHomeToResult
            )
        }

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(listData) { student ->
                OnBackgroundItemText(text = student.name)
            }
        }
    }
}

@Composable
fun ResultContent(listDataString: String) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OnBackgroundTitleText(text = "Result Screen")

        // List sederhana dari string join, nanti di-boost pakai Moshi
        val items = if (listDataString.isBlank()) {
            emptyList()
        } else {
            listDataString.split(",")
        }

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(items) { name ->
                OnBackgroundItemText(text = name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppNav() {
    LAB_WEEK_09Theme {
        val navController = rememberNavController()
        App(navController = navController)
    }
}
