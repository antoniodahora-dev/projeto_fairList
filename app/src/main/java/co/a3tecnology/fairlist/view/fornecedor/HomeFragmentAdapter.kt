package co.a3tecnology.fairlist.view.fornecedor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.a3tecnology.fairlist.R
import co.a3tecnology.fairlist.model.ItemResponse

class HomeFragmentAdapter(
     val onClick: () -> Unit
) : RecyclerView.Adapter<HomeFragmentAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_fornecedor, parent, false)
        )


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(R.id.fornecedor_txt_title)
//      holder.bind(list[position])
    }

    override fun getItemCount(): Int = 2

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloFonecedor = itemView.findViewById<TextView>(R.id.fornecedor_txt_title)

        fun bind(itemView: Int) {
            tituloFonecedor.setOnClickListener {
                onClick.invoke()
            }

        }

    }

}