package com.araujo.jordan.wordfindify.presenter.board

import android.util.Log
import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.models.WordAvailable
import kotlin.random.Random


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
class BoardBuilder(val board: ArrayList<ArrayList<BoardChararacter>>) {

    private var wordsAvailable = ArrayList<WordAvailable>()
    private val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val width = board.size
    private val height = board.size

    var type =
        Orientation.VERTICAL

    /**
     * Reset board and word list to be added
     * @param wordList list of words to be added
     */
    fun reset(wordList: Array<WordAvailable>) {
        wordsAvailable = ArrayList()
        wordsAvailable.addAll(wordList.toList().shuffled())
        for (w in 0 until width)
            for (h in 0 until height)
                board[w][h].char = ""
    }

    /**
     * Build the character table based on words that were given
     * @param wordList words to be add at the table
     */
    fun build(wordList: Array<WordAvailable>) {

        //clear previously board
        reset(wordList)

        //add words in board
        var attempt = 0
        while (wordsAvailable.isNotEmpty()) {

            //if it's 1000 attempts to fill the board, try another configuration
            if (attempt++ > 1000) {
                reset(wordList)
                attempt = 0
            }

            //Add an word in the board. Can be reversed (50%)
            val wordToAdd =
                if (Random.nextBoolean()) wordsAvailable.first().word else wordsAvailable.first().word.reversed()
            when (type) {
                Orientation.VERTICAL -> {
                    canAddVertically(wordToAdd)?.let { arrayOfAvailablePosition ->
                        addVertically(wordToAdd, arrayOfAvailablePosition)
                        wordsAvailable.removeAt(0)
                    }
                }
                Orientation.HORIZONTAL -> {
                    canAddHorizontally(wordToAdd)?.let { arrayOfAvailablePosition ->
                        addHorizontally(wordToAdd, arrayOfAvailablePosition)
                        wordsAvailable.removeAt(0)
                    }
                }
                Orientation.DIAGONAL_BACKSLASH -> {
                    canAddPrincipalDiagonal(wordToAdd)?.let { arrayOfAvailablePosition ->
                        addPrincipalDiagonalWord(wordToAdd, arrayOfAvailablePosition)
                        wordsAvailable.removeAt(0)
                    }
                }
                Orientation.DIAGONAL_FORWARDSLASH -> {
                    canAddSecondaryDiagonal(wordToAdd)?.let { arrayOfAvailablePosition ->
                        addSecondaryDiagonalWord(wordToAdd, arrayOfAvailablePosition)
                        wordsAvailable.removeAt(0)
                    }
                }
            }
        }

        //Add random characters in empty spaces
        for (x in 0 until width)
            for (y in 0 until height)
                if (board[x][y].char.isEmpty()) board[x][y].char = source.random().toString()
    }

    /**
     * Check if it's possible to add a Principal Diagonal word starting at an random place
     * @param wordToPut the word that you want to put it
     * @return the position that was possible to add
     */
    private fun canAddPrincipalDiagonal(wordToPut: String): Array<Int>? {
//        Log.d("BoardBuilder", "canAddVertically() ")

        val posX = Random.nextInt(0, width - 1)
        val posY = Random.nextInt(0, height - 1)

        try {
            //check if is possible to put in it
            for (position in wordToPut.indices) {
                if (board[posX + position][posY + position].char.isEmpty() || board[posX + position][posY + position].char == wordToPut[position].toString()) {
                    // can put it here
                } else {
                    return null
                }
            }
            return arrayOf(posX, posY)
        } catch (err: Exception) { //out of bonds
            return null
        }
    }

    /**
     * Add the Principal Diagonal word starting at the specific place
     * @param wordToPut the word that you want to put it
     * @param positionToPut position that it will start to put
     */
    private fun addPrincipalDiagonalWord(wordToPut: String, positionToPut: Array<Int>) {
        Log.d(
            "BoardBuilder",
            "addPrincipalDiagonalWord() $wordToPut ${positionToPut[0]} ${positionToPut[1]}"
        )
        type =
            Orientation.DIAGONAL_FORWARDSLASH
        for (offset in wordToPut.indices) {
            board[positionToPut[0] + offset][positionToPut[1] + offset].char =
                wordToPut[offset].toString()
        }
    }

