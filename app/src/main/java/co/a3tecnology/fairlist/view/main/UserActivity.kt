package co.a3tecnology.fairlist.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.a3tecnology.fairlist.R
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        btn_user_salvar.setOnClickListener {
            saveUser()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_user_cancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun saveUser() {
        if (validatorForm()) {
            val name = edt_dialog_user.text.toString()

            val sharedPref = getSharedPreferences(
                getString(R.string.bd_user), Context.MODE_PRIVATE)

            with (sharedPref.edit()) {
                putString("nome", name)
                apply()
            }

        }

    }

    private fun validatorForm() : Boolean {
        val name = edt_dialog_user.text.toString()

        if (name.isNullOrEmpty()) {
            Toast.makeText(this, R.string.title_empty, Toast.LENGTH_LONG).show()
            return false
        }
        return true

    }
}