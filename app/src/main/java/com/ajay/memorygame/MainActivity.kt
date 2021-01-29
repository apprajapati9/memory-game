package com.ajay.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewBoard: RecyclerView
    private lateinit var textViewNumMoves: TextView
    private lateinit var textViewNumPairs: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewBoard = findViewById(R.id.rcMoves)
        textViewNumMoves = findViewById(R.id.tvNumMoves)
        textViewNumPairs = findViewById(R.id.tvNumPairs)

        recyclerViewBoard.adapter = MemoryBoardAdapter(this, 8);
        recyclerViewBoard.setHasFixedSize(true)
        recyclerViewBoard.layoutManager = GridLayoutManager(this, 2 )
    }
}