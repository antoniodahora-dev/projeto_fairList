package co.a3tecnology.fairlist.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.a3tecnology.fairlist.App
import co.a3tecnology.fairlist.R
import co.a3tecnology.fairlist.network.RemoteDataSource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSource()
    }

    private lateinit var adapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter()
        main_rv.adapter = adapter

        val token = App.getToken()
        if (token == null) {
            SignInActivity.launch(this)
        }
     }

    override fun onStart() {
        super.onStart()
        remoteDataSource.getAll { list, throwable ->
            runOnUiThread {
                list?.let {
                  adapter.list.clear()
                    adapter.list.addAll(it)
                    adapter.notifyDataSetChanged()

                }
            }
        }
    }
}