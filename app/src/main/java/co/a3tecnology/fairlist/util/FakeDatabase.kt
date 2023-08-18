package co.a3tecnology.fairlist.util

import android.graphics.Color
import co.a3tecnology.fairlist.model.*
import java.util.*
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashSet

//user fake
data class User(
    val name: String,
    val email: String,
    val password: String,
    val token: String
)

class FakeDatabase {

    companion object {
        private var users: HashSet<User> = hashSetOf()

        private var items: LinkedHashSet<ItemResponse> = linkedSetOf()


        init {
            users.add(User("123","123@gmail.com", "123", "abc"))
            users.add(User("321","321@gmail.com", "321", "cba"))

            items.add(
                ItemResponse(
                    "Açai no Pote",
                    "teste de desc",
                    "1",
                    Date().time,
                    Color.GREEN)
            )

            items.add(
                ItemResponse(
                    "Sorvete no Pote",
                    "teste de cont",
                    "1",
                    Date().time,
                    Color.BLUE)
            )
            items.add(
                ItemResponse(
                    "Coca-Cola",
                    "teste de cont",
                    "1",
                    Date().time,
                    Color.MAGENTA)
            )
        }

        fun login(loginRequest: LoginRequest, response: (LoginResponse?) -> Unit) {

            Thread.sleep(1000)
                val user = users.filter {
                    it.email == loginRequest.email && it.password == loginRequest.password
                }.firstOrNull()

                if (user != null) {
                    response(LoginResponse(user.token))
                } else {
                    response(null)
                }
        }

        fun register(request: RegisterRequest, response: (CreateResponse?) -> Unit ) {
            Thread.sleep(800)

            val user = User(request.name, request.email,
                request.password, UUID.randomUUID().toString())

            val added = users.add(user)
                if (added) {
                    response(CreateResponse(user.token))
                } else {
                    response(null)
                }
        }

        fun getAll(token: String?, response: (GetAllResponse?) -> Unit) {
            Thread.sleep(2000)

            val list = mutableListOf<ItemResponse>()
            items.toCollection(list)
            response(GetAllResponse(list))
        }
    }
}