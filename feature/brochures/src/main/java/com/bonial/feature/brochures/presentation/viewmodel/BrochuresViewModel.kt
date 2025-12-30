package com.bonial.feature.brochures.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonial.feature.brochures.domain.model.Brochure
import com.bonial.feature.brochures.domain.usecase.GetBrochuresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BrochuresState(
    val brochures: List<Brochure> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFilterActive: Boolean = false
)

class BrochuresViewModel(
    private val getBrochuresUseCase: GetBrochuresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BrochuresState())
    val state: StateFlow<BrochuresState> = _state.asStateFlow()

    init {
        loadBrochures()
    }

    fun loadBrochures() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val filterDistance = if (_state.value.isFilterActive) 5.0 else null
                val result = getBrochuresUseCase(filterDistance)
                _state.update { it.copy(brochures = result, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun toggleFilter() {
        _state.update { it.copy(isFilterActive = !it.isFilterActive) }
        loadBrochures()
    }
}

