package com.example.android_app.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class Book(
    val isbn: String,
    val title: String,
    val author: String,
    val pages: Int,
    val publicationDate: LocalDate,
    val genre: Book.Genre
) : Serializable {
    enum class Genre(private val genre: String) {
        Horror("Horror"),
        Thriller("Thriller"),
        Fantasy("Fantasy"),
        SciFi("Scifi")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(dto: BookDTO) : this(
        dto.isbn,
        dto.title,
        dto.author,
        dto.pages,
        LocalDate.parse(dto.publicationDate),
        Book.Genre.valueOf(dto.genre)
    )
}

data class BookDTO(
    val isbn: String,
    val title: String,
    val author: String,
    val pages: Int,
    val publicationDate: String,
    val genre: String
) : Serializable {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(b: Book) : this(
        b.isbn,
        b.title,
        b.author,
        b.pages,
        b.publicationDate.toString(),
        b.genre.toString()
    )
}

