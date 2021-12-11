package com.example.android_app.state

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.android_app.data.Book
import java.time.LocalDate

class BookInputState(
    private val _isbn: String,
    private val _title: String,
    private val _author: String,
    private val _pages: Int,
    private val _genre: Book.Genre,
    private val _publicationDate: LocalDate
) {
    var isbn by mutableStateOf(_isbn)
    var title by mutableStateOf(_title)
    var author by mutableStateOf(_author)
    var pages by mutableStateOf(_pages)
    var genre by mutableStateOf(_genre)
    var publicationDate by mutableStateOf(_publicationDate)

    val book
        get() = Book(isbn, title, author, pages, publicationDate, genre)

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val Saver: Saver<BookInputState, *> = mapSaver(
            save = {
                mapOf(
                    "isbn" to it.isbn,
                    "title" to it.title,
                    "author" to it.author,
                    "pages" to it.pages,
                    "publicationDate" to it.publicationDate,
                    "genre" to it.genre.name
                )
            },
            restore = {
                BookInputState(
                    _isbn = it["isbn"] as String,
                    _title = it["title"] as String,
                    _author = it["author"] as String,
                    _pages = it["pages"] as Int,
                    _publicationDate = it["publicationDate"] as LocalDate,
                    _genre = Book.Genre.valueOf(it["genre"] as String)
                )
            },
        )

    }
}

@Composable
fun rememberBookInputState(
    _isbn: String,
    _title: String,
    _author: String,
    _pages: Int,
    _genre: Book.Genre,
    _publicationDate: LocalDate
): BookInputState {
    return rememberSaveable(inputs = arrayOf(_isbn), saver = BookInputState.Saver) {
        BookInputState(_isbn, _title, _author, _pages, _genre, _publicationDate)
    }
}
