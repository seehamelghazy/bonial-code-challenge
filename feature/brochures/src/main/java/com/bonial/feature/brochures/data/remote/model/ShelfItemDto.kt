package com.bonial.feature.brochures.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ShelfItemDto(
    @SerialName("contentType")
    val contentType: String?,
    @SerialName("content")
    val content: JsonElement?
)
