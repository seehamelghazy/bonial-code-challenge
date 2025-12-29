package com.bonial.feature.brochures.data.remote.model

import kotlinx.serialization.Serializable


@Serializable
data class ShelfItemEmbeddedDto(
    val contents: List<ShelfItemDto>?
)
