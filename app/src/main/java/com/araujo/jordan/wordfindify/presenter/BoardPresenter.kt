package com.araujo.jordan.wordfindify.presenter

import android.util.Log
import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.models.WordAvailable
import kotlin.math.max
import kotlin.math.min

class BoardPresenter(private val boardEvents: BoardListener) {

    var board = ArrayList<ArrayList<BoardChararacter>>()
    var allWords = ArrayList<WordAvailable>()
    var selectingWord = ArrayList<BoardChararacter>()

    fun addCharacter(character: BoardChararacter) {
        if (!selectingWord.contains(character)) {
            Log.d("CharacterController", "Adding: $character")
            selectingWord.add(character)
            boardEvents.selectingWord(selectingWord)
        }
    }

    private fun availableWords() = ArrayList(allWords.filter { !it.strikethrough })
    private fun containsAvailable(word: String) =
        availableWords().indexOfFirst { it.word == word } > -1

    private fun remove(word: String) {
        val wordIndex = allWords.indexOfFirst { it.word == word }
        if (wordIndex >= 0)
            allWords[wordIndex].strikethrough = true
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

        if (isALine(selectingWord) && containsAvailable(word)) {
            acceptWord(selectingWord)
            boardEvents.selectingWord(selectingWord, true)
        } else {
            boardEvents.selectingWord(selectingWord)
        }
        deselectWord()
    }

    private fun deselectWord() {
        selectingWord.forEach {
            it.isOnSelection = false
        }
        selectingWord.clear()
    }

    private fun acceptWord(
        selectingWord: ArrayList<BoardChararacter>
    ) {
        val wordSelected = getString(selectingWord)
        removeWord(wordSelected)
        boardEvents.updateWordList(allWords)

        selectingWord.forEach {
            it.selected = true
        }

        if (availableWords().size == 0)
            boardEvents.onVictory()
        deselectWord()
    }

    private fun setWords() {
        allWords.addAll(
            arrayOf(
                WordAvailable("Swift"),
                WordAvailable("Kotlin"),
                WordAvailable("ObjectiveC"),
                WordAvailable("Variable"),
                WordAvailable("Java"),
                WordAvailable("Mobile")
//                "HireMe",
//                "Shopify",
//                "React",
//                "Native",
//                "Jetpack"
            )
        )
    }

    fun reset() {
        allWords.clear()
        setWords()
        BoardBuilder(board).build(availableWords().toTypedArray())
    }

    private fun removeWord(wordToRemove: String) {
        if (containsAvailable(wordToRemove))
            remove(wordToRemove)
        else if (containsAvailable(wordToRemove.reversed()))
            remove(wordToRemove.reversed())
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
    fun updateWordList(words: ArrayList<WordAvailable>) {}
    fun selectingWord(selectingWord: ArrayList<BoardChararacter>,acceptedWord:Boolean = false) {}
}