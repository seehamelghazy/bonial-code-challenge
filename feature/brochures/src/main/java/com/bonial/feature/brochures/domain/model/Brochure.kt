package com.bonial.feature.brochures.domain.model

data class Brochure(
    val title: String,
    val retailerName: String,
    val imageUrl: String?,
    val distance: Double,
    val isPremium: Boolean
)
