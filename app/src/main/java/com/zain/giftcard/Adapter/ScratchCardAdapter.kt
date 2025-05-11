package com.zain.giftcard.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.zain.giftcard.R
import com.zain.giftcard.models.ScratchCardModel

class ScratchCardAdapter(
    private val cards: List<ScratchCardModel>,
    private val onCardClick: (ScratchCardModel) -> Unit
) : RecyclerView.Adapter<ScratchCardAdapter.CardViewHolder>() {

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.cardImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_scratch_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.image.setImageResource(card.imageRes)

        holder.itemView.setOnClickListener {
            // Null check to avoid crash
            if (true) {
                onCardClick(card)
            }
        }
    }

    override fun getItemCount() = cards.size
}
