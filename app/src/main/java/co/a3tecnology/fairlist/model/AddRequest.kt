package co.a3tecnology.fairlist.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class  AddRequest(

        val title: String,
        val desc: String? = null,
        @SerialName("amount") val qtd: String? = null,
        val priority: Int
)