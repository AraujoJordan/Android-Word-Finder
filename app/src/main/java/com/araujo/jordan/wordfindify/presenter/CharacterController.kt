package com.araujo.jordan.wordfindify.presenter

import android.util.Log
import com.araujo.jordan.wordfindify.models.BoardChararacter
import java.util.*
import kotlin.math.max
import kotlin.math.min

class CharacterController(private val boardEvents: BoardListener) {

    var board = ArrayList<ArrayList<BoardChararacter>>()

    var wordsAvailable = ArrayList<String>()
    var allWords = ArrayList<String>()
    var selectingWord = ArrayList<BoardChararacter>()

    fun addCharacter(character: BoardChararacter) {
        if (!selectingWord.contains(character)) {
            Log.d("CharacterController", "Adding: $character")
            selectingWord.add(character)
            boardEvents.selectingWord(selectingWord)
        }
    }

    /**
     * Check selection for some word
     */
    fun checkForWord() {
        if (selectingWord.isEmpty() || selectingWord.size == 1) {
            selectingWord.clear()
            boardEvents.selectingWord(selectingWord)
            return
        }

        var word = ""
        selectingWord.forEach { word += it.char }


        if (isALine(selectingWord) && wordsAvailable.contains(word))
            acceptWord(selectingWord)

        selectingWord.forEach {
            it.isOnSelection = false
        }
        selectingWord.clear()
        boardEvents.selectingWord(selectingWord)
    }

    fun acceptWord(
        selectingWord: ArrayList<BoardChararacter>
    ) {
        val wordSelected = getString(selectingWord)

        removeWord(wordSelected)
        boardEvents.removeWord(selectingWord)

        selectingWord.forEach {
            it.selected = true
        }

        if (wordsAvailable.size == 0) {
            boardEvents.onVictory()
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
            word += it.char
        }
        return word
    }

    private fun isALine(wordSelected: ArrayList<BoardChararacter>): Boolean {

        Log.d(
            "CharController",
            "isALine isHorizontalLine(wordSelected) " + isHorizontalLine(wordSelected)
        )
        Log.d(
            "CharController",
            "isALine isVerticalLine(wordSelected) " + isVerticalLine(wordSelected)
        )
        Log.d(
            "CharController",
            "isALine isDiagonalLine(wordSelected) " + isDiagonal(wordSelected)
        )

        return isHorizontalLine(wordSelected) ||
                isVerticalLine(wordSelected) ||
                isDiagonal(wordSelected)
    }

    private fun isDiagonal(wordSelected: ArrayList<BoardChararacter>): Boolean {
        val distXY = (max(wordSelected.first().position[0], wordSelected.last().position[0]) -
                min(wordSelected.first().position[0], wordSelected.last().position[0]))
        return (distXY ==
                (max(wordSelected.first().position[1], wordSelected.last().position[1]) -
                        min(wordSelected.first().position[1], wordSelected.last().position[1]))) &&
                wordSelected.size - 1 == distXY
    }

    private fun isHorizontalLine(wordSelected: ArrayList<BoardChararacter>): Boolean {
        var last = wordSelected.first()
        wordSelected.forEach {
            if (last != it) {
                if (it.position[0] != last.position[0] || kotlin.math.abs(it.position[1] - last.position[1]) != 1) return false
                last = it
            }
        }
        return true
    }

    private fun isVerticalLine(wordSelected: ArrayList<BoardChararacter>): Boolean {
        var last = wordSelected.first()
        wordSelected.forEach {
            if (last != it) {
                if (it.position[1] != last.position[1] || kotlin.math.abs(it.position[0] - last.position[0]) != 1) return false
                last = it
            }
        }
        return true
    }


}

interface BoardListener {
    fun onVictory() {}
    fun removeWord(charactersBetween: ArrayList<BoardChararacter>) {}
    fun selectingWord(selectingWord: ArrayList<BoardChararacter>) {}
}