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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
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
        }
    )
}

@Composable
fun HomeContent(
    listData: List<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
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

        PrimaryTextButton(
            text = stringResource(id = R.string.button_click),
            onClick = onButtonClick
        )

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(listData) { student ->
                OnBackgroundItemText(text = student.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeElements() {
    LAB_WEEK_09Theme {
        val previewList = listOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
        HomeContent(
            listData = previewList,
            inputField = Student(""),
            onInputValueChange = {},
            onButtonClick = {}
        )
    }
}
