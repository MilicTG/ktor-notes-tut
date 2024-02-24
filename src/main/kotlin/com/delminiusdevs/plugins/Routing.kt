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
            call.respondText("Mafija je svuda oko nas")
        }

        userRoutes()
        noteRoutes()
    }
}
