package co.a3tecnology.fairlist.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//import com.squareup.moshi.Json

@Serializable
data class  AddRequest(
//        Moshi
//        @field:Json(name = "title") val title: String,
//        @field:Json(name = "desc") val desc: String? = null,
//        @field:Json(name = "priority") val priority: Int,

        val title: String,
        val desc: String? = null,
        @SerialName("amount") val qtd: String? = null,
        val priority: Int
)