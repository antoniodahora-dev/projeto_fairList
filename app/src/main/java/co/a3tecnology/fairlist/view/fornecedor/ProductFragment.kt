package co.a3tecnology.fairlist.view.fornecedor

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.a3tecnology.fairlist.App
import co.a3tecnology.fairlist.R
import co.a3tecnology.fairlist.model.*
import co.a3tecnology.fairlist.network.RemoteDataSource
import co.a3tecnology.fairlist.util.formatted
import co.a3tecnology.fairlist.view.FragmentAttachListener
import co.a3tecnology.fairlist.view.login.SignInActivity
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : Fragment(), AddDialogFragment.AddedListener {

    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSource(App.apiService)
    }

    private lateinit var adapter: MainAdapter
    private var fragmentAttachListener: FragmentAttachListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MainAdapter { id, position ->
//            editDialog()
            deleteItemClick(id, position)
        }

        frag_rv.adapter = adapter

        val token = App.getToken()

        if (token == null) {
            SignInActivity.launch(requireContext())
        } else {
            frag_progress_main.visibility = View.VISIBLE

            frag_fab_main.setOnClickListener {
                val dialog = AddDialogFragment()
                dialog.setAddedListener(this)
                dialog.show(childFragmentManager, dialog.tag)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         return inflater.inflate(R.layout.fragment_product, container, false)
    }


//    private fun editDialog() {
//        item_img_edit.setOnClickListener {
//            val dialog = AddDialogFragment()
//            dialog.show(childFragmentManager, dialog.tag)
//        }
//    }


    private fun deleteItemClick(id: Long, position: Int) {
        remoteDataSource.delete(id) { result ->
            when(result) {
                is Result.Success -> {
                    adapter.list.removeAt(position)
                    adapter.notifyItemRemoved(position)

                    if (adapter.list.isEmpty()) {
                        frag_txt_empty_list.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        remoteDataSource.getAll { result ->
            when(result) {

                is Result.Success -> {

                    result.data?.list?.let {

                        if (it.isEmpty()) {
                            frag_txt_empty_list.visibility = View.VISIBLE
                            frag_progress_main.visibility = View.GONE
                        } else {
                            frag_txt_empty_list.visibility = View.GONE
                            frag_progress_main.visibility = View.GONE

                            adapter.list.clear()
                            adapter.list.addAll(
                                it.map { item ->
                                    ItemResponse(
                                        id = item.id,
                                        user = item.user,
                                        title = item.title,
                                        desc = item.desc,
                                        qtd = item.qtd,
                                        date = item.date.formatted(),
                                        priority = PriorityColor.values()[item.priority].getColor()
                                    )
                                }
                            )
                        }
                        adapter.notifyDataSetChanged()
                    }
                }

                is Result.Failure -> {
                    Toast.makeText(context, result.error?.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }


    override fun onAdded(addedResponse: AddedResponse) {
      frag_txt_empty_list.visibility = View.GONE
        adapter.add(
            ItemResponse(
                id = addedResponse.id,
                user = addedResponse.user,
                title = addedResponse.title,
                desc = addedResponse.desc,
                qtd = addedResponse.qtd,
                date = addedResponse.date.formatted(),
                priority = PriorityColor.values()[addedResponse.priority].getColor()
            )
        )
    }
}