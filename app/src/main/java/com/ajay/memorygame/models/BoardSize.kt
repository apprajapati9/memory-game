package com.ajay.memorygame.models

enum class BoardSize(val numOfCards: Int) {

    EASY(8),
    MEDIUM(18),
    HARD(24);

//    fun getValueOfEnum():Int{
//        return
//    }

    fun getWidth():Int{
        return when(this){
            EASY -> 2
            MEDIUM -> 3
            HARD -> 4
        }
    }

    fun getHeigh():Int{
        return numOfCards / getWidth()
    }

    fun getNumPairs():Int{
        return numOfCards/2;
    }
}