package com.eloemi.todo.tasklist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eloemi.todo.data.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TasksListViewModel : ViewModel() {
    private val webService = Api.tasksWebService

    public val tasksStateFlow = MutableStateFlow<List<Task>>(emptyList())

    fun refresh() {
        viewModelScope.launch {
            val response = webService.fetchTasks() // Call HTTP (opération longue)
            if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }
            val fetchedTasks = response.body()!!
            tasksStateFlow.value = fetchedTasks // on modifie le flow, ce qui déclenche ses observers
        }
    }

    // à compléter plus tard:
    fun add(task: Task) {
        viewModelScope.launch {
            val response = webService.create(task)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }
            val createdTask = response.body()!!
            tasksStateFlow.value = tasksStateFlow.value.map { if (it.id == createdTask.id) createdTask else it }
        }
    }
    fun edit(task: Task) {
        viewModelScope.launch {
            val response = webService.update(task, task.id)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }
            val updatedTask = response.body()!!
            tasksStateFlow.value = tasksStateFlow.value.map { if (it.id == updatedTask.id) updatedTask else it }
        }
    }
    fun remove(task: Task) {
        viewModelScope.launch {
            val response = webService.delete(task.id)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }
            tasksStateFlow.value = tasksStateFlow.value - task
        }
    }
}