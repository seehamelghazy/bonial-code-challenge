package com.bonial.feature.brochures.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentDto(
    @SerialName("title")
    val title: String?,
    @SerialName("brochureImage")
    val brochureImage: String?,
    @SerialName("publisher")
    val publisher: PublisherDto?,
    @SerialName("distance")
    val distance: Double?,
)
