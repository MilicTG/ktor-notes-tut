package com.delminiusdevs.plugins

import com.delminiusdevs.routing.noteRoutes
import com.delminiusdevs.routing.userRoutes
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Bošnjak mafija je svuda oko tebe!!!")
        }

        userRoutes()
        noteRoutes()
    }
}
