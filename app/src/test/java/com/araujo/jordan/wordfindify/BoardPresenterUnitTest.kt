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

package com.araujo.jordan.wordfindify


import android.os.Build
import com.araujo.jordan.wordfindify.models.BoardCharacter
import com.araujo.jordan.wordfindify.models.WordAvailable
import com.araujo.jordan.wordfindify.presenter.board.BoardListener
import com.araujo.jordan.wordfindify.presenter.board.BoardPresenter
import com.araujo.jordan.wordfindify.utils.KTestWait
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

/**
 * Unit Test for the board generation
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class BoardPresenterUnitTest {

    @Test
    @DisplayName("Check for win situation")
    fun checkForVictory() {
        val waitTest = KTestWait<Boolean>(3000)
        val boardPresenter: BoardPresenter?
        boardPresenter = BoardPresenter(object : BoardListener {
            override fun onVictory() {
                waitTest.send(true)
            }

            override fun onLose() {
                waitTest.send(false)
            }

            override fun updateWordList(words: ArrayList<WordAvailable>) {
                println("updateWordList() words:$words")
            }

            override fun updateSelectedWord(
                selectingWord: ArrayList<BoardCharacter>?,
                acceptedWord: Boolean
            ) {
                println("updateSelectedWord:$selectingWord")
                println("acceptedWord:$acceptedWord")
            }

            override fun updateClockTime(clock: String) {
                println("clock:$clock")
            }
        })

        boardPresenter.buildEmptyBoard()
        boardPresenter.reset("test")
        boardPresenter.startCounter("test")
        boardPresenter.addCharacter(boardPresenter.board[0][0])
        boardPresenter.addCharacter(boardPresenter.board[1][0])
        boardPresenter.addCharacter(boardPresenter.board[2][0])
        boardPresenter.addCharacter(boardPresenter.board[3][0])
        boardPresenter.checkForWord()
        assertTrue(waitTest.receive() == true)
    }

    @Test
    @DisplayName("Check for lose situation")
    fun checkForLose() {
        val waitTest = KTestWait<Boolean>(6000)
        val boardPresenter: BoardPresenter?
        boardPresenter = BoardPresenter(object : BoardListener {
            override fun onVictory() {
                waitTest.send(true)
            }

            override fun onLose() {
                println("Time out!")
                waitTest.send(false)
            }

            override fun updateWordList(words: ArrayList<WordAvailable>) {}
            override fun updateSelectedWord(
                selectingWord: ArrayList<BoardCharacter>?,
                acceptedWord: Boolean
            ) {
            }

            override fun updateClockTime(clock: String) {
                println("clock:$clock")
            }
        })

        boardPresenter.buildEmptyBoard()
        boardPresenter.reset("test")
        val shadowTimer = Shadows.shadowOf(boardPresenter.startCounter("test"))
        boardPresenter.addCharacter(boardPresenter.board[0][0])
        boardPresenter.addCharacter(boardPresenter.board[1][0])
        boardPresenter.addCharacter(boardPresenter.board[2][0])
        boardPresenter.checkForWord()
        shadowTimer.invokeFinish()
        assertTrue(waitTest.receive() == false)
    }


}
