package co.a3tecnology.fairlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        //remove todas a statusBar para que o layout seja visto em toda tela
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}