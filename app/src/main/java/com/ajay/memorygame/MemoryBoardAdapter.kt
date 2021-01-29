package com.ajay.memorygame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

class MemoryBoardAdapter(private val context: Context, private val numPieces: Int) :
    RecyclerView.Adapter<MemoryBoardAdapter.AdapterHolder>() {

    companion object{
        private const val MARGIN_SIZE = 10
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {

        val cardWidth = parent.width /2 - (2* MARGIN_SIZE)
        val cardHeight = parent.height / (numPieces/2) - (2* MARGIN_SIZE)

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

    override fun getItemCount() = numPieces

    inner class AdapterHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)

        fun bind(position: Int) {
            imageButton.setOnClickListener{
                Toast.makeText(context, "Clicked position:" + position, Toast.LENGTH_SHORT).show()
            }
        }

    }
}
