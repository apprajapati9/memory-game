package com.ajay.memorygame

import android.animation.ArgbEvaluator
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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


        recyclerViewBoard = findViewById(R.id.rcMoves)
        textViewNumMoves = findViewById(R.id.tvNumMoves)
        textViewNumPairs = findViewById(R.id.tvNumPairs)

        setUpBoard()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mi_refresh -> {
                if(isShowAlert()){
                    showResetDialog()
                }else
                    setUpBoard()
            }
            R.id.mi_easy -> {
                boardSize = BoardSize.EASY
                if(isShowAlert()){
                    showResetDialog()
                }else
                    setUpBoard()
            }
            R.id.mi_medium -> {
                boardSize = BoardSize.MEDIUM
                if(isShowAlert()){
                    showResetDialog()
                }else
                    setUpBoard()
            }
            R.id.mi_hard -> {
                boardSize = BoardSize.HARD
                if(isShowAlert()){
                    showResetDialog()
                }else
                    setUpBoard()
            }
            R.id.mi_vHard -> {
                boardSize = BoardSize.VERY_HARD
                if(isShowAlert()){
                    showResetDialog()
                }else
                    setUpBoard()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isShowAlert():Boolean{
        return memoryGame.getMoves()>0 && !memoryGame.haveWonGame()
    }

    private fun showResetDialog(){
        showAlert("Quit your current game?", null, View.OnClickListener {
            setUpBoard()
        })
    }

    private fun showAlert(title: String, view: View?, positiveButtonListener: View.OnClickListener) {
        AlertDialog.Builder(this).setTitle(title).setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok") { _, _ ->
                    positiveButtonListener.onClick(null) }.show()
    }

    private fun setUpBoard(){
        memoryGame = MemoryGame(boardSize)

        when(boardSize){
            BoardSize.EASY ->{
               // textViewNumPairs.text ="Pairs: 0 / 4"
                textViewNumMoves.text = "Easy: 4 x 2 "
            }
            BoardSize.MEDIUM -> {
                //textViewNumPairs.text = "Pairs: 0 / 9"
                textViewNumMoves.text ="Medium: 6 x 3"
            }
            BoardSize.HARD -> {
                //textViewNumPairs.text ="Pairs: 0 / 12"
                textViewNumMoves.text ="Hard: 6 x 4"
            }
            BoardSize.VERY_HARD ->{
                textViewNumMoves.text = "Very Hard: 8 x 4"
            }
        }

        textViewNumPairs.setTextColor(ContextCompat.getColor(this,R.color.color_progress_none))
        //textViewNumMoves.setText("Moves: ${memoryGame.getMoves()}")
        textViewNumPairs.setText("Pairs: ${memoryGame.numPairsfound} / ${boardSize.getNumPairs()}")

        adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards,
                object: MemoryBoardAdapter.CardClickListener{
                    override fun onCardClickListener(position: Int) {
                        //Toast.makeText(this@MainActivity, "Clicked position:" + position, Toast.LENGTH_SHORT).show()
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