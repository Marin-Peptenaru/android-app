package com.example.android_app.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.NavController
import com.example.android_app.composables.books.BookInput
import com.example.android_app.composables.books.BookList
import com.example.android_app.state.BooksState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BooksScreen(
    bookState: BooksState,
    navController: NavController,
    onEnter: () -> Unit,
) {

    val currentOnEnter by rememberUpdatedState(onEnter)
    LaunchedEffect(true){
        currentOnEnter()
    }

    BookList(
        books = bookState.books,
        booksCount = bookState.booksCount,
        onAddBook = { navController.navigate("add-book") },
        onBookClicked = { navController.navigate("edit-book/${it.isbn}") }
    )

}