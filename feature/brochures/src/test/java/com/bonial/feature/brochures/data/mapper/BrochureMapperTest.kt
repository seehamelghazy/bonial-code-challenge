package com.bonial.feature.brochures.data.mapper

import com.bonial.feature.brochures.data.BrochureMapper
import com.bonial.feature.brochures.data.remote.model.ContentDto
import com.bonial.feature.brochures.data.remote.model.PublisherDto
import com.bonial.feature.brochures.data.remote.model.ShelfItemDto
import com.bonial.feature.brochures.domain.constants.BrochureContentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BrochureMapperTest {

    private lateinit var mapper: BrochureMapper
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setup() {
        mapper = BrochureMapper(json)
    }

    @Test
    fun `mapToBrochures filters and maps multiple items correctly`() {
        // Given
        val items = listOf(
            ShelfItemDto(
                contentType = BrochureContentType.BROCHURE,
                content = json.encodeToJsonElement(
                    ContentDto("Title 1", "url1", PublisherDto("Store 1"), 1.0)
                )
            ),
            ShelfItemDto(
                contentType = BrochureContentType.BROCHURE_PREMIUM,
                content = json.encodeToJsonElement(
                    ContentDto("Title 2", "url2", PublisherDto("Store 2"), 2.0)
                )
            ),
            ShelfItemDto(
                contentType = "other",
                content = json.encodeToJsonElement(
                    ContentDto("Title 3", "url3", PublisherDto("Store 3"), 3.0)
                )
            )
        )

        // When
        val result = mapper.mapToBrochures(items)

        // Then
        assertEquals(2, result.size)
        assertEquals("Title 1", result[0].title)
        assertEquals(false, result[0].isPremium)
        assertEquals("Title 2", result[1].title)
        assertEquals(true, result[1].isPremium)
    }

    @Test
    fun `mapToBrochures skips items with null content`() {
        // Given
        val items = listOf(
            ShelfItemDto(contentType = BrochureContentType.BROCHURE, content = null),
            ShelfItemDto(
                contentType = BrochureContentType.BROCHURE,
                content = json.encodeToJsonElement(
                    ContentDto("Valid", "url", PublisherDto("Store"), 1.0)
                )
            )
        )

        // When
        val result = mapper.mapToBrochures(items)

        // Then
        assertEquals(1, result.size)
        assertEquals("Valid", result[0].title)
    }

    @Test
    fun `mapToBrochures returns empty list when input is empty`() {
        // When
        val result = mapper.mapToBrochures(emptyList())

        // Then
        assertTrue(result.isEmpty())
    }
}
