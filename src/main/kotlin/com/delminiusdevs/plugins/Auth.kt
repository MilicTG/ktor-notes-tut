package com.delminiusdevs.plugins

import com.delminiusdevs.database
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureAuthentication() {
    install(Authentication) {
        configureAuth()
    }
}

private fun AuthenticationConfig.configureAuth() {
    basic {
        realm = "Notes server"
        validate { credentials ->
            val email = credentials.name
            val password = credentials.password
            if (database.checkPasswordForEmail(email = email, password = password)) {
                UserIdPrincipal(email)
            } else {
                null
            }
        }
    }
}