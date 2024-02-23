package com.delminiusdevs.routing

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.noteRoutes() {

    route("/notes"){
        get {
            val email = call.principal<UserIdPrincipal>()!!.name
        }
    }
}