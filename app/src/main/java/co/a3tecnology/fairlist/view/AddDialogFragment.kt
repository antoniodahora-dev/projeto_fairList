package co.a3tecnology.fairlist.view

import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import co.a3tecnology.fairlist.App
import co.a3tecnology.fairlist.R
import co.a3tecnology.fairlist.model.AddRequest
import co.a3tecnology.fairlist.model.AddedResponse
import co.a3tecnology.fairlist.model.Result
import co.a3tecnology.fairlist.network.RemoteDataSource
import co.a3tecnology.fairlist.util.NetworkCheck
import kotlinx.android.synthetic.main.dialog_create.*

class AddDialogFragment : DialogFragment() {

    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSource(App.apiService)
    }

    private val networkCheck by lazy {
        NetworkCheck(ContextCompat.getSystemService(
                requireContext(), ConnectivityManager::class.java)!!)
    }


    private var itemAddedListener: AddedListener? = null

    interface AddedListener {
        fun onAdded(addedResponse: AddedResponse)
    }

    fun setAddedListener(listener: AddedListener) {
        this.itemAddedListener = listener
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_create, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn__dialog_salvar.setOnClickListener {
            saveItem()
        }
    }

    private fun saveItem() {
       if (validateForm()) {
           networkCheck.performActionIfConnected {
               val title = edt_dialog_product.text.toString()
               val desc: String? = if (!edt_dialog_desc.text.toString().isBlank())
                   edt_dialog_desc.text.toString()
               else
                   null

               val qtd: String? = if (!edt_dialog_qtd.text.toString().isBlank())
                   edt_dialog_qtd.text.toString()
               else
                   null

               val priority = save_dialog_spinner.selectedItemPosition

               remoteDataSource.addItem(AddRequest(title, desc, qtd, priority)) { result -> //ao invés de: item, error ->
                   when(result) {
                       is Result.Success -> {
                           result.data?.let { onItemAdded(it) }
                       }

                       is Result.Failure -> {
                           result.error?.message?.let { onItemAddedFailed(it) }
                       }
                   }

               }

           }
       }
    }

    private fun onItemAdded(addedResponse: AddedResponse) {
        itemAddedListener?.onAdded(addedResponse)
        dismiss()
    }
    private fun validateForm() : Boolean {
        val title = edt_dialog_product.text.toString()

        if (title.isNullOrEmpty()) {
            Toast.makeText(activity, R.string.title_empty, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun onItemAddedFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}