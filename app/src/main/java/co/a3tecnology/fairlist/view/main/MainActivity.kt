package co.a3tecnology.fairlist.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import co.a3tecnology.fairlist.App
import co.a3tecnology.fairlist.R
import co.a3tecnology.fairlist.view.FragmentAttachListener
import co.a3tecnology.fairlist.view.fornecedor.HomeFragment
import co.a3tecnology.fairlist.view.fornecedor.ProductFragment
import co.a3tecnology.fairlist.view.login.SignInActivity

// AddDialogFragment.AddedListener
class MainActivity : AppCompatActivity(), FragmentAttachListener {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
//    private val remoteDataSource: RemoteDataSource by lazy {
//        RemoteDataSource(App.apiService)
//    }

//    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFragment()
//        adapter = MainAdapter { id, position ->
//            editDialog()
//            deleteItemClick(id, position)
//        }
//
//        main_rv.adapter = adapter
//
        val token = App.getToken()
        if (token == null) {
            SignInActivity.launch(this)
        }
//        else {
//            progress_main.visibility = View.VISIBLE
//            fab_main.setOnClickListener {
//                val dialog = AddDialogFragment()
//                dialog.setAddedListener(this)
//                dialog.show(supportFragmentManager, dialog.tag)
//            }
//        }
     }

    private fun homeFragment() {
        val fragment = HomeFragment()
        replaceFragment(fragment)

    }

    override fun goToProductFragment() {
       val fragment = ProductFragment()
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentById(R.id.fragment_home) == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_home, fragment)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_home, fragment)
                addToBackStack(null)
                commit()
            }
        }
    }
//
//    private fun editDialog() {
//        item_img_edit.setOnClickListener {
//            val dialog = AddDialogFragment()
//            dialog.show(supportFragmentManager, dialog.tag)
//        }
//    }
//
//    private fun deleteItemClick(id: Long, position: Int) {
//        remoteDataSource.delete(id) { result ->
//            when(result) {
//                is Result.Success -> {
//                    adapter.list.removeAt(position)
//                    adapter.notifyItemRemoved(position)
//
//                    if (adapter.list.isEmpty()) {
//                        main_txt_empty_list.visibility = View.VISIBLE
//                    }
//                }
//            }
//        }
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//        remoteDataSource.getAll { result ->
//            when(result) {
//
//                is Result.Success -> {
//
//                    result.data?.list?.let {
//
//                        if (it.isEmpty()) {
//                            main_txt_empty_list.visibility = View.VISIBLE
//                            progress_main.visibility = View.GONE
//                        } else {
//                            main_txt_empty_list.visibility = View.GONE
//                            progress_main.visibility = View.GONE
//
//                            adapter.list.clear()
//                            adapter.list.addAll(
//                                it.map { item ->
//                                    ItemResponse(
//                                        id = item.id,
//                                        title = item.title,
//                                        desc = item.desc,
//                                        qtd = item.qtd,
//                                        date = item.date.formatted(),
//                                        priority = PriorityColor.values()[item.priority].getColor()
//                                    )
//                                }
//                            )
//                        }
//                        adapter.notifyDataSetChanged()
//                    }
//                }
//
//                is Result.Failure -> {
//                    Toast.makeText(this, result.error?.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//
//    }
//
//    override fun onAdded(addedResponse: AddedResponse) {
//        main_txt_empty_list.visibility = View.GONE
//
//        adapter.add(
//            ItemResponse(
//                id = addedResponse.id,
//                title = addedResponse.title,
//                desc = addedResponse.desc,
//                qtd = addedResponse.qtd,
//                date = addedResponse.date.formatted(),
//                priority = PriorityColor.values()[addedResponse.priority].getColor()
//            )
//        )
//    }
}