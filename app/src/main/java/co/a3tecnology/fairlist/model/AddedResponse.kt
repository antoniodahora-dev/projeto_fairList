package co.a3tecnology.fairlist.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//
//import com.google.gson.annotations.SerializedName
//import com.squareup.moshi.Json

@Serializable
data class  AddedResponse(
        val title: String,
        val desc: String? = null,
        @SerialName("amount") val qtd: String? = null,
        val priority: Int,
        @SerialName("created_date") val date: String

        //moshi
//        @field:Json(name = "title") val title: String,
//        @field:Json(name = "desc") val desc: String,
//        @field:Json(name = "priority") val priority: Int,
//        @field:Json(name = "created_date") val date: String

//          Retrofit
//        val title: String,
//        val desc: String? = null,
//        val priority: Int
        //SerializedName("created_date") val date: String

)