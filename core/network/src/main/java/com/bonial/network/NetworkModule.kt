package com.bonial.network

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
    }

    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(get())
            }
        }
    }

    single {
        Ktorfit.Builder()
            .baseUrl("https://mobile-s3-test-assets.aws-sdlc-bonial.com/")
            .httpClient(get<HttpClient>())
            .build()
    }
}
