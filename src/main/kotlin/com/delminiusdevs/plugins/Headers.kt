package com.delminiusdevs.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.defaultheaders.*

fun Application.configureHeaders() = install(DefaultHeaders)
