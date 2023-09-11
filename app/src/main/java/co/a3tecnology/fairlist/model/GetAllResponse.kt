package co.a3tecnology.fairlist.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllResponse(
   @SerialName("items") val list: List<ItemResponse>
)

@Serializable
data class ItemResponse(
    val id: Long,
    val title: String,
    val desc: String? = null,
    @SerialName("amount") val qtd: String? = null,
    @SerialName("created_date") val date: String,
    @SerialName("priority") val priority: Int
)
