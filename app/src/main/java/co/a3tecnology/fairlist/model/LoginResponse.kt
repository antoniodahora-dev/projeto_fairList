package co.a3tecnology.fairlist.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
   @SerialName("auth_token") val token: String
)