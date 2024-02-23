package com.delminiusdevs.routing

import com.delminiusdevs.data.model.User
import com.delminiusdevs.data.requests.UserLogInRequest
import com.delminiusdevs.data.requests.UserRegisterRequest
import com.delminiusdevs.database
import com.delminiusdevs.util.getSaltWithHash
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes() {

    post("/login") {
        val userLogInRequest = try {
            call.receive<UserLogInRequest>()

        } catch (e: ContentTransformationException) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (database.checkPasswordForEmail(email = userLogInRequest.email, password = userLogInRequest.password)) {
            call.respond(HttpStatusCode.OK)
            return@post
        }

        call.respond(HttpStatusCode.Unauthorized)
    }

    post("/register") {
        val userRegisterRequest = try {
            call.receive<UserRegisterRequest>()

        } catch (e: ContentTransformationException) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (database.checkIfEmailExist(userRegisterRequest.email)) {
            call.respond(HttpStatusCode.Conflict)
        }

        val emailPattern = ("^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$").toRegex()

        if (!emailPattern.matches(userRegisterRequest.email)) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val hashPassword = getSaltWithHash(userRegisterRequest.password)

        if (database.registerUser(
                User(
                    email = userRegisterRequest.email,
                    userName = userRegisterRequest.username,
                    password = hashPassword
                )
            )
        ) {
            call.respond(HttpStatusCode.Created)
        } else {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}