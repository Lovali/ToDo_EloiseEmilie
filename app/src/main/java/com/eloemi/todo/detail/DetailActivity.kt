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

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoEloiseEmilieTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Detail("Android")
                }
            }
        }
    }
}

@Composable
fun Detail(name: String) {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Task Detail", style = MaterialTheme.typography.h3)
        Text("title")
        Text("description")
        Button(onClick = { /*TODO*/ }) {
            
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    ToDoEloiseEmilieTheme {
        Detail("Android")
    }
}