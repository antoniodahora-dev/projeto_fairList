package co.a3tecnology.fairlist.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest (
    @SerialName("username")  val name: String,
    val email: String,
    val password: String
)