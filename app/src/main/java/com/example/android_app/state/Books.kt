package com.example.android_app.state

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.android_app.data.Book
import com.example.android_app.data.BookDTO
import com.example.android_app.network.BooksApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksState constructor(
    private val initialList: MutableList<Book>? = null,
    private val book: Book? = null
) {
    private val _books = mutableStateOf<MutableList<Book>>(initialList ?: mutableListOf());
    private val _selectedBook = mutableStateOf<Book?>(book)

    val books
        get() = _books.value

    val booksCount
        get() = _books.value.size

    val selectedBook
        get() = _selectedBook.value

    val isSelected
        get() = _selectedBook.value != null

    fun fetchBooks(bearerToken: String, onSuccess: () -> Unit = {}, onFailure: () -> Unit = {}){
        BooksApi.retrofitService.getBooks(bearerToken).enqueue(
            object: Callback<List<BookDTO>> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<List<BookDTO>>, response: Response<List<BookDTO>>) {
                    _books.value = response.body()?.map { Book(it) } as MutableList<Book>
                    onSuccess()
                }

                override fun onFailure(call: Call<List<BookDTO>>, t: Throwable) {
                    t.message?.let { Log.d("DEBUG", it) }
                    onFailure()
                }
            }
        )
    }

    fun getBook(isbn: String?): Book? {
        if (isbn == null) return null
        for (book in _books.value) {
            if (book.isbn == isbn)
                return book
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addBook(bearerToken: String, book: Book, onSuccess: (Book) -> Unit = {}, onFailure: (Book) -> Unit = {}) {
        BooksApi.retrofitService.addBook(bearerToken, BookDTO(book)).enqueue(
            object: Callback<BookDTO>{
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<BookDTO>, response: Response<BookDTO>) {
                    response.body()?.let { _books.value.add(Book(it)) }
                    response.body()?.let { onSuccess(Book(it)) }
                }

                override fun onFailure(call: Call<BookDTO>, t: Throwable) {
                    t.message?.let { Log.d("DEBUG", it) }
                    onFailure(book)
                }
            }
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveBook(bearerToken: String, book: Book, onSuccess: (Book) -> Unit = {}, onFailure: (Book) -> Unit = {}) {

        BooksApi.retrofitService.updateBook(bearerToken, BookDTO(book)).enqueue(
            object: Callback<BookDTO>{
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<BookDTO>, response: Response<BookDTO>) {
                    for (i in _books.value.indices) {
                        if (_books.value[i].isbn == book.isbn) {
                            _books.value[i] = Book(response.body()!!)
                            onSuccess(Book(response.body()!!))
                            return
                        }
                    }
                    _books.value.add(book)
                }

                override fun onFailure(call: Call<BookDTO>, t: Throwable) {
                    t.message?.let { Log.d("DEBUG", it) }
                    onFailure(book)
                }
            }
        )

    }

    fun select(book: Book?) {
        this._selectedBook.value = book
    }

    fun unselect() {
        this._selectedBook.value = null
    }

    companion object {
        val Saver: Saver<BooksState, *> = mapSaver(
            save = {
                mapOf(
                    "selected" to (it._selectedBook.value ?: -1),
                    "books" to it._books.value
                )

            },
            restore = {
                val selectedBook = when (it["selected"]) {
                    is Book -> it["selected"] as Book
                    else -> null
                }
                BooksState(it["books"] as? MutableList<Book>?, selectedBook)
            }
        )
    }

}

@Composable
fun rememberBooksState(books: List<Book> = emptyList()): BooksState {
    Log.d("Debug", "Creating saveable books state.\n")
    return rememberSaveable(saver = BooksState.Saver) {
        BooksState(books.toMutableList())
    }
}