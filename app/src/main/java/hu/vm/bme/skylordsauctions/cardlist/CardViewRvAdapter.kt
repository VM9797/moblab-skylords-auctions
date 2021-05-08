package hu.vm.bme.skylordsauctions.cardlist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.vm.bme.skylordsauctions.R
import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.util.Callback

class CardViewRvAdapter(private val context: Context, val onCardClick: Callback<Card>): RecyclerView.Adapter<CardViewRvAdapter.ViewHolder>() {
    var items: List<Card> = emptyList()


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardImage: ImageView = view.findViewById(R.id.ivCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_holder, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val card = items[position]

        viewHolder.cardImage.setOnClickListener {
            onCardClick(card)
        }

        Glide.with(context)
            .load(card.imageUrl)
            .into(viewHolder.cardImage)
    }

    override fun getItemCount() = items.size
}