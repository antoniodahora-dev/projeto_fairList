package co.a3tecnology.fairlist.model

data class RegisterRequest (
    val name: String,
    val email: String,
    val password: String
)