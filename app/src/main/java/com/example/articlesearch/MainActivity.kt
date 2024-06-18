package com.example.articlesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.articlesearch.domain.model.Article
import com.example.articlesearch.presentation.ArticleScreen
import com.example.articlesearch.presentation.ArticleViewModel
import com.example.articlesearch.presentation.DetailScreen
import com.example.articlesearch.ui.theme.ArticleSearchTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArticleSearchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    val viewModel = koinViewModel<ArticleViewModel>()
                    val articles = viewModel.articles.collectAsLazyPagingItems()
                    val searchText = viewModel.searchText.collectAsState().value
                    MainLayout(
                        modifier = Modifier.padding(paddingValues),
                        articles = articles,
                        searchText = searchText,
                        onValueChange = viewModel::search
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MainLayout(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    searchText: String,
    onValueChange: (String) -> Unit,
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            ArticleScreen(
                articles = articles,
                searchText = searchText,
                onValueChange = onValueChange,
                itemClick = {
                    navigator.navigateTo(
                        pane = ListDetailPaneScaffoldRole.Detail,
                        content = it
                    )
                }
            )
        },
        detailPane = {
            (navigator.currentDestination?.content as? Article?)?.let { article ->
                AnimatedPane {
                    DetailScreen(
                        modifier = modifier,
                        article = article
                    )
                }
            }
        }
    )
}