package com.bonial.feature.brochures.presentation.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bonial.feature.brochures.R
import com.bonial.feature.brochures.domain.model.Brochure


@Composable
fun BrochureGrid(
    brochures: List<Brochure>
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val columnCount = if (isLandscape) 3 else 2

    val screenWidth = configuration.screenWidthDp.dp
    val contentWidth = screenWidth - 32.dp
    val spacing = 16.dp * (columnCount - 1)
    val itemWidth = (contentWidth - spacing) / columnCount
    val targetImageHeight = itemWidth * 1.5f

    if (brochures.isEmpty()) {
        EmptyState(
            icon = Icons.Default.Info,
            title = stringResource(R.string.empty_brochures),
            description = stringResource(R.string.empty_brochures_hint)
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columnCount),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(brochures, span = { item ->
                val span = if (item.isPremium) columnCount else 1
                GridItemSpan(span)
            }) { brochure ->
                BrochureItem(brochure, targetImageHeight)
            }
        }
    }
}
