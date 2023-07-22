package co.a3tecnology.fairlist.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import co.a3tecnology.fairlist.App
import co.a3tecnology.fairlist.R
import co.a3tecnology.fairlist.model.AddedResponse
import co.a3tecnology.fairlist.model.ItemResponse
import co.a3tecnology.fairlist.model.PriorityColor
import co.a3tecnology.fairlist.network.RemoteDataSource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddDialogFragment.AddedListener {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSource(App.apiService)
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
        } else {
            fab_main.setOnClickListener {
                val dialog = AddDialogFragment()
                dialog.setAddedListener(this)
                dialog.show(supportFragmentManager, dialog.tag)
            }
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

    override fun onAdded(addedResponse: AddedResponse) {
       adapter.add(
               ItemResponse(
               title = addedResponse.title,
               desc = addedResponse.desc,
               date = 0,
               type = PriorityColor.values()[addedResponse.priority].getColor()
            )
       )
    }
}