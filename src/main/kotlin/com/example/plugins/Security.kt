package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.response.*

fun Application.configureSecurity() {

    intercept(Plugins) {
        val keys = System.getenv("API_KEYS")?.split(",") ?: emptyList()
        val apiKey = call.request.headers["X-Api-Key"]
        if (apiKey == null || apiKey !in keys) {
            call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
            finish()
        }
    }
}
