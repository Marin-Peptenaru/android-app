package com.example.android_app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.android_app.composables.authentication.LoginScreen
import com.example.android_app.composables.authentication.Secured
import com.example.android_app.composables.books.BookInput
import com.example.android_app.composables.books.BookList
import com.example.android_app.dummy.DummyScreen
import com.example.android_app.screens.BooksScreen
import com.example.android_app.state.rememberAuthenticationState
import com.example.android_app.state.rememberBooksState

@ExperimentalComposeUiApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    val authenticationState = rememberAuthenticationState()
    val booksState = rememberBooksState()

    NavHost(
        navController = navController,
        startDestination = "main",
        modifier = modifier
    ) {
        composable("main") {
            if (!authenticationState.isAuthenticated) {
                LoginScreen(onAuthenticate = authenticationState::login)
            } else {
                BooksScreen(
                    onEnter = {booksState.fetchBooks(authenticationState.bearerToken)},
                    bookState = booksState,
                    navController = navController )
            }
        }
        composable("add-book"){
            Secured(isAuthenticated = authenticationState::isAuthenticated) {
                BookInput(onSubmit = {
                    booksState.addBook(authenticationState.bearerToken, it)
                    navController.navigate("main")
                })
            }
        }

        composable("edit-book/{isbn}", arguments = listOf(
          navArgument("isbn"){
              type = NavType.StringType
          })){ entry ->
            val isbn = entry.arguments?.getString("isbn")
            val book = booksState.getBook(isbn)
           BookInput(book = book, onSubmit = {
               booksState.saveBook(authenticationState.bearerToken, it)
               navController.navigate("main")
           })
        }

    }
}