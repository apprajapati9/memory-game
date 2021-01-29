package com.ajay.memorygame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.ajay.memorygame.models.BoardSize
import com.ajay.memorygame.models.MemoryCard
import kotlin.math.min

class MemoryBoardAdapter(private val context: Context,
                         private val numPieces: BoardSize,
                         private val images: List<MemoryCard>,
                        private val cardClickListener: CardClickListener) :
    RecyclerView.Adapter<MemoryBoardAdapter.AdapterHolder>() {

    companion object{
        private const val MARGIN_SIZE = 10
    }

    interface CardClickListener{
        fun onCardClickListener(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {

        val cardWidth = parent.width / numPieces.getWidth() - (2* MARGIN_SIZE)
        val cardHeight = parent.height / numPieces.getHeigh() - (2* MARGIN_SIZE)

        val cardSideLength = min(cardWidth, cardHeight)
        val view = LayoutInflater.from(context).inflate(R.layout.memory_card, parent, false)

        val layoutParams = view.findViewById<ImageButton>(R.id.imageButton).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = cardSideLength
        layoutParams.height = cardSideLength

        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)
        return AdapterHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = numPieces.numOfCards

    inner class AdapterHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)

        fun bind(position: Int) {
            val myCard  = images.get(position)
            imageButton.setImageResource(
                    if(myCard.isFaceUp)
                        myCard.identifier else R.drawable.ic_launcher_background)

            imageButton.alpha = if (myCard.isMatched) .4f else 1.0f

            val colorStateList = if(myCard.isMatched) ContextCompat.getColorStateList(context, R.color.color_transparent) else null
            ViewCompat.setBackgroundTintList(imageButton, colorStateList)

            imageButton.setOnClickListener{
                cardClickListener.onCardClickListener(position)
            }
        }

    }
}
