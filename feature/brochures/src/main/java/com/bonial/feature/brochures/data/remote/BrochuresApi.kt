package com.bonial.feature.brochures.data.remote

import com.bonial.feature.brochures.data.remote.model.ShelfResponseDto
import de.jensklingenberg.ktorfit.http.GET

interface BrochuresApi {
    @GET("shelf.json")
    suspend fun getShelf(): ShelfResponseDto
}
