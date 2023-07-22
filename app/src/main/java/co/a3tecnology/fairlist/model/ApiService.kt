package co.a3tecnology.fairlist.model

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("fairlist/items/")
//    fun addItem(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Call<ResponseBody>
//      fun addItem(@Header("Authorization") token: String,
//                  @Body addRequest: AddRequest) : Call<AddedResponse> -- com token

    fun addItem(@Body addRequest: AddRequest) : Call<AddedResponse> // sem token na solicitação
}