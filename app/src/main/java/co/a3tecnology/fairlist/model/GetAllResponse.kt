package co.a3tecnology.fairlist.model

data class GetAllResponse(
    val list: List<ItemResponse>
)

data class ItemResponse(
    val title: String,
    val desc: String,
    val date: Long,
    val type: Int
)