package co.a3tecnology.fairlist.view

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.a3tecnology.fairlist.R
import co.a3tecnology.fairlist.model.ItemResponse
import kotlinx.android.synthetic.main.list_item.view.*

class MainAdapter(
    var list: MutableList<ItemResponse> = arrayListOf()
) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder =
        MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item, parent, false
            )
        )

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun  bind(itemResponse: ItemResponse) {
            with(itemView) {
                item_txt_title.text = itemResponse.title
                item_txt_descricao.text = itemResponse.desc
                item_txt_date.text = itemResponse.date.toString()

               val layerDrawable =
                ContextCompat.getDrawable(context, R.drawable.bottom_type) as LayerDrawable
                layerDrawable.findDrawableByLayerId(R.id.mainDrawable)

                val gradientDrawable: GradientDrawable =
                    layerDrawable.findDrawableByLayerId(R.id.mainDrawable) as GradientDrawable

                gradientDrawable.setStroke(6, itemResponse.type)
                item_container.background = layerDrawable
            }
        }
    }

}