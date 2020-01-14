package com.araujo.jordan.wordfindify.presenter

import android.graphics.Rect
import android.util.Log
import com.araujo.jordan.wordfindify.models.BoardChararacter
import java.util.*

class CharacterController(private val boardEvents: BoardListener) {

    var board = ArrayList<ArrayList<BoardChararacter>>()

    private var wordsAvailable = ArrayList<String>()
    private var allWords = ArrayList<String>()
    var boardRect = Rect()

    var selectingWord = ArrayList<BoardChararacter>()

    fun addCharacter(character: BoardChararacter) {
        if (!selectingWord.contains(character)) {
            Log.d("CharacterController", "Adding: $character")
            selectingWord.add(character)
        }
    }

    /**
     * Check selection for some word
     */
    fun checkForWord() {
        if (selectingWord.isEmpty() || selectingWord.size == 1) {
            selectingWord.clear()
            return
        }

        var word = ""
        selectingWord.forEach { word += it.char }
//        Log.d("CharacterController", "checkForWord() $word")


        val from = selectingWord.first()
        val to = selectingWord.last()

        val charactersBetween = getCharactersBetween(from, to)

        val wordSelected = getString(charactersBetween)
        Log.d("CharacterController", "checkForWord() $wordSelected")

        if (wordsAvailable.contains(getString(getCharactersBetween(from, to))) ||
            wordsAvailable.contains(getString(getCharactersBetween(to, from)).reversed())
        ) acceptWord(from, to)
        if (wordsAvailable.contains(getString(getCharactersBetween(to, from))) ||
            wordsAvailable.contains(getString(getCharactersBetween(to, from)).reversed())
        ) acceptWord(to, from)

        selectingWord.forEach {
            it.isOnSelection = false
        }
        selectingWord.clear()
    }

    fun acceptWord(
        from: BoardChararacter,
        to: BoardChararacter
    ) {
        val charactersBetween = getCharactersBetween(from, to)
        val wordSelected = getString(charactersBetween)

        removeWord(wordSelected)
        boardEvents.removeWord(charactersBetween)

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

    /**
     * Get characters between initial position and end position.
     * Beware, this can have a reversed word
     */
    fun getCharactersBetween(
        from: BoardChararacter,
        to: BoardChararacter
    ): ArrayList<BoardChararacter> {

//        if(from.position[0]>to.position[0] || from.position[1]>to.position[1])
//            getCharactersBetween(to,from)


        var yFrom = kotlin.math.min(from.position[0], to.position[0])
        val yTo: Int = kotlin.math.max(from.position[0], to.position[0])
        var xFrom = kotlin.math.min(from.position[1], to.position[1])
        val xTo = kotlin.math.max(from.position[1], to.position[1])

        val charactersBetween = ArrayList<BoardChararacter>()

        charactersBetween.add(from)


        while (xFrom < xTo || yFrom < yTo) {

            Log.d("Logsa", "$xFrom $yFrom $xTo $yTo")
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