/**
 * Designed and developed by Jordan Lira (@araujojordan)
 *
 * Copyright (C) 2020 Jordan Lira de Araujo Junior
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.araujo.jordan.wordfindify.presenter.board

import android.os.CountDownTimer
import com.araujo.jordan.wordfindify.models.BoardCharacter
import com.araujo.jordan.wordfindify.models.WordAvailable
import com.araujo.jordan.wordfindify.presenter.requests.DataMuseAPI
import java.io.Serializable
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min

/**
 * Presenter class that manipulate the board
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
class BoardPresenter(private val boardEvents: BoardListener?) : Serializable {

    var board = ArrayList<ArrayList<BoardCharacter>>()
    var allWords = ArrayList<WordAvailable>()
    var selectedWord = ArrayList<BoardCharacter>()
    private var countDownTimer: CountDownTimer? = null
    private var countDownMil = 300000L

    /**
     * Add Character to update the user selection
     */
    fun addCharacter(character: BoardCharacter) {
        if (!selectedWord.contains(character)) {
            selectedWord.add(character)
            boardEvents?.updateSelectedWord(selectedWord)
        }
    }

    private fun availableWords() = ArrayList(allWords.filter { !it.strikethrough })
    private fun containsAvailable(word: String) =
        availableWords().indexOfFirst { it.word.equals(word, true) } > -1

    private fun remove(word: String) {
        val wordIndex = allWords.indexOfFirst { it.word.equals(word, true) }
        if (wordIndex >= 0)
            allWords[wordIndex].strikethrough = true
    }

    /**
     * Build an empty board
     */
    fun buildEmptyBoard(xSize: Int = 9, ySize: Int = 9): ArrayList<ArrayList<BoardCharacter>> {
        if (board.isNotEmpty()) return board
        val grid = ArrayList<ArrayList<BoardCharacter>>()
        for (y in 0..ySize) {
            val line = ArrayList<BoardCharacter>()
            for (x in 0..xSize) {
                line.add(BoardCharacter("", arrayOf(x, y), false))
            }
            grid.add(line)
        }
        board = grid
        return grid
    }

    /**
     * Check selection for some AvailableWord
     */
    fun checkForWord() {
        if (selectedWord.isEmpty() || selectedWord.size == 1) {
            selectedWord.clear()
            boardEvents?.updateSelectedWord(selectedWord)
            return
        }
        var word = ""
        selectedWord.forEach { word += it.char }

        if (isALine(selectedWord) && containsAvailable(word)) {
            acceptWord(selectedWord)
            boardEvents?.updateSelectedWord(selectedWord, true)
        } else {
            boardEvents?.updateSelectedWord(selectedWord)
        }
        deselectWord()
    }

    private fun deselectWord() {
        selectedWord.forEach {
            it.isOnSelection = false
        }
        selectedWord.clear()
        boardEvents?.updateSelectedWord()
    }

    private fun acceptWord(selectingWord: ArrayList<BoardCharacter>) {
        val wordSelected = getString(selectingWord)
        removeWord(wordSelected)
        boardEvents?.updateWordList(allWords)

        selectingWord.forEach {
            it.selected = true
        }

        if (availableWords().size == 0) {
            boardEvents?.onVictory()
        }
        deselectWord()
    }

    private fun setWordsCategory(category: String) {
        allWords.addAll(
            when (category) {
                "Shopify" -> listOf(
                    WordAvailable("Swift"),
                    WordAvailable("Kotlin"),
                    WordAvailable("ObjectiveC"),
                    WordAvailable("Variable"),
                    WordAvailable("Java"),
                    WordAvailable("Mobile")
                )
                "test" -> listOf(WordAvailable("Test"))
                else -> DataMuseAPI().getRandomWordList(category)
            }
        )
    }

    /**
     * Reset the board with the given category
     */
    fun reset(category: String) {
        allWords.clear()
        setWordsCategory(category)
        board.forEach { line ->
            line.forEach {
                it.selected = false
                it.isOnSelection = false
            }
        }
        BoardBuilder(board).build(allWords.toTypedArray())
    }

    private fun removeWord(wordToRemove: String) {
        if (containsAvailable(wordToRemove))
            remove(wordToRemove)
        else if (containsAvailable(wordToRemove.reversed()))
            remove(wordToRemove.reversed())
    }

    private fun getString(wordSelected: ArrayList<BoardCharacter>): String {
        var word = ""
        wordSelected.forEach {
            word += it.char
        }
        return word
    }

    private fun isALine(wordSelected: ArrayList<BoardCharacter>): Boolean {

        return isHorizontalLine(wordSelected) ||
                isVerticalLine(wordSelected) ||
                isDiagonal(wordSelected)
    }

    private fun isDiagonal(wordSelected: ArrayList<BoardCharacter>): Boolean {
        val distXY = (max(wordSelected.first().position[0], wordSelected.last().position[0]) -
                min(wordSelected.first().position[0], wordSelected.last().position[0]))
        return (distXY ==
                (max(wordSelected.first().position[1], wordSelected.last().position[1]) -
                        min(wordSelected.first().position[1], wordSelected.last().position[1]))) &&
                wordSelected.size - 1 == distXY
    }

    private fun isHorizontalLine(wordSelected: ArrayList<BoardCharacter>): Boolean {
        var last = wordSelected.first()
        wordSelected.forEach {
            if (last != it) {
                if (it.position[0] != last.position[0] || kotlin.math.abs(it.position[1] - last.position[1]) != 1) return false
                last = it
            }
        }
        return true
    }

    private fun isVerticalLine(wordSelected: ArrayList<BoardCharacter>): Boolean {
        var last = wordSelected.first()
        wordSelected.forEach {
            if (last != it) {
                if (it.position[1] != last.position[1] || kotlin.math.abs(it.position[0] - last.position[0]) != 1) return false
                last = it
            }
        }
        return true
    }

    /**
     * Destroy view related component connected with this class
     */
    fun onDestroy() {
        countDownTimer?.cancel()
        countDownTimer = null
    }

    /**
     * Start Countdown based on the difficulty of the game
     * @param difficulty defines the difficulty. For now, easy, medium and hard are available.
     * @return Return the countdown itself. Useful for testing
     */
    fun startCounter(difficulty: String?): CountDownTimer? {
        countDownMil = when (difficulty) {
            "easy" -> 300000L
            "medium" -> 150000L
            "hard" -> 120000L
            "test" -> 3000L
            else -> 300000L
        }

        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(countDownMil, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownMil -= 1000
                boardEvents?.updateClockTime(
                    String.format(
                        "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                    )
                )
            }

            override fun onFinish() {
                if (allWords.count { !it.strikethrough } > 0)
                    boardEvents?.onLose()
            }
        }
        countDownTimer?.start()
        return countDownTimer
    }

}

/**
 * Interface of the BoardPresenter Contract
 * It's show the actions that the board does on View
 */
interface BoardListener {
    /**
     * The board presenter will trigger this method if all WordAvailable where swiped
     */
    fun onVictory()

    /**
     * The board presenter will trigger this method in a timeout
     */
    fun onLose()

    /**
     * Update word list of the UI
     */
    fun updateWordList(words: ArrayList<WordAvailable>)

    /**
     * Accept or ignore word selection
     */
    fun updateSelectedWord(
        selectingWord: ArrayList<BoardCharacter>? = null,
        acceptedWord: Boolean = false
    )

    /**
     * Update bottom clock time
     */
    fun updateClockTime(clock: String)
}