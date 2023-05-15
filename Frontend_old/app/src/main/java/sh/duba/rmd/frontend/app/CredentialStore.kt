package sh.duba.rmd.frontend.app

import arrow.core.Either
import arrow.core.left
import arrow.core.right

fun generateLoginRequestPayload(username: String, password: String): String {
    return "balls"
}

data class LoginResponseUserPayload(
    val isAdmin: Boolean,
    val netId: String
)

data class LoginResponsePayload(
    val token: String,
    val user: LoginResponseUserPayload
)

fun sendLoginRequestToServer(payload: String): Either<LoginResponsePayload, String> {
    return LoginResponsePayload("balls", LoginResponseUserPayload(false, "balls")).left();
}