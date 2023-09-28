package com.example.plugins

import com.example.authentification.JwtService
import com.example.data.model.tables.RoleModel
import com.example.data.model.tables.UserModel

import com.example.data.model.tables.repository.UserRepositoryImpl
import com.example.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking

fun Application.configureSecurity() {

    val jwtService = JwtService()
    val repository = UserRepositoryImpl()
    val userUseCase = UserUseCase(repository, jwtService)

    runBlocking {
        userUseCase.createUser(
            UserModel(
                id = 1,
                email = "test@test.com",
                login = "Login",
                password = "Password",
                firstName = "Den",
                lastName = "Borwn",
                isActive = true,
                role = RoleModel.CLIENT
            )
        )
    }

    authentication {
        jwt("jwt") {
            verifier(jwtService.getVerifier())
            realm = "Service server"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = userUseCase.findUserByEmail(email = email)
                user
            }
        }
    }
}
