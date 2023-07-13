package co.a3tecnology.fairlist.network

import co.a3tecnology.fairlist.App
import co.a3tecnology.fairlist.model.ItemResponse
import co.a3tecnology.fairlist.model.LoginRequest
import co.a3tecnology.fairlist.model.RegisterRequest
import co.a3tecnology.fairlist.util.FakeDatabase

class RemoteDataSource {

    fun login(loginRequest: LoginRequest,
              onUserLoggerIn: (String?, Throwable?) -> Unit) {

        Thread{
            //solicitacao fake
            FakeDatabase.login(loginRequest) { res ->
                if (res != null) {
                    App.saveToken(res.token)
                    onUserLoggerIn(res.token, null)
                } else {
                    onUserLoggerIn(null, null)
                }
            }

            //simulacao de deley do app -api
//            Thread.sleep(1000)
//            onUserLoggerIn("cvxvxcvxc", null)
        }.start()
    }

    fun register(registerRequest: RegisterRequest,
              onUserCreated: (String?, Throwable?) -> Unit) {

        Thread{
            //solicitacao fake
            FakeDatabase.register(registerRequest) { res ->
                if (res != null) {
                    App.saveToken(res.token)
                    onUserCreated(res.token, null)
                } else {
                    onUserCreated(null, null)
                }
            }

            //simulacao de deley do app -api
//            Thread.sleep(1000)
//            onUserLoggerIn("cvxvxcvxc", null)
        }.start()
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