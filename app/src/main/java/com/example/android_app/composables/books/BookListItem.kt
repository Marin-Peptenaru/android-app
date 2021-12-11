package com.example.android_app.composables.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android_app.data.Book
import com.example.android_app.ui.theme.Shapes

@Composable
fun BookListItem(book: Book, modifier: Modifier = Modifier) {
    Card(
        contentColor = MaterialTheme.colors.onBackground,
        modifier = modifier.padding(8.dp).fillMaxSize(),
        elevation = 8.dp
    ) {
        Text("${book.title} by ${book.author}: ${book.genre}", modifier = Modifier.padding(16.dp))
    }
}