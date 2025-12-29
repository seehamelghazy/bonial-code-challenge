package com.bonial.feature.brochures.presentation.viewmodel

import com.bonial.feature.brochures.domain.model.Brochure
import com.bonial.feature.brochures.domain.repository.BrochureRepository
import com.bonial.feature.brochures.domain.usecase.GetBrochuresUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BrochuresViewModelTest {
    private val fakeBrochures = listOf(
        Brochure("Title 1", "Retailer 1", "url1", 2.0, false),
        Brochure("Title 2", "Retailer 2", "url2", 6.0, false)
    )

    private lateinit var repository: BrochureRepository
    private lateinit var getBrochuresUseCase: GetBrochuresUseCase
    private lateinit var viewModel: BrochuresViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = mockk()
        getBrochuresUseCase = GetBrochuresUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state loads brochures successfully`() = runTest {
        // Given
        coEvery { repository.getBrochures() } returns fakeBrochures

        // When
        viewModel = BrochuresViewModel(GetBrochuresUseCase(repository))
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(fakeBrochures, state.brochures)
        assertNull(state.error)
        assertFalse(state.isFilterActive)
        coVerify(exactly = 1) { repository.getBrochures() }
    }

    @Test
    fun `toggleFilter activates filter and reloads brochures`() = runTest {
        // Given
        coEvery { repository.getBrochures() } returns fakeBrochures

        // When
        viewModel = BrochuresViewModel(GetBrochuresUseCase(repository))
        advanceUntilIdle()

        viewModel.toggleFilter()

        // Then
        assertTrue(viewModel.state.value.isFilterActive)

        advanceUntilIdle()
        val state = viewModel.state.value

        assertEquals(1, state.brochures.size)
        assertEquals("Title 1", state.brochures[0].title)
    }

    @Test
    fun `loadBrochures completes successfully and clears loading state`() = runTest {
        // Given
        coEvery { repository.getBrochures() } returns fakeBrochures

        // When
        viewModel = BrochuresViewModel(GetBrochuresUseCase(repository))
        viewModel.loadBrochures()
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(fakeBrochures, state.brochures)
    }

    @Test
    fun `loadBrochures handles error state correctly`() = runTest {
        val errorMessage = "Network error"
        // Given
        coEvery { repository.getBrochures() } throws Exception(errorMessage)

        // When
        viewModel = BrochuresViewModel(GetBrochuresUseCase(repository))
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNotNull(state.error)
        assertEquals(errorMessage, state.error)
        assertTrue(state.brochures.isEmpty())
    }

    @Test
    fun `handles empty brochures list`() = runTest {
        // Given
        coEvery { repository.getBrochures() } returns emptyList()

        // When
        viewModel = BrochuresViewModel(GetBrochuresUseCase(repository))
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state.brochures.isEmpty())
        assertNull(state.error)
        assertFalse(state.isLoading)
    }

}
