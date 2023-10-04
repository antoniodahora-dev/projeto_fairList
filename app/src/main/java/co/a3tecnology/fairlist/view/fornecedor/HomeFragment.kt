package co.a3tecnology.fairlist.view.fornecedor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import co.a3tecnology.fairlist.R
import co.a3tecnology.fairlist.view.FragmentAttachListener
import co.a3tecnology.fairlist.view.main.UserActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.list_fornecedor.*

class HomeFragment : Fragment() {

    private lateinit var adapter: HomeFragmentAdapter
    private var fragmentAttachListener: FragmentAttachListener? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        nameUser()

//        frag_txt_user.setOnClickListener {
//           val intent = Intent(requireActivity(), UserActivity::class.java)
//            startActivity(intent)
//
//        }
//
//        adapter = HomeFragmentAdapter { onClick() }
//
//        frag_rv_home.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

//    private fun dialogUser() {
//        val dialog = UserDialogFragment()
//        dialog.show(childFragmentManager, dialog.tag)
//
//    }

//    private fun nameUser() {
//      val txtResult = frag_txt_user
//
//            val sharedPref = this.requireActivity().getSharedPreferences(
//                getString(R.string.bd_user), Context.MODE_PRIVATE )
//
//            val nome = sharedPref.getString("nome", "")
//            txtResult.text = nome
//
//    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        if (context is FragmentAttachListener) {
//            fragmentAttachListener = context
//        }
//    }

//    override fun onDestroy() {
//        super.onDestroy()
//        fragmentAttachListener = null
//    }

//    private fun onClick() {
//        fornecedor_txt_title.setOnClickListener {
//            fragmentAttachListener?.goToProductFragment()
//            Toast.makeText(context, "Clicou na tela", Toast.LENGTH_LONG).show()
//        }
//    }

}