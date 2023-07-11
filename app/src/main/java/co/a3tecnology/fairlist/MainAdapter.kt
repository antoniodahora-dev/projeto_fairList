package co.a3tecnology.fairlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder =
        MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item, parent, false
            )
        )

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
    }

    override fun getItemCount(): Int = 10

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}