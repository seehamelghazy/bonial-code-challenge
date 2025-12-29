package com.bonial.feature.brochures.data.repository

import com.bonial.feature.brochures.data.BrochureMapper
import com.bonial.feature.brochures.data.remote.BrochuresApi
import com.bonial.feature.brochures.domain.model.Brochure
import com.bonial.feature.brochures.domain.repository.BrochureRepository

class BrochureRepositoryImpl(
    private val api: BrochuresApi,
    private val mapper: BrochureMapper = BrochureMapper()
) : BrochureRepository {

    override suspend fun getBrochures(): List<Brochure> {
        val response = api.getShelf()
        return mapper.mapToBrochures(response.embedded?.contents)
    }
}
