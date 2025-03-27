package com.example.movies.core

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.MovieDetailScreen
import com.example.movies.MovieUserScreen
import com.example.movies.data.sampleMovies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "movies") {
        composable("movies") {
            MovieUserScreen(navController = navController)
        }
        composable("movie_details/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 1
            val movie = sampleMovies.find { it.id == movieId } ?: sampleMovies[0]
            MovieDetailScreen(movie = movie, navController = navController)
        }
    }
}