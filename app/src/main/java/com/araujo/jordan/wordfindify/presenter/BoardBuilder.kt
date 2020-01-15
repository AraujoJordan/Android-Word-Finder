package com.araujo.jordan.wordfindify.presenter

import android.util.Log
import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.models.WordAvailable
import kotlin.random.Random

class BoardBuilder(val board: ArrayList<ArrayList<BoardChararacter>>) {

    private var wordsAvailable: ArrayList<WordAvailable> = ArrayList()
    private val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val width = board.size
    private val height = board.size

    var type = Orientation.VERTICAL

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
                    canAddBackslash(wordToAdd)?.let { arrayOfAvailablePosition ->
                        addBackSlash(wordToAdd, arrayOfAvailablePosition)
                        wordsAvailable.removeAt(0)
                    }
                }
                Orientation.DIAGONAL_FORWARDSLASH -> {
                    canAddForwardly(wordToAdd)?.let { arrayOfAvailablePosition ->
                        addForwardSlash(wordToAdd, arrayOfAvailablePosition)
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

    private fun canAddBackslash(wordToPut: String): Array<Int>? {
        Log.d("BoardBuilder", "canAddVertically() ")

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

    private fun addBackSlash(wordToPut: String, positionToPut: Array<Int>) {
        Log.d("BoardBuilder", "addVertically() $wordToPut")
        type = Orientation.DIAGONAL_FORWARDSLASH
        for (offset in wordToPut.indices) {
            board[positionToPut[0] + offset][positionToPut[1] + offset].char =
                wordToPut[offset].toString()
        }
    }

    private fun canAddForwardly(wordToPut: String): Array<Int>? {
        Log.d("BoardBuilder", "canAddVertically() ")

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

    private fun addForwardSlash(wordToPut: String, positionToPut: Array<Int>) {
        Log.d("BoardBuilder", "addVertically() $wordToPut")
        type = Orientation.VERTICAL
        for (offset in wordToPut.indices) {
            board[positionToPut[0] - offset][positionToPut[1] + offset].char =
                wordToPut[offset].toString()
        }
    }

    private fun canAddVertically(wordToPut: String): Array<Int>? {
        Log.d("BoardBuilder", "canAddVertically() ")

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

    private fun addVertically(wordToPut: String, positionToPut: Array<Int>) {
        Log.d("BoardBuilder", "addVertically() $wordToPut")
        type = Orientation.HORIZONTAL
        for (offset in wordToPut.indices) {
            board[positionToPut[0] + offset][positionToPut[1]].char = wordToPut[offset].toString()
        }
    }

    private fun canAddHorizontally(wordToPut: String): Array<Int>? {
        Log.d("BoardBuilder", "canAddHorizontally() ")

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

    private fun addHorizontally(wordToPut: String, positionToPut: Array<Int>) {
        Log.d("BoardBuilder", "addHorizontally() ")

        type = Orientation.DIAGONAL_BACKSLASH

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