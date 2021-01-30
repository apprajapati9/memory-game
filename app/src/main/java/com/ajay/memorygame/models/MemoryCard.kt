package com.ajay.memorygame.models

data class MemoryCard (
            val identifier:Int,
            var isFaceUp: Boolean = false,
            var isMatched: Boolean = false)

// data class always have rounded brackets