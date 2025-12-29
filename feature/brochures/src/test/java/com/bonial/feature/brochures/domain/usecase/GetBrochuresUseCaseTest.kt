package com.bonial.feature.brochures.domain.usecase

import com.bonial.feature.brochures.domain.model.Brochure
import com.bonial.feature.brochures.domain.repository.BrochureRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetBrochuresUseCaseTest {

    private val brochure1km = Brochure("Title 1", "Retailer 1", "url1", 1.0, false)
    private val brochure4km = Brochure("Title 2", "Retailer 2", "url2", 4.0, false)
    private val brochure6km = Brochure("Title 3", "Retailer 3", "url3", 6.0, true)
    private val fakeBrochures = listOf(brochure1km, brochure4km, brochure6km)

    private lateinit var repository: BrochureRepository

    private lateinit var useCase: GetBrochuresUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetBrochuresUseCase(repository)
        coEvery { repository.getBrochures() } returns fakeBrochures
    }

    @Test
    fun `invoke returns all brochures when filter is null`() = runTest {
        val result = useCase(null)
        assertEquals(fakeBrochures, result)
    }

    @Test
    fun `invoke returns filtered brochures when filter is provided`() = runTest {
        val result = useCase(5.0)
        assertEquals(2, result.size)
        assertTrue(result.containsAll(listOf(brochure1km, brochure4km)))
    }

    @Test
    fun `invoke returns brochures at exact boundary value`() = runTest {
        val result = useCase(4.0)
        assertEquals(1, result.size)
        assertEquals(brochure1km, result[0])
    }

    @Test
    fun `invoke returns empty list when all brochures are filtered out`() = runTest {
        val result = useCase(0.5)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke handles empty brochures list`() = runTest {
        coEvery { repository.getBrochures() } returns emptyList()
        val emptyUseCase = GetBrochuresUseCase(repository)

        val result = emptyUseCase(5.0)
        assertTrue(result.isEmpty())
    }
}
