package com.ajay.memorygame

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajay.memorygame.models.BoardSize
import com.ajay.memorygame.models.MemoryCard
import com.ajay.memorygame.models.MemoryGame
import com.ajay.memorygame.utils.DEFAULT_ICONS
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {


    private lateinit var rootConstraintLayout: ConstraintLayout
    private lateinit var adapter: MemoryBoardAdapter
    private lateinit var recyclerViewBoard: RecyclerView
    private lateinit var textViewNumMoves: TextView
    private lateinit var textViewNumPairs: TextView

    private lateinit var memoryGame: MemoryGame
    private var boardSize: BoardSize = BoardSize.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val chosenImages: List<Int> = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
//        val randomizedImages: List<Int> = (chosenImages + chosenImages).shuffled()
//        val memoryCard: List<MemoryCard> = randomizedImages.map { MemoryCard(it) }

        rootConstraintLayout = findViewById(R.id.rootView)
        memoryGame = MemoryGame(boardSize)

        recyclerViewBoard = findViewById(R.id.rcMoves)
        textViewNumMoves = findViewById(R.id.tvNumMoves)
        textViewNumPairs = findViewById(R.id.tvNumPairs)

        textViewNumPairs.setTextColor(ContextCompat.getColor(this,R.color.color_progress_none))
        textViewNumMoves.setText("Moves: ${memoryGame.getMoves()}")
        textViewNumPairs.setText("Pairs: ${memoryGame.numPairsfound} / ${boardSize.getNumPairs()}")

        adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards,
                object: MemoryBoardAdapter.CardClickListener{
                    override fun onCardClickListener(position: Int) {
                        Toast.makeText(this@MainActivity, "Clicked position:" + position, Toast.LENGTH_SHORT).show()
                        UpdateGameWithFlip(position)
                    }

                });
        recyclerViewBoard.adapter = adapter
        recyclerViewBoard.setHasFixedSize(true)
        recyclerViewBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }

    private fun UpdateGameWithFlip(position:Int){

        if(memoryGame.haveWonGame()){
            Snackbar.make(rootConstraintLayout, "You have won!", Snackbar.LENGTH_SHORT).show()
            return
        }

        if(memoryGame.isFaceUp(position)){
            Snackbar.make(rootConstraintLayout, "Invalid move!", Snackbar.LENGTH_SHORT).show()
            return
        }

        if(memoryGame.flipCard(position)){
            val color = ArgbEvaluator().evaluate(memoryGame.numPairsfound.toFloat()/boardSize.getNumPairs(),
            ContextCompat.getColor(this, R.color.color_progress_none),
            ContextCompat.getColor(this, R.color.color_progress_full)) as Int

            textViewNumPairs.setTextColor(color)
            textViewNumPairs.text = "Pairs: ${memoryGame.numPairsfound} / ${boardSize.getNumPairs()}"
        }

        textViewNumMoves.text = "Moves: ${memoryGame.getMoves()}"
        adapter.notifyDataSetChanged()
    }
}