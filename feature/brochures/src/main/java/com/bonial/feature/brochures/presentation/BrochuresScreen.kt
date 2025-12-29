package com.bonial.feature.brochures.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bonial.feature.brochures.R
import com.bonial.feature.brochures.presentation.compose.BrochureGrid
import com.bonial.feature.brochures.presentation.compose.EmptyState
import com.bonial.feature.brochures.presentation.compose.LoadingGrid
import com.bonial.feature.brochures.presentation.viewmodel.BrochuresViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrochuresScreen(
    viewModel: BrochuresViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.feature_brochure_title),
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = BonialRed,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BonialWhite,
                        titleContentColor = BonialBlack
                    ),
                    actions = {
                        FilterChip(
                            selected = state.isFilterActive,
                            onClick = { viewModel.toggleFilter() },
                            label = { Text(stringResource(R.string.filter_nearby)) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.FilterList,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = BonialRed
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BonialRed.copy(alpha = 0.1f),
                                selectedLabelColor = BonialRed,
                                selectedLeadingIconColor = BonialRed
                            ),
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(BonialWhite)
            ) {
                if (state.isLoading) {
                    LoadingGrid()
                } else if (state.error != null) {
                    EmptyState(
                        icon = Icons.Default.BrokenImage,
                        title = stringResource(R.string.error_no_connection),
                        description = stringResource(R.string.error_no_connection_hint),
                        onRetry = { viewModel.loadBrochures() }
                    )
                } else {
                    BrochureGrid(
                        brochures = state.brochures
                    )
                }
            }
        }
    }
}

