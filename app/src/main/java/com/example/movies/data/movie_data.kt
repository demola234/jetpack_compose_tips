package com.example.movies.data

import com.example.movies.R

data class MovieData(
    val id: Int,
    val title: String,
    val year: String,
    val rating: Float,
    val genre: String,
    val imageResId: Int,
    val description: String
)

val sampleMovies = listOf(
    MovieData(
        id = 1,
        title = "The Shawshank Redemption",
        year = "1994",
        rating = 9.3f,
        genre = "Drama",
        imageResId = R.drawable.net,
        description = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."
    ),
    MovieData(
        id = 2,
        title = "The Godfather",
        year = "1972",
        rating = 9.2f,
        genre = "Crime, Drama",
        imageResId = R.drawable.net,
        description = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."
    ),
    MovieData(
        id = 3,
        title = "The Dark Knight",
        year = "2008",
        rating = 9.0f,
        genre = "Action, Crime, Drama",
        imageResId = R.drawable.net,
        description = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice."
    ),
    MovieData(
        id = 4,
        title = "Pulp Fiction",
        year = "1994",
        rating = 8.9f,
        genre = "Crime, Drama",
        imageResId = R.drawable.net,
        description = "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption."
    ),
    MovieData(
        id = 5,
        title = "Inception",
        year = "2010",
        rating = 8.8f,
        genre = "Action, Adventure, Sci-Fi",
        imageResId = R.drawable.net,
        description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O."
    ),
)