    /**
     * Check if it's possible to add a Secondary Diagonal word starting at an random place
     * @param wordToPut the word that you want to put it
     * @return the position that was possible to add
     */
    private fun canAddSecondaryDiagonal(wordToPut: String): Array<Int>? {
//        Log.d("BoardBuilder", "canAddVertically() ")

        val posX = Random.nextInt(0, width - 1)
        val posY = Random.nextInt(0, height - 1)

        try {
            //check if is possible to put in it
            for (position in wordToPut.indices) {
                if (board[posX - position][posY + position].char.isEmpty() || board[posX - position][posY + position].char == wordToPut[position].toString()) {
                    // can put it here
                } else {
                    return null
                }
            }
            return arrayOf(posX, posY)
        } catch (err: Exception) { //out of bonds
            return null
        }
    }

    /**
     * Add the Secondary Diagonal word starting at the specific place
     * @param wordToPut the word that you want to put it
     * @param positionToPut position that it will start to put
     */
    private fun addSecondaryDiagonalWord(wordToPut: String, positionToPut: Array<Int>) {
        Log.d(
            "BoardBuilder",
            "addSecondaryDiagonalWord() $wordToPut ${positionToPut[0]} ${positionToPut[1]}"
        )
        type =
            Orientation.VERTICAL
        for (offset in wordToPut.indices) {
            board[positionToPut[0] - offset][positionToPut[1] + offset].char =
                wordToPut[offset].toString()
        }
    }

    /**
     * Check if it's possible to add a Vertical word starting at an random place
     * @param wordToPut the word that you want to put it
     * @return the position that was possible to add
     */
    private fun canAddVertically(wordToPut: String): Array<Int>? {
//        Log.d("BoardBuilder", "canAddVertically() ")

        val posX = Random.nextInt(0, width - 1)
        val posY = Random.nextInt(0, height - 1)

        try {
            //check if is possible to put in it
            for (position in wordToPut.indices) {
                if (board[posX + position][posY].char.isEmpty() || board[posX + position][posY].char == wordToPut[position].toString()) {
                    // can put it here
                } else {
                    return null
                }
            }
            return arrayOf(posX, posY)
        } catch (err: Exception) { //out of bonds
            return null
        }
    }

    /**
    * Add the Vertical word starting at the specific place
    * @param wordToPut the word that you want to put it
    * @param positionToPut position that it will start to put
    */
    private fun addVertically(wordToPut: String, positionToPut: Array<Int>) {
        type =
            Orientation.HORIZONTAL
        for (offset in wordToPut.indices) {
            board[positionToPut[0] + offset][positionToPut[1]].char = wordToPut[offset].toString()
        }
    }

    /**
     * Check if it's possible to add a Horizontal word starting at an random place
     * @param wordToPut the word that you want to put it
     * @return the position that was possible to add
     */
    private fun canAddHorizontally(wordToPut: String): Array<Int>? {
//        Log.d("BoardBuilder", "canAddHorizontally() ")

        val posX = Random.nextInt(0, width - 1)
        val posY = Random.nextInt(0, height - 1)

        try {
            //check if is possible to put in it
            for (position in wordToPut.indices) {
                if (board[posX][posY + position].char.isEmpty() || board[posX][posY + position].char == wordToPut[position].toString()) {
                    // can put it here
                } else {
                    return null
                }
            }
            return arrayOf(posX, posY)
        } catch (err: Exception) { //out of bonds
            return null
        }
    }

    /**
     * Add the Horizontal word starting at the specific place
     * @param wordToPut the word that you want to put it
     * @param positionToPut position that it will start to put
     */
    private fun addHorizontally(wordToPut: String, positionToPut: Array<Int>) {
        Log.d(
            "BoardBuilder",
            "addHorizontally() $wordToPut ${positionToPut[0]} ${positionToPut[1]}"
        )

        type =
            Orientation.DIAGONAL_BACKSLASH

        for (offset in wordToPut.indices)
            board[positionToPut[0]][positionToPut[1] + offset].char = wordToPut[offset].toString()
    }

    enum class Orientation {
        VERTICAL,
        HORIZONTAL,
        DIAGONAL_BACKSLASH,
        DIAGONAL_FORWARDSLASH
    }
}