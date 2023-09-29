package co.a3tecnology.fairlist.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllResponse(
   @SerialName("items") val list: List<ItemResponse>
)

@Serializable
data class ItemResponse(
    var id: Long,
    val user: String,
    var title: String,
    var desc: String? = null,
    @SerialName("amount") var qtd: String? = null,
    @SerialName("created_date") var date: String,
    @SerialName("priority") var priority: Int
)
