package com.example.movies

import ShimmerEffect
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

//Sample Data
data class MovieData(
    val id: Int,
    val title: String,
    val year: String,
    val rating: Float,
    val genre: String,
    val imageResId: String,
    val description: String
)

val sampleMovies = listOf(
    MovieData(
        id = 1,
        title = "The Shawshank Redemption",
        year = "1994",
        rating = 9.3f,
        genre = "Drama",
        imageResId = "https://images.squarespace-cdn.com/content/v1/5c75dfa97d0c9166551f52b1/9351f4e2-94f9-42e2-81df-003d5fe7b8e0/9964546b0ba1f6e14a6045e34b341f8ca2a3569752c5afed95b89682fcde1a68._RI_V_TTW_.jpg",
        description = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."
    ),
    MovieData(
        id = 2,
        title = "The Godfather",
        year = "1972",
        rating = 9.2f,
        genre = "Crime, Drama",
        imageResId = "https://s3-eu-west-1.amazonaws.com/wno/News-Stories/GettyImages-525589292.jpg",
        description = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."
    ),
    MovieData(
        id = 3,
        title = "The Dark Knight",
        year = "2008",
        rating = 9.0f,
        genre = "Action, Crime, Drama",
        imageResId = "https://res.cloudinary.com/jerrick/image/upload/v1743063546/67e509f989b45d001d6d469f.jpg",
        description = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice."
    ),
    MovieData(
        id = 4,
        title = "Pulp Fiction",
        year = "1994",
        rating = 8.9f,
        genre = "Crime, Drama",
        imageResId = "https://resizing.flixster.com/06qphuWGrzYARk9p124DWnaZBFM=/fit-in/705x460/v2/https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p15684_i_h10_au.jpg",
        description = "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption."
    ),
    MovieData(
        id = 5,
        title = "Inception",
        year = "2010",
        rating = 8.8f,
        genre = "Action, Adventure, Sci-Fi",
        imageResId = "https://m.media-amazon.com/images/I/912AErFSBHL._AC_UF1000,1000_QL80_.jpg",
        description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O."
    ),
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieUserScreen(navController: NavController) {
    val primaryColor = Color(0xFF1E3264)
    val accentColor = Color(0xFF536DFE)
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search") },
                            modifier = Modifier.fillMaxWidth(),

                            )
                    } else {
                        Text(
                            text = "Movies Collection",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor
                ),
                actions = {
                    if (!isSearching) {
                        IconButton(onClick = { isSearching = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            isSearching = false
                            searchQuery = ""

                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }
                }

            )
        },

        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF5F5F5))
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
                {
                    item {
                        Text(
                            text = "Popular Movies",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    items(sampleMovies.filter {
                        if (searchQuery.isEmpty()) true
                        else it.title.contains(searchQuery, ignoreCase = true)
                    }) { movie ->
                        MovieCards(movie = movie) {
                            navController.navigate("movie_details/${movie.id}")
                        }
                    }
                }
            }
        }
    )
}



@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieCards(movie: MovieData, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
            ) {

                ShimmerEffect(
                    modifier = Modifier.fillMaxSize()
                )


                GlideImage(
                    model = movie.imageResId,
                    contentDescription = "Movie poster for ${movie.title}",
                    modifier = Modifier.fillMaxSize(),
                ) {
                    it
                        .centerCrop()
//                        .placeholder(R.drawable)
                        .error(R.drawable.net)
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${movie.year} • ${movie.genre}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = movie.rating.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    fontWeight = FontWeight.Light,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
