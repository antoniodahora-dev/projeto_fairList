package co.a3tecnology.fairlist.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("app/items/")
    fun addItem(@Body addRequest: AddRequest) : Call<AddedResponse> // sem token na solicitação

    @POST("app/auth/users/")
    fun createUser(@Body registerRequest: RegisterRequest): Call<ResponseBody>

    @POST("app/auth/token/login/")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("app/items/")
    fun getItems(@Query ("page_number") pageNumber: Int = 1,
                @Query ("page_size") pageSize: Int = 30, ): Call<GetAllResponse>

    @DELETE("app/items/{id}")
    fun deleteItem(@Path("id") id: Long) : Call<ResponseBody>

    @PUT("app/items/{id}")
    fun updateItem(@Path("id") id: Long) : Call<AddedResponse>

}