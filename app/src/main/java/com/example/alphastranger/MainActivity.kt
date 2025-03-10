package com.example.alphastranger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val darkColors  = lightColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black
)

val lightColors = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    background = Color.Black,
    onBackground = Color.White
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            MaterialTheme(
                colorScheme = if (isDarkMode) darkColors else lightColors
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Stranger Book") },
                            actions = {
                                IconButton(onClick = { isDarkMode = !isDarkMode }) {
                                    Icon(
                                        painter = painterResource(id = if (isDarkMode) R.drawable.brightness else R.drawable.night_mode),
                                        contentDescription = "Toggle Dark Mode"
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    StrangerBookApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Profile(val username: String, val profilePic: Int)
data class Post(val username: String, val profilePic: Int, val postImage: Int, val caption: String, var likes: Int = 0)

@Composable
fun StrangerBookApp(modifier: Modifier = Modifier) {
    val profiles = listOf(
        Profile("Eleven", R.drawable.eleven),
        Profile("Eddie", R.drawable.eddie),
        Profile("Lucas", R.drawable.lucas)
    )

    val posts = listOf(
        Post("Eleven", R.drawable.eleven, R.drawable.snow, "Un beau jour à Hawkins sur La Glace ❄️ #ICE mood !"),
        Post("Eddie", R.drawable.eddie, R.drawable.father, "Rock and roll forever! The best father one line 🤘"),
        Post("Lucas", R.drawable.lucas, R.drawable.haircut, "Basket avec l'équipe ! Les cheveux de rêves 🏀✂️"),

        Post("Eleven", R.drawable.eleven, R.drawable.grass, "Se perdre dans l'herbe, respirer la liberté 🌿✨"),
        Post("Eddie", R.drawable.eddie, R.drawable.crush, "Ce regard... Est-ce que j'ai un crush ? 😳❤️"),
        Post("Lucas", R.drawable.lucas, R.drawable.fireworks, "Feux d'artifice et souvenirs inoubliables 🎆🔥"),

        Post("Eleven", R.drawable.eleven, R.drawable.marine, "La mer, le vent et moi... Moment parfait 🌊💙"),
        Post("Eddie", R.drawable.eddie, R.drawable.roller, "Sur les roulettes, et toujours au top 🛼🔥"),
        Post("Lucas", R.drawable.lucas, R.drawable.vecna, "Les ténèbres approchent... mais je suis prêt 💀⚡")
    )

    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            ProfileRow(profiles)
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(posts) { post ->
            PostItem(post)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun ProfileRow(profiles: List<Profile>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        profiles.forEach { profile ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = profile.profilePic),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Text(text = profile.username, fontSize = 12.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun PostItem(post: Post) {
    var likes by remember { mutableStateOf(post.likes) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = post.profilePic),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = post.username,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            Image(
                painter = painterResource(id = post.postImage),
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Text(
                text = post.caption,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { likes++ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.like),
                        contentDescription = "Like"
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("$likes")
                }
                Button(onClick = {  }) {
                    Icon(
                        painter = painterResource(id = R.drawable.comment),
                        contentDescription = "Comment"
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("")
                }
            }
        }
    }
}
