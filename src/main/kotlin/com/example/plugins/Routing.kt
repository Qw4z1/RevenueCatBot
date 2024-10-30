package com.example.plugins

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory

@Serializable
data class RevenueCatEvent(
    val event: Event
)

@Suppress("PropertyName")
@Serializable
data class Event(
    val country_code: String,
    val product_id: String,
    val price: Double,
)

fun Application.configureRouting() {

    val logger = LoggerFactory.getLogger("🚀 Hook")

    fun createBody(body: RevenueCatEvent): String {
        return "New ${body.event.product_id} in ${body.event.country_code} for $${body.event.price}"
    }

    routing {
        get("/healthz") {
            call.respondText("Happy!")
        }

        post("/hook") {

            val body = call.receive<RevenueCatEvent>()

            val text = createBody(body)

            httpClient.post("https://api.telegram.org/bot${System.getenv("TELEGRAM_BOT_TOKEN")}/sendMessage") {
                contentType(ContentType.Application.Json)
                setBody(
                    """
                    {
                        "chat_id": "${System.getenv("TELEGRAM_CHAT_ID")}",
                        "text": "$text"
                    }
                    """.trimIndent()
                )
            }

            call.respond(HttpStatusCode.OK)
        }
    }
}

