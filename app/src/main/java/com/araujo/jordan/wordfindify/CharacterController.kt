package com.araujo.jordan.wordfindify

import android.util.Log
import android.widget.TextView
import java.util.*

class CharacterController(private val boardEvents: BoardListener) {

    var board = ArrayList<ArrayList<TextView>>()

    private var wordsAvailable = ArrayList<String>()
    private var allWords = ArrayList<String>()

    /**
     * Check selection for some word
     */
    fun checkForWord(from: TextView, to: TextView) {
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
                "Swift".toUpperCase(Locale.US),
                "Kotlin".toUpperCase(Locale.US),
                "ObjectiveC".toUpperCase(Locale.US),
                "Variable".toUpperCase(Locale.US),
                "Java".toUpperCase(Locale.US),
                "Mobile".toUpperCase(Locale.US)
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
        Log.e("yolo", "WORDS: " + wordsAvailable.toString())
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
    private fun getString(wordSelected: ArrayList<TextView>): String {
        var word = ""
        wordSelected.forEach {
            word += it.text.toString()
        }
        return word
    }

    private fun isALine(wordSelected: ArrayList<TextView>): Boolean {
        return isHorizontalLine(wordSelected) ||
                isVerticalLine(wordSelected) ||
                isDiagonalLine(wordSelected)
    }

    private fun isDiagonalLine(wordSelected: ArrayList<TextView>): Boolean {
        val firstChar: Array<Int> = wordSelected.first().tag as Array<Int>
        val lastChar: Array<Int> = wordSelected.last().tag as Array<Int>
        return (lastChar[0] - firstChar[0]) == (lastChar[1] - firstChar[1])
    }

    private fun isVerticalLine(wordSelected: ArrayList<TextView>): Boolean {
        val firstChar: Array<Int> = wordSelected.first().tag as Array<Int>
        val lastChar: Array<Int> = wordSelected.last().tag as Array<Int>
        return lastChar[0] == firstChar[0] && (lastChar[1] - firstChar[1]) != 0
    }

    private fun isHorizontalLine(wordSelected: ArrayList<TextView>): Boolean {
        val firstChar: Array<Int> = wordSelected.first().tag as Array<Int>
        val lastChar: Array<Int> = wordSelected.last().tag as Array<Int>
        return lastChar[1] == firstChar[1] && (lastChar[0] - firstChar[0]) != 0
    }

    /**
     * Get characters between initial position and end position.
     * Beware, this can have a reversed word
     */
    private fun getCharactersBetween(
        from: TextView,
        to: TextView
    ): ArrayList<TextView> {
        val positionFrom = from.tag as Array<Int>
        val positionTo = to.tag as Array<Int>

        var xFrom: Int
        var xTo: Int
        var yFrom: Int
        var yTo: Int

        if (positionFrom[0] <= positionTo[0]) {
            xFrom = positionFrom[0]
            xTo = positionTo[0]
        } else {
            xFrom = positionTo[0]
            xTo = positionFrom[0]
        }

        if (positionFrom[1] <= positionTo[1]) {
            yFrom = positionFrom[1]
            yTo = positionTo[1]
        } else {
            yFrom = positionTo[1]
            yTo = positionFrom[1]
        }

        val charactersBetween = ArrayList<TextView>()

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
    fun removeWord(charactersBetween: ArrayList<TextView>) {}
}