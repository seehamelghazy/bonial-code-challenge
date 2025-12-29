package com.bonial.feature.brochures.di

import com.bonial.feature.brochures.data.BrochureMapper
import com.bonial.feature.brochures.data.remote.BrochuresApi
import com.bonial.feature.brochures.data.remote.createBrochuresApi
import com.bonial.feature.brochures.data.repository.BrochureRepositoryImpl
import com.bonial.feature.brochures.domain.repository.BrochureRepository
import com.bonial.feature.brochures.domain.usecase.GetBrochuresUseCase
import com.bonial.feature.brochures.presentation.viewmodel.BrochuresViewModel
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureBrochuresModule = module {
    single<BrochuresApi> {
        get<Ktorfit>().createBrochuresApi()
    }

    single<BrochureMapper> {
        BrochureMapper()
    }

    single<BrochureRepository> {
        BrochureRepositoryImpl(get(), get())
    }

    factory {
        GetBrochuresUseCase(get())
    }

    viewModel {
        BrochuresViewModel(get())
    }
}
