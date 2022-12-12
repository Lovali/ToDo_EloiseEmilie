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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.eloemi.todo.data.Api
import com.eloemi.todo.user.ui.theme.ToDoEloiseEmilieTheme
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoEloiseEmilieTheme {
                var bitmap: Bitmap? by remember { mutableStateOf(null) }
                val uri: Uri? by remember { mutableStateOf(null) }
                val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                    bitmap = it
                    lifecycleScope.launch {
                        bitmap?.let { it1 -> avatar(it1.toRequestBody()) }
                    }
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

private fun Bitmap.toRequestBody(): MultipartBody.Part {
    val tmpFile = File.createTempFile("avatar", "jpg")
    tmpFile.outputStream().use { // *use* se charge de faire open et close
        this.compress(Bitmap.CompressFormat.JPEG, 100, it) // *this* est le bitmap ici
    }
    return MultipartBody.Part.createFormData(
        name = "avatar",
        filename = "avatar.jpg",
        body = tmpFile.readBytes().toRequestBody()
    )
}

private suspend fun avatar(bitmap: MultipartBody.Part) {
    Api.userWebService.updateAvatar(bitmap)
}