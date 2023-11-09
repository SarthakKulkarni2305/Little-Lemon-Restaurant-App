package com.edwards2kx.littlelemon.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.edwards2kx.littlelemon.R
import com.edwards2kx.littlelemon.data.AppDatabase
import com.edwards2kx.littlelemon.data.MenuItemRoom
import com.edwards2kx.littlelemon.ui.theme.LittleLemonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController, database: AppDatabase) {
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(initial = emptyList())
    Scaffold(topBar = {
        TopAppBar(title = {
            Image(
                painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(160.dp)
            )
        }, actions = {
            Image(
                painterResource(id = R.drawable.profile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .padding(16.dp)
                    .size(64.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(ProfileDestination.route)
                    }
            )
        }
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            HeroSectionHeader()
            BodySection(databaseMenuItems)
        }
    }
}

@Composable
private fun HeroSectionHeader() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth(1.5f)
            .background(LittleLemonColor.green)
            .padding(18.dp)
    ) {
        Text(
            stringResource(id = R.string.restaurant_name),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = LittleLemonColor.yellow
        )
        Row(
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Column {
                Text(
                    stringResource(id = R.string.restaurant_city),
                    fontSize = 24.sp,
                    color = LittleLemonColor.cloud
                )
                Text(
                    stringResource(id = R.string.restaurant_desc),
                    style = MaterialTheme.typography.bodyLarge,
                    color = LittleLemonColor.cloud,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(0.6f)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                modifier = Modifier
                    .padding()
                    .fillMaxWidth(0.9F)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MenuItems(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        items(items = items, itemContent = { menuItem ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column {
                    Text(text = menuItem.title, style = MaterialTheme.typography.headlineSmall)
                    Text(
                        text = menuItem.desc,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .padding(top = 5.dp)
                            .padding(bottom = 5.dp)
                    )
                    Text(
                        text = "$%.2f".format(menuItem.price),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                GlideImage(
                    model = menuItem.image,
                    contentDescription = "Menu Item Image",
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                )
            }
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodySection(menuItemsLocal: List<MenuItemRoom>) {
    var menuItems = menuItemsLocal
    var selectedCategory by remember { mutableStateOf("") }

    Column(modifier = Modifier.background(LittleLemonColor.cloud)) {
        var searchPhrase by remember { mutableStateOf("") }

        OutlinedTextField(
            label = { Text(text = "Enter search phrase") },
            value = searchPhrase,
            onValueChange = { searchPhrase = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp)
                .background(LittleLemonColor.cloud),
            leadingIcon = {
                Icon(
                    Icons.Default.Search, contentDescription = "Search"
                )
            },
        )
        if (searchPhrase.isNotEmpty()) {
            menuItems = menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
        }
    }


    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(LittleLemonColor.cloud)
    ) {
        Text(
            text = "ORDER FOR DELIVERY!",
            modifier = Modifier.padding(top = 15.dp),
        )
        val scrollState = rememberScrollState()

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
                .horizontalScroll(scrollState)
        ) {
            Button(
                onClick = {
                    selectedCategory = "starters"
                }, modifier = Modifier.height(40.dp)
            ) {
                Text(text = "Starters", style = MaterialTheme.typography.bodyMedium)
            }

            Button(
                onClick = {
                    selectedCategory = "mains"
                }, modifier = Modifier.height(40.dp)
            ) {
                Text(text = "Mains", style = MaterialTheme.typography.bodyMedium)
            }

            Button(
                onClick = {
                    selectedCategory = "desserts"
                }, modifier = Modifier.height(40.dp)
            ) {
                Text(text = "Desserts", style = MaterialTheme.typography.bodyMedium)
            }

            Button(
                onClick = {
                    selectedCategory = "drinks"
                }, modifier = Modifier.height(40.dp)
            ) {
                Text(text = "Drinks", style = MaterialTheme.typography.bodyMedium)
            }
        }
        if (selectedCategory.isNotEmpty()) {
            menuItems = menuItems.filter { it.category.contains(selectedCategory) }
        }
        MenuItems(menuItems)
    }
}


@Composable
@Preview
fun HeroPreview() {
    HeroSectionHeader()
}
//@Composable
//@Preview(showBackground = true)
//fun HomePreview() {
//    val controller = rememberNavController()
//    Home(controller)
//}