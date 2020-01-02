package com.araujo.jordan.wordfindify.presenter

import com.araujo.jordan.wordfindify.models.BoardChararacter
import java.util.*

class CharacterController(private val boardEvents: BoardListener) {

    var board = ArrayList<ArrayList<BoardChararacter>>()

    private var wordsAvailable = ArrayList<String>()
    private var allWords = ArrayList<String>()

    /**
     * Check selection for some word
     */
    fun checkForWord(from: BoardChararacter, to: BoardChararacter) {
        val charactersBetween = getCharactersBetween(from, to)

        val wordSelected = getString(charactersBetween)

        if (isALine(charactersBetween) &&
            (wordsAvailable.contains(wordSelected) ||
                    wordsAvailable.contains(wordSelected.reversed()))
        ) {
            removeWord(wordSelected)
            boardEvents.removeWord(charactersBetween)
            if (wordsAvailable.size == 0) {
                boardEvents.onVictory()
            }
        }
    }

    fun setWords() {
        allWords.addAll(
            arrayOf(
                "Swift",
                "Kotlin",
                "ObjectiveC",
                "Variable",
                "Java",
                "Mobile"
                //adding more words
//                "Hire",
//                "Shopify",
//                "React",
//                "Native",
//                "Jetpack"
            )
        )
    }

    /**
     * Reset board with random
     */
    fun reset() {
        setWords()
        wordsAvailable.clear()
        wordsAvailable.addAll(allWords)
        BoardBuilder(board).build(wordsAvailable.toTypedArray())
    }


    private fun removeWord(wordToRemove: String) {
        if (wordsAvailable.contains(wordToRemove))
            wordsAvailable.remove(wordToRemove)
        else if (wordsAvailable.contains(wordToRemove.reversed())) {
            wordsAvailable.remove(wordToRemove.reversed())
        }
    }

    /**
     * Get string from characters selected
     */
    private fun getString(wordSelected: ArrayList<BoardChararacter>): String {
        var word = ""
        wordSelected.forEach {
            word += it.char.toString()
        }
        return word
    }

    private fun isALine(wordSelected: ArrayList<BoardChararacter>): Boolean {
        return isHorizontalLine(wordSelected) ||
                isVerticalLine(wordSelected) ||
                isDiagonalLine(wordSelected)
    }

    private fun isDiagonalLine(wordSelected: ArrayList<BoardChararacter>): Boolean {
        val firstChar: Array<Int> = wordSelected.first().position
        val lastChar: Array<Int> = wordSelected.last().position
        return (lastChar[0] - firstChar[0]) == (lastChar[1] - firstChar[1])
    }

    private fun isVerticalLine(wordSelected: ArrayList<BoardChararacter>): Boolean {
        val firstChar: Array<Int> = wordSelected.first().position
        val lastChar: Array<Int> = wordSelected.last().position
        return lastChar[0] == firstChar[0] && (lastChar[1] - firstChar[1]) != 0
    }

    private fun isHorizontalLine(wordSelected: ArrayList<BoardChararacter>): Boolean {
        val firstChar: Array<Int> = wordSelected.first().position
        val lastChar: Array<Int> = wordSelected.last().position
        return lastChar[1] == firstChar[1] && (lastChar[0] - firstChar[0]) != 0
    }

    /**
     * Get characters between initial position and end position.
     * Beware, this can have a reversed word
     */
    private fun getCharactersBetween(
        from: BoardChararacter,
        to: BoardChararacter
    ): ArrayList<BoardChararacter> {
        val positionFrom = from.position
        val positionTo = to.position

        var xFrom: Int
        val xTo: Int
        if (positionFrom[0] <= positionTo[0]) {
            xFrom = positionFrom[0]
            xTo = positionTo[0]
        } else {
            xFrom = positionTo[0]
            xTo = positionFrom[0]
        }

        var yFrom: Int
        val yTo: Int
        if (positionFrom[1] <= positionTo[1]) {
            yFrom = positionFrom[1]
            yTo = positionTo[1]
        } else {
            yFrom = positionTo[1]
            yTo = positionFrom[1]
        }

        val charactersBetween = ArrayList<BoardChararacter>()

        charactersBetween.add(from)
        while (xFrom < xTo && yFrom < yTo) {
            if (xFrom < xTo)
                xFrom++
            if (yFrom < yTo)
                yFrom++
            charactersBetween.add(board[xFrom][yFrom])
        }

        return charactersBetween
    }

}

interface BoardListener {
    fun onVictory() {}
    fun removeWord(charactersBetween: ArrayList<BoardChararacter>) {}
}