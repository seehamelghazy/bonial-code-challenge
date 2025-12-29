package com.bonial.feature.brochures.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShelfResponseDto(
    @SerialName("_embedded") val embedded: ShelfItemEmbeddedDto?
)
