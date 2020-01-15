package com.araujo.jordan.wordfindify.presenter

import android.util.Log
import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.models.WordAvailable
import kotlin.math.max
import kotlin.math.min

/**
 * Designed and developed by Jordan Lira (@AraujoJordan)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class BoardPresenter(private val boardEvents: BoardListener) {

    var board = ArrayList<ArrayList<BoardChararacter>>()
    var allWords = ArrayList<WordAvailable>()
    var selectedWord = ArrayList<BoardChararacter>()

    fun addCharacter(character: BoardChararacter) {
        if (!selectedWord.contains(character)) {
            Log.d("CharacterController", "Adding: $character")
            selectedWord.add(character)
            boardEvents.updateSelectedWord(selectedWord)
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
        if (selectedWord.isEmpty() || selectedWord.size == 1) {
            selectedWord.clear()
            boardEvents.updateSelectedWord(selectedWord)
            return
        }

        var word = ""
        selectedWord.forEach { word += it.char }

        if (isALine(selectedWord) && containsAvailable(word)) {
            acceptWord(selectedWord)
            boardEvents.updateSelectedWord(selectedWord, true)
        } else {
            boardEvents.updateSelectedWord(selectedWord)
        }
        deselectWord()
    }

    private fun deselectWord() {
        selectedWord.forEach {
            it.isOnSelection = false
        }
        selectedWord.clear()
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
        if(allWords.isEmpty()) setWords()
        allWords.forEach { it.strikethrough = false }
        BoardBuilder(board).build(allWords.toTypedArray())
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
    fun updateSelectedWord(selectingWord: ArrayList<BoardChararacter>, acceptedWord:Boolean = false) {}
}