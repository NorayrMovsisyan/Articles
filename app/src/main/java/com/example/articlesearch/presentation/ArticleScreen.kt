package com.example.articlesearch.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.articlesearch.domain.model.Article

@Composable
fun ArticleScreen(
    articles: LazyPagingItems<Article>,
    searchText: String,
    onValueChange: (String) -> Unit,
    itemClick: (Article) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = articles.loadState) {
        if (articles.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (articles.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = searchText,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = "Search Articles")
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                key = articles.itemKey(),
                contentType = articles.itemContentType(),
                count = articles.itemCount
            ) {
                val article = articles[it]
                if (article != null) {
                    ArticleItem(
                        article = article,
                        modifier = Modifier.fillMaxWidth(),
                        itemClick = itemClick
                    )
                }
            }
            item {
                if (articles.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}