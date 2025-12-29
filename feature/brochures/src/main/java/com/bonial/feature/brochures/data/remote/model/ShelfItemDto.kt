package com.bonial.feature.brochures.data.remote.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ShelfItemDto(
    val contentType: String?,
    val content: JsonElement?
)
