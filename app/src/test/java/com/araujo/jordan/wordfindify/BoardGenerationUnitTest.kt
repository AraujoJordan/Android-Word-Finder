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

import com.araujo.jordan.wordfindify.models.WordAvailable
import com.araujo.jordan.wordfindify.presenter.board.BoardBuilder
import com.araujo.jordan.wordfindify.presenter.board.BoardPresenter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError
import java.time.Duration.ofSeconds
import kotlin.random.Random

/**
 * Unit Test for the board generation
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
class BoardGenerationUnitTest {

    @Test
    fun generateOneBoard() {
        assertTimeoutPreemptively(ofSeconds(3)) {
            val boardPresenter = BoardBuilder(BoardPresenter(null).buildEmptyBoard())
            boardPresenter.build(
                arrayOf(
                    WordAvailable(randomString()),
                    WordAvailable(randomString()),
                    WordAvailable(randomString()),
                    WordAvailable(randomString()),
                    WordAvailable(randomString()),
                    WordAvailable(randomString())
                )
            )
//            println(boardPresenter.board)
            assertTrue(true)
        }
    }

    @Test
    fun generateRandomBoardsStressTest() {
        assertTimeoutPreemptively(ofSeconds(40)) {
            repeat(1000) {
                val boardPresenter = BoardBuilder(BoardPresenter(null).buildEmptyBoard())
                boardPresenter.build(
                    arrayOf(
                        WordAvailable(randomString()),
                        WordAvailable(randomString()),
                        WordAvailable(randomString()),
                        WordAvailable(randomString()),
                        WordAvailable(randomString()),
                        WordAvailable(randomString())
                    )
                )
//                println(boardPresenter.board)
            }

            assertTrue(true)
        }
    }

    @Test
    fun impossibleBoardGeneration() {
        assertThrows(AssertionFailedError::class.java) {
            assertTimeoutPreemptively(ofSeconds(5)) {
                val boardPresenter = BoardBuilder(BoardPresenter(null).buildEmptyBoard())
                boardPresenter.build(
                    arrayOf(
                        WordAvailable("aaaaaaaaaa"),
                        WordAvailable("bbbbbbbbbb"),
                        WordAvailable("cccccccccc"),
                        WordAvailable("dddddddddd"),
                        WordAvailable("eeeeeeeeee"),
                        WordAvailable("ffffffffff"),
                        WordAvailable("gggggggggg"),
                        WordAvailable("hhhhhhhhhh"),
                        WordAvailable("iiiiiiiiii"),
                        WordAvailable("jjjjjjjjjj"),
                        WordAvailable("llllllllll")
                    )
                )
//                println(boardPresenter.board)
                fail("This should not be reach")
            }
        }
    }

    private fun randomString(stringLength: Int = Random.nextInt(3, 10)): String {
        val list = "abcdefghijklmnopqrstuvwxyz".toCharArray()
        var randomS = ""
        for (i in 1..stringLength) {
            randomS += list[getRandomNumber(0, list.size - 1)]
        }
        return randomS
    }

    private fun getRandomNumber(min: Int, max: Int) = Random.nextInt((max - min) + 1) + min
}
