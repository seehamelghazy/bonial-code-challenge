package com.bonial.feature.brochures.domain.usecase

import com.bonial.feature.brochures.domain.model.Brochure
import com.bonial.feature.brochures.domain.repository.BrochureRepository

class GetBrochuresUseCase(
    private val repository: BrochureRepository
) {
    suspend operator fun invoke(filterDistanceBelowKm: Double? = null): List<Brochure> {
        val brochures = repository.getBrochures()
        return if (filterDistanceBelowKm != null) {
            brochures.filter { it.distance < filterDistanceBelowKm }
        } else {
            brochures
        }
    }
}
