package co.a3tecnology.fairlist.view.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
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
import co.a3tecnology.fairlist.view.main.MainActivity
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_home.*

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

        if (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) ==
            Configuration.UI_MODE_NIGHT_YES) {
            register_img_logo.imageTintList = ColorStateList.valueOf(Color.WHITE)
        } else {
            register_img_logo.imageTintList = ColorStateList.valueOf(Color.BLACK)
        }

        signUp_txt_click.setOnClickListener {
         finish()
        }

        register_btn.setOnClickListener {
            if (validateForm()) {
                register_btn.showProgress { progressColor = Color.WHITE }
                doRegister()
                saveDateUser()
            }
        }

    }


    private fun doRegister() {
        val name = register_edt_name.text.toString()
        val email = register_edt_email.text.toString()
        val password = register_edt_password.text.toString()

        networkCheck.performActionIfConnected {
            remoteDataSource.register(RegisterRequest(name, email, password)) { result ->
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
                                        Toast.makeText(this@SignUpActivity,
                                            R.string.register_now, Toast.LENGTH_LONG).show()
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
                            Toast.makeText(this@SignUpActivity,
                                R.string.register_now,Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun validateForm() : Boolean {
        val name = register_edt_name.text.toString()
        val email = register_edt_email.text.toString()
        val password = register_edt_password.text.toString()

        if (name.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
           Toast.makeText(this@SignUpActivity,
               R.string.email_and_password_incorrect, Toast.LENGTH_LONG).show()
           return false
        }
        return true
    }

    private fun saveDateUser() {
        val name = register_edt_name.text.toString()

        val sharedPref = getSharedPreferences(
            getString(R.string.bd_user), Context.MODE_PRIVATE)

        with (sharedPref.edit()) {
            putString("nome", name)
            apply()
        }
    }

}