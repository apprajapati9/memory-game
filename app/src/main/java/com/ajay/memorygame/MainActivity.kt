package com.ajay.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajay.memorygame.models.BoardSize
import com.ajay.memorygame.models.MemoryCard
import com.ajay.memorygame.models.MemoryGame
import com.ajay.memorygame.utils.DEFAULT_ICONS

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewBoard: RecyclerView
    private lateinit var textViewNumMoves: TextView
    private lateinit var textViewNumPairs: TextView

    private var boardSize: BoardSize = BoardSize.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val chosenImages: List<Int> = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
//        val randomizedImages: List<Int> = (chosenImages + chosenImages).shuffled()
//        val memoryCard: List<MemoryCard> = randomizedImages.map { MemoryCard(it) }

        val memoryGame = MemoryGame(boardSize)

        recyclerViewBoard = findViewById(R.id.rcMoves)
        textViewNumMoves = findViewById(R.id.tvNumMoves)
        textViewNumPairs = findViewById(R.id.tvNumPairs)

        recyclerViewBoard.adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards,
                object: MemoryBoardAdapter.CardClickListener{
                    override fun onCardClickListener(position: Int) {
                        Toast.makeText(this@MainActivity, "Clicked position:" + position, Toast.LENGTH_SHORT).show()
                    }

                });
        recyclerViewBoard.setHasFixedSize(true)
        recyclerViewBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }
}