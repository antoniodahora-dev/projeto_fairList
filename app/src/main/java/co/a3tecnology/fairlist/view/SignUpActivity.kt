package co.a3tecnology.fairlist.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import co.a3tecnology.fairlist.App
import co.a3tecnology.fairlist.R
import co.a3tecnology.fairlist.model.LoginRequest
import co.a3tecnology.fairlist.model.RegisterRequest
import co.a3tecnology.fairlist.model.Result
import co.a3tecnology.fairlist.network.RemoteDataSource
import co.a3tecnology.fairlist.util.NetworkCheck
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSource(App.apiService)
    }

    private val networkCheck by lazy {
        NetworkCheck(ContextCompat.getSystemService(this, ConnectivityManager::class.java)!!)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        signUp_txt_click.setOnClickListener {
         finish()
        }

        register_btn.setOnClickListener {

            if (validateForm()) {
                register_btn.showProgress { progressColor = Color.WHITE }
                doRegister()
            }
        }

    }

    private fun doRegister() {
        val name = register_edt_name.text.toString()
        val email = register_edt_email.text.toString()
        val password = register_edt_password.text.toString()

        networkCheck.performActionIfConnected {
            remoteDataSource.register(RegisterRequest(name, email, password)) { result ->                    //token, throwable ->

                when(result) {
                    is Result.Success -> {
                        remoteDataSource.login(LoginRequest(email, password)) { result ->

                            when(result) {
                                is Result.Success -> {
                                    MainActivity.launch(this@SignUpActivity)
                                }

                                is Result.Failure -> {
                                    register_btn.hideProgress(R.string.register_now)

                                    if (result.error?.message != null) {

                                        Toast.makeText(
                                            this, result.error.message, Toast.LENGTH_LONG).show()

                                    } else {
                                        Toast.makeText(
                                            this@SignUpActivity, R.string.register_now,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }

                        }

                    }

                    is Result.Failure -> {
                        register_btn.hideProgress(R.string.register_now)

                        if (result.error?.message != null) {

                            Toast.makeText(
                                this, result.error.message, Toast.LENGTH_LONG).show()

                        } else {
                            Toast.makeText(
                                this@SignUpActivity, R.string.register_now,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

//                runOnUiThread {
//                    if (token != null) {
//                        MainActivity.launch(this@SignUpActivity)
//                    } else {
//                        register_btn.hideProgress(R.string.register_now)
//
//                        if (throwable != null) {
//
//                            Toast.makeText(
//                                    this, throwable.message, Toast.LENGTH_LONG).show()
//
//                        } else {
//                            Toast.makeText(
//                                    this@SignUpActivity, R.string.register_now,
//                                    Toast.LENGTH_LONG
//                            ).show()
//                        }
//
//                    }
//                }
            }
        }
    }

    private fun validateForm() : Boolean {
        val name = register_edt_name.text.toString()
        val email = register_edt_email.text.toString()
        val password = register_edt_password.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this@SignUpActivity, R.string.email_and_password_incorrect,
                Toast.LENGTH_LONG
            ).show()

            return false
        }

        return true
    }

}