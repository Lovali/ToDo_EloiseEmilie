package com.eloemi.todo.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModified(
    @SerialName("type")
    val type: String = "user_update",
    @SerialName("uuid")
    val uuid : String,
    @SerialName("args")
    var args : ArgsModified
) : java.io.Serializable

@Serializable
data class ArgsModified(
    @SerialName("full_name")
    val name: String?,
    @SerialName("email")
    val email: String?
) : java.io.Serializable
