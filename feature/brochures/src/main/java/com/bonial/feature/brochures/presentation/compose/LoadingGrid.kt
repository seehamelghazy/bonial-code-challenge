package com.bonial.feature.brochures.presentation.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.bonial.feature.brochures.presentation.BonialLightGray

@Composable
fun LoadingGrid() {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val columnCount = if (isLandscape) 3 else 2

    val screenWidth = configuration.screenWidthDp.dp
    val contentWidth = screenWidth - 32.dp
    val spacing = 16.dp * (columnCount - 1)
    val itemWidth = (contentWidth - spacing) / columnCount
    val imageHeight = itemWidth * 1.5f

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(6) { index ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(24.dp)
                            .background(BonialLightGray, RoundedCornerShape(4.dp))
                    ) {
                        LoadingShimmerPlugin(modifier = Modifier.fillMaxSize())
                    }

                    if (index % 3 == 0) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Box(
                            modifier = Modifier
                                .size(width = 60.dp, height = 20.dp)
                                .background(BonialLightGray, RoundedCornerShape(4.dp))
                        ) {
                            LoadingShimmerPlugin(modifier = Modifier.fillMaxSize())
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight)
                        .background(BonialLightGray, RoundedCornerShape(12.dp))
                ) {
                    LoadingShimmerPlugin(modifier = Modifier.fillMaxSize())
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .padding(top = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .background(BonialLightGray, CircleShape)
                        ) {
                            LoadingShimmerPlugin(modifier = Modifier.fillMaxSize())
                        }
                        Spacer(modifier = Modifier.size(4.dp))
                        Box(
                            modifier = Modifier
                                .size(width = 80.dp, height = 16.dp)
                                .background(BonialLightGray, RoundedCornerShape(4.dp))
                        ) {
                            LoadingShimmerPlugin(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }
}
