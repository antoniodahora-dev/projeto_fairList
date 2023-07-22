package co.a3tecnology.fairlist.network

import co.a3tecnology.fairlist.App
import co.a3tecnology.fairlist.model.*
import co.a3tecnology.fairlist.util.FakeDatabase
//import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

const val BASE_URL = "http://192.168.0.228:8000/"

class RemoteDataSource(private val apiService: ApiService) {

    fun login(loginRequest: LoginRequest,
              onUserLoggerIn: (String?, Throwable?) -> Unit) {

        Thread{
            val conn = URL("${BASE_URL}fairlist/auth/token/login/").openConnection() as HttpURLConnection

            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Accept", "application/json")
            conn.readTimeout = 10000
            conn.connectTimeout = 10000
            conn.doInput = true
            conn.doOutput = true

            val json = JSONObject()
            json.put("email", loginRequest.email)
            json.put("password", loginRequest.password)

            val bytes = json.toString().toByteArray()

            try {
                conn.outputStream.use { os ->
                    os.write(bytes)
                }
                val status: Int = conn.responseCode
                val reader = if (status < 400) {
                    InputStreamReader(conn.inputStream)
                } else {
                    InputStreamReader(conn.errorStream)
                }
                reader.use { input ->
                    val response = StringBuilder()
                    val bf = BufferedReader(input)

                    bf.useLines { lines ->
                        lines.forEach {
                            response.append(it.trim())
                        }
                    }

                    try {
                        val jsonObject = JSONObject(response.toString())
                        val token = jsonObject.getString("auth_token")
                        App.saveToken(token)
                        onUserLoggerIn(token, null)
                    } catch (e: Throwable) {
                      onUserLoggerIn(null, java.lang.IllegalArgumentException(response.toString()))
                    }
                }

            } catch (error: Throwable) {
                error.printStackTrace()
                onUserLoggerIn(null, error)
            }
            conn.disconnect()
            //solicitacao fake
//            FakeDatabase.login(loginRequest) { res ->
//                if (res != null) {
//                    App.saveToken(res.token)
//                    onUserLoggerIn(res.token, null)
//                } else {
//                    onUserLoggerIn(null, null)
//                }
//            }

            //simulacao de deley do app -api
//            Thread.sleep(1000)
//            onUserLoggerIn("cvxvxcvxc", null)
        }.start()
    }

    fun register(registerRequest: RegisterRequest,
              onUserCreated: (String?, Throwable?) -> Unit) {

        Thread{
            //conexão com servidor local
            val conn = URL("${BASE_URL}fairlist/Lauth/users/").openConnection() as HttpURLConnection

            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Accept", "application/json")
            conn.readTimeout = 10000
            conn.connectTimeout = 10000
            conn.doInput = true
            conn.doOutput = true

            val json = JSONObject()
            json.put("username", registerRequest.name)
            json.put("email", registerRequest.email)
            json.put("password", registerRequest.password)

            val bytes = json.toString().toByteArray()

            try {
                conn.outputStream.use { os ->
                    os.write(bytes)
                }
                val status: Int = conn.responseCode
                val reader = if (status < 400) {
                    InputStreamReader(conn.inputStream)
                } else {
                    InputStreamReader(conn.errorStream)
                }
                reader.use { input ->
                    val response = StringBuilder()
                    val bf = BufferedReader(input)

                    bf.useLines { lines ->
                        lines.forEach {
                            response.append(it.trim())
                        }
                    }

                    if (status == 201) {
                        login(LoginRequest(registerRequest.email, registerRequest.password),
                        onUserCreated)
                    } else {
                        onUserCreated(null, IllegalArgumentException(response.toString()))
                    }

                }
            } catch (error: Throwable) {
                error.printStackTrace()
                onUserCreated(null, error)
            }
            conn.disconnect()
            //solicitacao fake
//            FakeDatabase.register(registerRequest) { res ->
//                if (res != null) {
//                    App.saveToken(res.token)
//                    onUserCreated(res.token, null)
//                } else {
//                    onUserCreated(null, null)
//                }
//            }

            //simulacao de deley do app -api
//            Thread.sleep(1000)
//            onUserLoggerIn("cvxvxcvxc", null)
        }.start()
    }

    // No lugar do AddedResponse?, Throwable? -  vamos utilizar a class celada Result
    fun  addItem(addRequest: AddRequest, onResponse: (Result<AddedResponse?>) -> Unit) {
        // usando padrão retrofit
//        val body = Gson().toJson(addRequest)
//                .toRequestBody("application/json; charset=UTF-8".toMediaType())

        apiService.addItem(addRequest)
                .enqueue(object : Callback<AddedResponse> {

            override fun onResponse(call: Call<AddedResponse>,
                                    response: Response<AddedResponse>
            ) {
                if (response.isSuccessful) {
                    //Retrofit
//                    val message = response.body()?.string()
//                    val res = Gson().fromJson(message, AddedResponse::class.java)
//                    onResponse(res, null)
//                    onResponse(response.body(), null)

                    onResponse(Result.Success(response.body()))
                } else {
                   val message = response.errorBody()?.string()
//                   onResponse(null, RuntimeException(message))
                    onResponse(Result.Failure(RuntimeException(message)))
                }
            }

            override fun onFailure(call: Call<AddedResponse>, t: Throwable) {
//                onResponse(null, t)
                onResponse(Result.Failure(t))
            }
        })
//        Thread {
//            onResponse (AddedResponse("", "", 0, ""), null)

            //items fake
//            FakeDatabase.getAll(App.getToken()) { res ->
//                if (res != null) {
//                    onResponse(res.list, null)
//                } else {
//                    onResponse(null, null)
//                }
//
//            }
//        }.start()
    }

    fun  getAll(onResponse: (List<ItemResponse>?, Throwable?) -> Unit) {
        Thread {
            FakeDatabase.getAll(App.getToken()) { res ->
                if (res != null) {
                    onResponse(res.list, null)
                } else {
                    onResponse(null, null)
                }

            }
        }.start()
    }
}