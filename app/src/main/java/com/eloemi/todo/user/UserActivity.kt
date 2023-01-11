package com.eloemi.todo.user

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.eloemi.todo.data.Api
import com.eloemi.todo.data.User
import com.eloemi.todo.user.ui.theme.ToDoEloiseEmilieTheme
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserActivity : ComponentActivity() {
    private val capturedUri by lazy {
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
    }
    private val viewModelUser : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelUser.refresh()
        val onValidate = { user : User -> Unit
            viewModelUser.edit(user)
            finish()
        }
        setContent {
            ToDoEloiseEmilieTheme {
                var bitmap: Bitmap? by remember { mutableStateOf(null) }
                var uri: Uri? by remember { mutableStateOf(null) }
                val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                    if (success) uri = capturedUri
                    lifecycleScope.launch {
                        avatar(uri.toRequestBody())
                    }
                }
                val pickPhoto = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                    uri = it
                    lifecycleScope.launch {
                        avatar(uri.toRequestBody())
                    }
                }
                val pickPhotoPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
                    pickPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
                val user by viewModelUser.userStateFlow.collectAsState()
                if (uri == null) uri = user.avatar?.toUri()
                var userModified by remember(user) { mutableStateOf(user) }
                Column {
                    Text("User detail", style = MaterialTheme.typography.h3)
                    AsyncImage(
                        modifier = Modifier.fillMaxHeight(.2f),
                        model = bitmap ?: uri,
                        contentDescription = null
                    )
                    Button(
                        onClick = { takePicture.launch(capturedUri) },
                        content = { Text("Take picture") }
                    )
                    Button(
                        onClick = { pickPhotoPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE) },
                        content = { Text("Pick photo") }
                    )
                    OutlinedTextField(
                        value = userModified.name  ,
                        onValueChange = {userModified = userModified.copy(name = it)},
                        label = { Text(text = "Name")}
                    )
                    OutlinedTextField(
                        value = userModified?.email ?: "jean.dupont@gmail.com",
                        onValueChange = {userModified = userModified.copy(email = it)},
                        label = { Text(text = "Email")}
                    )
                    Button(
                        onClick = { onValidate(userModified) } ) {
                            Text("Validate")
                        }
                }
            }
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

    private fun Uri?.toRequestBody(): MultipartBody.Part {
        val fileInputStream = this?.let { contentResolver.openInputStream(it) }!!
        val fileBody = fileInputStream.readBytes().toRequestBody()
        return MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "avatar.jpg",
            body = fileBody
        )
    }

    private suspend fun avatar(bitmap: MultipartBody.Part) {
        Api.userWebService.updateAvatar(bitmap)
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