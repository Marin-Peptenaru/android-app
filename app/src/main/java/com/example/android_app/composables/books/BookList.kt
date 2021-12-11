package com.example.android_app.composables.books

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.unit.dp
import com.example.android_app.data.Book
import com.example.android_app.state.rememberBooksState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private enum class ScrollDirection {
    Top,
    Bottom,
}

@Composable
fun ScrollControlls(listSize: Int, listState: LazyListState, scope: CoroutineScope) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = {
            scope.launch {
                listState.animateScrollToItem(0)
            }
        }) {
            Surface(color = MaterialTheme.colors.primary, modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = "Scroll to the top"
                )
            }

        }
        IconButton(onClick = {
            scope.launch {
                listState.animateScrollToItem(listSize - 1)
            }
        }) {
            Surface(color = MaterialTheme.colors.primary, modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Scroll to the bottom"
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookList(
    books: List<Book>,
    booksCount: Int,
    onAddBook: () -> Unit,
    onBookClicked: (Book) -> Unit
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Column {
        ScrollControlls(listSize = booksCount, listState = scrollState, scope = scope)
        Spacer(modifier = Modifier.size(8.dp))
        LazyColumn(state = scrollState, horizontalAlignment = Alignment.CenterHorizontally) {
            items(books) { book ->
                BookListItem(book, modifier = Modifier.clickable { onBookClicked(book) })
            }
        }
        Button(onClick = onAddBook, modifier = Modifier.fillMaxWidth()) {
            Text("Add Book")
        }
    }

}