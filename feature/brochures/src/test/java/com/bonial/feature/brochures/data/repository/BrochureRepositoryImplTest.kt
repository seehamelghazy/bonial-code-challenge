package com.bonial.feature.brochures.data.repository

import com.bonial.feature.brochures.data.BrochureMapper
import com.bonial.feature.brochures.data.remote.BrochuresApi
import com.bonial.feature.brochures.data.remote.model.ContentDto
import com.bonial.feature.brochures.data.remote.model.PublisherDto
import com.bonial.feature.brochures.data.remote.model.ShelfItemDto
import com.bonial.feature.brochures.data.remote.model.ShelfItemEmbeddedDto
import com.bonial.feature.brochures.data.remote.model.ShelfResponseDto
import com.bonial.feature.brochures.domain.constants.BrochureContentType
import com.bonial.feature.brochures.domain.model.Brochure
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BrochureRepositoryImplTest {

    private lateinit var api: BrochuresApi
    private lateinit var mapper: BrochureMapper
    private lateinit var repository: BrochureRepositoryImpl
    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    @Before
    fun setup() {
        api = mockk()
        mapper = mockk()
        repository = BrochureRepositoryImpl(api, mapper)
    }

    @Test
    fun `getBrochures returns mapped brochures on successful API call`() = runTest {
        // Given
        val contentDto = ContentDto(
            title = "Test Brochure",
            brochureImage = "https://example.com/image.jpg",
            publisher = PublisherDto(name = "Test Store"),
            distance = 2.5
        )
        val shelfItems = listOf(
            ShelfItemDto(
                contentType = BrochureContentType.BROCHURE,
                content = json.encodeToJsonElement(contentDto)
            )
        )
        val response = ShelfResponseDto(
            embedded = ShelfItemEmbeddedDto(contents = shelfItems)
        )
        val expectedBrochures = listOf(
            Brochure("Test Brochure", "Test Store", "https://example.com/image.jpg", 2.5, false)
        )

        coEvery { api.getShelf() } returns response
        every { mapper.mapToBrochures(shelfItems) } returns expectedBrochures

        // When
        val result = repository.getBrochures()

        // Then
        assertEquals(expectedBrochures, result)
        coVerify(exactly = 1) { api.getShelf() }
        verify(exactly = 1) { mapper.mapToBrochures(shelfItems) }
    }

    @Test
    fun `getBrochures returns empty list when contents is empty`() = runTest {
        // Given
        val response = ShelfResponseDto(
            embedded = ShelfItemEmbeddedDto(contents = emptyList())
        )

        coEvery { api.getShelf() } returns response
        every { mapper.mapToBrochures(emptyList()) } returns emptyList()

        // When
        val result = repository.getBrochures()

        // Then
        assertTrue(result.isEmpty())
        verify(exactly = 1) { mapper.mapToBrochures(emptyList()) }
    }

    @Test
    fun `getBrochures handles multiple brochures correctly`() = runTest {
        // Given
        val shelfItems = listOf(
            ShelfItemDto(
                contentType = BrochureContentType.BROCHURE,
                content = json.encodeToJsonElement(
                    ContentDto("Brochure 1", "url1", PublisherDto("Store 1"), 1.0)
                )
            ),
            ShelfItemDto(
                contentType = BrochureContentType.BROCHURE_PREMIUM,
                content = json.encodeToJsonElement(
                    ContentDto("Brochure 2", "url2", PublisherDto("Store 2"), 2.0)
                )
            )
        )
        val response = ShelfResponseDto(
            embedded = ShelfItemEmbeddedDto(contents = shelfItems)
        )
        val expectedBrochures = listOf(
            Brochure("Brochure 1", "Store 1", "url1", 1.0, false),
            Brochure("Brochure 2", "Store 2", "url2", 2.0, true)
        )

        coEvery { api.getShelf() } returns response
        every { mapper.mapToBrochures(shelfItems) } returns expectedBrochures

        // When
        val result = repository.getBrochures()

        // Then
        assertEquals(2, result.size)
        assertEquals(expectedBrochures, result)
    }

    @Test(expected = Exception::class)
    fun `getBrochures propagates API exceptions`() = runTest {
        // Given
        coEvery { api.getShelf() } throws Exception("Network error")

        // When
        repository.getBrochures()

        // Then - exception is thrown
    }
}
