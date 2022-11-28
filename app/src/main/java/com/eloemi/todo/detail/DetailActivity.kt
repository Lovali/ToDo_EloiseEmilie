package com.eloemi.todo.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eloemi.todo.detail.ui.theme.ToDoEloiseEmilieTheme
import com.eloemi.todo.tasklist.Task
import java.util.*

class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newTask = Task(id = UUID.randomUUID().toString(), title = "New Task !")
        val onValidate = { task : Task -> Unit
            intent.putExtra("task", task);
            setResult(RESULT_OK, intent);
            finish()
        }
        setContent {
            ToDoEloiseEmilieTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Detail(onValidate, newTask)
                }
            }
        }
    }
}

@Composable
fun Detail(onValidate: (Task) -> Unit, newTask: Task) {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Task Detail", style = MaterialTheme.typography.h3)
        Text("title")
        Text("description")
        Button(onClick = { onValidate(newTask) }) {
            Text("Validate")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    ToDoEloiseEmilieTheme {
        //Detail("Android")
    }
}