package com.eloemi.todo.data.tasklist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task (
    @SerialName("id")
    val id : String = "default id",
    @SerialName("content")
    val title: String = "default title",
    @SerialName("description")
    val description: String = "default description"
) : java.io.Serializable
