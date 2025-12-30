package com.bonial.feature.brochures.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.bonial.feature.brochures.R
import com.bonial.feature.brochures.domain.model.Brochure
import com.bonial.feature.brochures.presentation.BonialBlack
import com.bonial.feature.brochures.presentation.BonialGray
import com.bonial.feature.brochures.presentation.BonialLightGray
import com.bonial.feature.brochures.presentation.BonialRed
import com.bonial.feature.brochures.presentation.BonialWhite

@Composable
fun BrochureItem(
    brochure: Brochure,
    imageHeight: Dp
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            BrochureHeader(brochure.retailerName, brochure.isPremium)
            BrochureImage(
                imageUrl = brochure.imageUrl,
                contentDescription = brochure.title,
                height = imageHeight
            )
            BrochureFooter(brochure.distance)
        }
    }
}

@Composable
private fun BrochureHeader(retailerName: String, isPremium: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = retailerName,
            style = MaterialTheme.typography.titleLarge,
            color = BonialBlack,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        if (isPremium) {
            Spacer(modifier = Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .background(BonialRed, RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = stringResource(R.string.badge_premium),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = BonialWhite,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
private fun BrochureImage(
    imageUrl: String?,
    contentDescription: String?,
    height: Dp
) {
    var isLoaded by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isLoaded) Color.Transparent else BonialLightGray)
    ) {
        if (!isLoaded && !isError) {
            LoadingShimmerPlugin(modifier = Modifier.fillMaxSize())
        }

        val painter = rememberAsyncImagePainter(
            model = imageUrl,
            onState = { state ->
                isLoaded = state is AsyncImagePainter.State.Success
                isError = state is AsyncImagePainter.State.Error
            }
        )

        if (isLoaded) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(20.dp),
                contentScale = ContentScale.Crop,
                alpha = 0.7f
            )
        }

        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        if (!isLoaded) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isError) Icons.Default.BrokenImage else Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = BonialGray.copy(alpha = 0.2f)
                )
            }
        }
    }
}

@Composable
private fun BrochureFooter(distance: Double) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .padding(top = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (distance > 0) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(R.string.content_desc_distance),
                    modifier = Modifier.size(14.dp),
                    tint = BonialGray
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = stringResource(R.string.distance_away, distance),
                    style = MaterialTheme.typography.bodyMedium.copy(color = BonialGray)
                )
            }
        }
    }
}
