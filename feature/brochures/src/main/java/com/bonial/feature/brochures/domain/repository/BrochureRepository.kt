package com.bonial.feature.brochures.domain.repository

import com.bonial.feature.brochures.domain.model.Brochure

interface BrochureRepository {
    suspend fun getBrochures(): List<Brochure>
}
