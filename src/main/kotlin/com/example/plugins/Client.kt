package com.example.plugins

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000 // Example: Set request timeout to 30 seconds
            connectTimeoutMillis = 15_000 // Example: Set connection timeout to 15 seconds
            socketTimeoutMillis = 15_000 // Example: Set socket timeout to 15 seconds
        }
        defaultRequest {
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }
    }
