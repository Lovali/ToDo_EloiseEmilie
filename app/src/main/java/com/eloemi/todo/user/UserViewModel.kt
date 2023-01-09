package com.eloemi.todo.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eloemi.todo.data.Api
import com.eloemi.todo.data.ArgsModified
import com.eloemi.todo.data.User
import com.eloemi.todo.data.UserModified
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val webService = Api.userWebService

    public val userStateFlow = MutableStateFlow<User>(User("", "", ""))

    fun refresh() {
        viewModelScope.launch {
            val response = webService.fetchUser() // Call HTTP (opération longue)
            if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }
            val fetchedUser = response.body()!!
            userStateFlow.value = fetchedUser // on modifie le flow, ce qui déclenche ses observers
        }
    }

    fun edit(user: User) {
        var argsModified = ArgsModified(user.name, user.email)
        var userModified = UserModified("user_update", user.id, argsModified)
        viewModelScope.launch {
            val response = webService.update(userModified)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }
            userStateFlow.value = user
        }
    }
}