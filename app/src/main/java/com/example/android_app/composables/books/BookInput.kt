package com.example.android_app.composables.books

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.android_app.data.Book
import com.example.android_app.state.rememberBookInputState
import com.example.android_app.utils.copy
import java.time.LocalDate
import com.example.android_app.composables.base.DateInputField
import com.example.android_app.composables.base.DropdownChoice

@ExperimentalComposeUiApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookInput(book: Book? = null, onSubmit: (Book) -> Unit = {}) {
    val bookInputState = rememberBookInputState(
        _isbn = book?.isbn ?: "",
        _title = book?.title ?: "",
        _author = book?.author ?: "",
        _pages = book?.pages ?: 0,
        _genre = book?.genre ?: Book.Genre.Thriller,
        _publicationDate = book?.publicationDate ?: LocalDate.now()
    )

    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (book == null) {
            OutlinedTextField(
                value = bookInputState.isbn,
                onValueChange = { bookInputState.isbn = it },
                label = { Text("ISBN") })
            Spacer(modifier = Modifier.size(8.dp))
        } else {
            Text("ISBN: ${book.isbn}")
        }


        OutlinedTextField(
            value = bookInputState.title,
            onValueChange = { bookInputState.title = it },
            label = { Text("Title") })
        Spacer(modifier = Modifier.size(8.dp))

        OutlinedTextField(
            value = bookInputState.author,
            onValueChange = { bookInputState.author = it },
            label = { Text("Author") })
        Spacer(modifier = Modifier.size(8.dp))

        OutlinedTextField(
            value = bookInputState.pages.toString(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()}),
            onValueChange = { bookInputState.pages = if (it.isNotBlank()) it.toInt() else 0 },
            label = { Text("Pages") })
        Spacer(modifier = Modifier.size(8.dp))

        Text("Publication Date")
        DateInputField(date = bookInputState.publicationDate, onValueChanged = {
            bookInputState.publicationDate = it
        })
        Spacer(modifier = Modifier.size(8.dp))

        DropdownChoice(
            value = bookInputState.genre,
            options = Book.Genre.values().asList(),
            onChoiceChange = { bookInputState.genre = it },
            description = "Genre"
        )
        Spacer(modifier = Modifier.size(8.dp))


        Button(onClick = { onSubmit(bookInputState.book) }) {
            Text("Submit")
        }
    }
}

