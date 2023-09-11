package co.a3tecnology.fairlist.network

import co.a3tecnology.fairlist.App
import co.a3tecnology.fairlist.model.*
import co.a3tecnology.fairlist.util.getError
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val BASE_URL = "https://free-fairlist.uc.r.appspot.com/"

class RemoteDataSource(private val apiService: ApiService) {

    fun login(loginRequest: LoginRequest, onResponse: (Result<LoginResponse?>) -> Unit) {

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        onResponse(Result.Success(response.body()))
                        response.body()?.token?.let { App.saveToken(it) }
                    } else {
                        var message = response.errorBody()?.string()

                        if(message != null && response.code() == 400) {
                            message = getError(message)
                        }
                        onResponse(Result.Failure(RuntimeException(message)))
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    onResponse(Result.Failure(t))
                }
            })
    }

    fun register(registerRequest: RegisterRequest, onResponse: (Result<ResponseBody?>) -> Unit) {

        apiService.createUser(registerRequest)
            .enqueue(object : Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>,
                                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful && response.code() == 201) {

                        onResponse(Result.Success(response.body()))
                    } else {
                        val message = response.errorBody()?.string()
                        onResponse(Result.Failure(RuntimeException(message)))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                onResponse(null, t)
                    onResponse(Result.Failure(t))
                }
            })
    }

    fun  addItem(addRequest: AddRequest, onResponse: (Result<AddedResponse?>) -> Unit) {

        apiService.addItem(addRequest).enqueue(object : Callback<AddedResponse> {

            override fun onResponse(call: Call<AddedResponse>, response: Response<AddedResponse>) {
                if (response.isSuccessful) {
                     onResponse(Result.Success(response.body()))
                } else {
                   val message = response.errorBody()?.string()
                    onResponse(Result.Failure(RuntimeException(message)))
                }
            }

            override fun onFailure(call: Call<AddedResponse>, t: Throwable) {
               onResponse(Result.Failure(t))
            }
        })
    }

    fun  getAll(onResponse: (Result<GetAllResponse?>) -> Unit) {

        apiService.getItems().enqueue(object : Callback<GetAllResponse> {

            override fun onResponse(call: Call<GetAllResponse>, response: Response<GetAllResponse>) {
                if (response.isSuccessful) {
                    onResponse(Result.Success(response.body()))
                } else {
                    val message = response.errorBody()?.string()
                    onResponse(Result.Failure(RuntimeException(message)))
                }
            }

            override fun onFailure(call: Call<GetAllResponse>, t: Throwable) {
                onResponse(Result.Failure(t))
            }
        })

    }

    fun delete(id: Long, onResponse: (Result<ResponseBody?>) -> Unit) {

        apiService.deleteItem(id).enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {

                        onResponse(Result.Success(response.body()))
                    } else {
                        val message = response.errorBody()?.string()
                        onResponse(Result.Failure(RuntimeException(message)))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    onResponse(Result.Failure(t))
                }
            }
        )
    }

}