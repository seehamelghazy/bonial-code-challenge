package com.bonial.feature.brochures.data

import com.bonial.feature.brochures.data.remote.model.ContentDto
import com.bonial.feature.brochures.data.remote.model.ShelfItemDto
import com.bonial.feature.brochures.domain.constants.BrochureContentType
import com.bonial.feature.brochures.domain.model.Brochure
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class BrochureMapper(
    private val json: Json = Json {
        ignoreUnknownKeys = true
    }
) {

    /**
     * Maps a list of ShelfItemDto to a list of Brochure domain models.
     * Filters for brochure and brochurePremium content types only.
     *
     * @param items List of shelf items from the API
     * @return List of successfully mapped Brochure objects
     */
    fun mapToBrochures(items: List<ShelfItemDto>?): List<Brochure> {
        return items?.filter { isBrochureContentType(it.contentType) }
            ?.mapNotNull { mapToBrochure(it) } ?: emptyList()
    }

    private fun mapToBrochure(item: ShelfItemDto): Brochure? {
        return item.content?.let { jsonElement ->
            try {
                val content = json.decodeFromJsonElement<ContentDto>(jsonElement)
                Brochure(
                    title = content.title ?: "",
                    retailerName = content.publisher?.name ?: "",
                    imageUrl = content.brochureImage,
                    distance = content.distance ?: 0.0,
                    isPremium = item.contentType == BrochureContentType.BROCHURE_PREMIUM
                )
            } catch (_: Exception) {
                null
            }
        }
    }

    private fun isBrochureContentType(contentType: String?): Boolean {
        return contentType == BrochureContentType.BROCHURE || contentType == BrochureContentType.BROCHURE_PREMIUM
    }
}
