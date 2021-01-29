package com.ajay.memorygame.models

import com.ajay.memorygame.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize) {

    val cards: List<MemoryCard>
    var numPairsfound = 0

    private var numFlipCard = 0

    private var indexOfSingleSelectedCard : Int? = null

    init {
        val chosenImages: List<Int> = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages: List<Int> = (chosenImages + chosenImages).shuffled()
        cards  = randomizedImages.map { MemoryCard(it) }

    }

    fun flipCard(position: Int): Boolean {
        numFlipCard++
        var foundMatch = false
        if(indexOfSingleSelectedCard == null){
            //0 or 2 cards previously flipped over
            //do restore cards + flip over selected one
            restoreCard()
            indexOfSingleSelectedCard = position
        }
        else{
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        cards[position].isFaceUp = !cards[position].isFaceUp
        return foundMatch
    }

    private fun checkForMatch(indexOfSingleSelectedCard: Int, position: Int): Boolean {
        if(cards[indexOfSingleSelectedCard].identifier != cards[position].identifier){
            return false;
        }
        cards[indexOfSingleSelectedCard].isMatched =true
        cards[position].isMatched = true
        numPairsfound++
        return true
    }

    private fun restoreCard() {
        for( card in cards){
            if(!card.isMatched)
                card.isFaceUp = false
        }
    }

    fun haveWonGame(): Boolean {
        return numPairsfound== boardSize.getNumPairs()
    }

    fun isFaceUp(position:Int): Boolean {
        return cards[position].isFaceUp
    }

    fun getMoves(): Int {
        return numFlipCard / 2
    }

}