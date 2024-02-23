package com.delminiusdevs

import com.delminiusdevs.data.NoteDAO
import com.delminiusdevs.data.NotesDatabase
import com.delminiusdevs.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

val database: NoteDAO = NotesDatabase()

fun Application.module() {
    configureHeaders()
    configureCallLogging()
    configureContentNegotiation()
    configureAuth()
    configureRouting()
}
