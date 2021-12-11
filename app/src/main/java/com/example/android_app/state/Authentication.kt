package com.example.android_app.state

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.android_app.data.AuthDTO
import com.example.android_app.network.BooksAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationState {
    var authError: String by mutableStateOf("")
        private set

    var token: String by mutableStateOf("")
        private set

    var username: String by mutableStateOf("")
        private set

    val isAuthenticated: Boolean
        get() = token.isNotBlank()

    val bearerToken: String
        get() = if(token.isNotBlank()) "Bearer $token" else ""

    val hasError: Boolean
        get() = authError.isNotBlank()


    fun login(username: String, password: String, onSuccess: ()->Unit = {}, onFailure: ()->Unit={}) {
        BooksAuth.retrofitService.authenticate(AuthDTO(username, password)).enqueue(
            object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    response.body()?.let { token = it}
                    onSuccess()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    t.message?.let { Log.d("DEBUG", it) }
                    onFailure()
                }
            }
        )
    }

    fun logout() {
        username = ""
    }

    companion object {
        val Saver: Saver<AuthenticationState, *> = mapSaver(
            save = {
                mapOf(
                    "token" to it.token,
                    "authError" to it.authError,
                    "username" to it.username,
                )
                   },
            restore = {
                val authState = AuthenticationState()
                authState.username = it["username"] as String
                authState.token = it["token"] as String
                authState.authError = it["authError"] as String
                authState
            }
        )
    }
}

@Composable
fun rememberAuthenticationState(): AuthenticationState {
    return rememberSaveable(saver = AuthenticationState.Saver) {
        AuthenticationState()
    }
}