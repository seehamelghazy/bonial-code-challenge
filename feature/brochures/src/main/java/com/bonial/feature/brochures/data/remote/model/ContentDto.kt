package com.bonial.feature.brochures.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ContentDto(
    val title: String?,
    val brochureImage: String?,
    val publisher: PublisherDto?,
    val distance: Double?
)
