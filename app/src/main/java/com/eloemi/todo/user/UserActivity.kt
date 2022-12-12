package com.eloemi.todo.user

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.eloemi.todo.user.ui.theme.ToDoEloiseEmilieTheme

class UserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoEloiseEmilieTheme {
                var bitmap: Bitmap? by remember { mutableStateOf(null) }
                val uri: Uri? by remember { mutableStateOf(null) }
                val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                    bitmap = it
                }
                Column {
                    AsyncImage(
                        modifier = Modifier.fillMaxHeight(.2f),
                        model = bitmap ?: uri,
                        contentDescription = null
                    )
                    Button(
                        onClick = {takePicture.launch()},
                        content = { Text("Take picture") }
                    )
                    Button(
                        onClick = {},
                        content = { Text("Pick photo") }
                    )
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
    ToDoEloiseEmilieTheme {
        Greeting("Android")
    }
}