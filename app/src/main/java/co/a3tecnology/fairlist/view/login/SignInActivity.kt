package co.a3tecnology.fairlist.view.login

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import co.a3tecnology.fairlist.App
import co.a3tecnology.fairlist.R
import co.a3tecnology.fairlist.model.LoginRequest
import co.a3tecnology.fairlist.model.Result
import co.a3tecnology.fairlist.network.RemoteDataSource
import co.a3tecnology.fairlist.util.NetworkCheck
import co.a3tecnology.fairlist.view.main.MainActivity
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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
        setContentView(R.layout.activity_sign_in)

        if (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) ==
            Configuration.UI_MODE_NIGHT_YES) {
            login_img_logo.imageTintList = ColorStateList.valueOf(Color.WHITE)
        } else {
            login_img_logo.imageTintList = ColorStateList.valueOf(Color.BLACK)
        }

        login_txt_click.setOnClickListener {
            SignUpActivity.launch(this@SignInActivity)
        }

        login_btn_send.setOnClickListener {
            if (validatorForm())
                login_btn_send.showProgress { progressColor = Color.WHITE }
                doLogin()
        }
    }

    private fun doLogin() {

        val email = login_edt_email.text.toString()
        val password = login_edt_password.text.toString()

        networkCheck.performActionIfConnected {

            remoteDataSource.login(LoginRequest(email, password)) { result -> //token, throwable ->
                when(result) {

                    is Result.Success -> {
                        MainActivity.launch(this@SignInActivity)
                    }

                    is Result.Failure -> {

                      login_btn_send.hideProgress(R.string.btn_login)
                        if (result.error?.message != null) {
                            Toast.makeText(
                                this@SignInActivity, "Preencher os campos em Branco.",
                                Toast.LENGTH_LONG).show()
                        }
                        else {
                            Toast.makeText(
                                this@SignInActivity, R.string.invalid_fields,
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun validatorForm() : Boolean {
        val email = login_edt_email.text.toString()
        val password = login_edt_password.text.toString()

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            login_btn_send.hideProgress(R.string.btn_login)
            Toast.makeText(
                this@SignInActivity, R.string.email_and_password_incorrect,
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }
}