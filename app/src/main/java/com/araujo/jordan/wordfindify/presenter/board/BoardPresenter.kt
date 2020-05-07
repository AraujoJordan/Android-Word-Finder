package com.araujo.jordan.wordfindify.presenter.board

import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.models.WordAvailable
import com.araujo.jordan.wordfindify.presenter.requests.DataMuseAPI
import java.io.Serializable
import kotlin.math.max
import kotlin.math.min

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
 * KtList is a RecyclerView.Adapter implementation that make easier to implement hard stuffs like
 * HeaderView, EmptyView, InfiniteScroll and so on. It will also make it easy to implement the
 * adapter itself as you don't need to implement ViewHolders and others boilerplate methods won't
 * change in most of implementations.
 */
class BoardPresenter(private val boardEvents: BoardListener?) : Serializable {

    var board = ArrayList<ArrayList<BoardChararacter>>()
    var allWords = ArrayList<WordAvailable>()
    var selectedWord = ArrayList<BoardChararacter>()

    fun addCharacter(character: BoardChararacter) {
        if (!selectedWord.contains(character)) {
            selectedWord.add(character)
            boardEvents?.updateSelectedWord(selectedWord)
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

    fun buildBoard(): ArrayList<ArrayList<BoardChararacter>> {
        if (board.isNotEmpty()) return board
        val grid = ArrayList<ArrayList<BoardChararacter>>()
        for (y in 0..9) {
            val line = ArrayList<BoardChararacter>()
            for (x in 0..9) {
                line.add(BoardChararacter("", arrayOf(x, y), false))
            }
            grid.add(line)
        }
        return grid
    }

    /**
     * Check selection for some word
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

    private fun acceptWord(
        selectingWord: ArrayList<BoardChararacter>
    ) {
        val wordSelected = getString(selectingWord)
        removeWord(wordSelected)
        boardEvents?.updateWordList(allWords)

        selectingWord.forEach {
            it.selected = true
        }

        if (availableWords().size == 0)
            boardEvents?.onVictory()
        deselectWord()
    }

    private fun setWords(category: String) {
        if (category == "Shopify")
            allWords.addAll(
                arrayOf(
                    WordAvailable("Swift"),
                    WordAvailable("Kotlin"),
                    WordAvailable("ObjectiveC"),
                    WordAvailable("Variable"),
                    WordAvailable("Java"),
                    WordAvailable("Mobile")
                )
            ) else
            allWords.addAll(DataMuseAPI().getRandomWordList(category))
    }

    fun reset(category: String) {
        allWords.clear()
        setWords(category)
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
    fun updateSelectedWord(
        selectingWord: ArrayList<BoardChararacter>? = null,
        acceptedWord: Boolean = false
    ) {
    }
}