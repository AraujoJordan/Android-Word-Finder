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
import com.araujo.jordan.wordfindify.models.WordAvailable
import com.araujo.jordan.wordfindify.presenter.board.BoardBuilder
import com.araujo.jordan.wordfindify.presenter.board.BoardPresenter
import com.araujo.jordan.wordfindify.utils.sharedExamples.BoardSharedExamples
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertTimeoutPreemptively
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.time.Duration.ofSeconds

/**
 * Unit Test for the board generation
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class BoardGenerationUnitTest {

    @Test
    @DisplayName("Generate one board with random characters")
    fun generateOneBoard() {
        assertTimeoutPreemptively(ofSeconds(3)) {
            val boardPresenter = BoardBuilder(BoardPresenter(null).buildEmptyBoard())
            boardPresenter.build(
                arrayOf(
                    WordAvailable(BoardSharedExamples.randomString()),
                    WordAvailable(BoardSharedExamples.randomString()),
                    WordAvailable(BoardSharedExamples.randomString()),
                    WordAvailable(BoardSharedExamples.randomString()),
                    WordAvailable(BoardSharedExamples.randomString()),
                    WordAvailable(BoardSharedExamples.randomString())
                )
            )
//            println(boardPresenter.board)
            assertTrue(true)
        }
    }

    @Test
    @DisplayName("Generate 1000 board with random characters")
    fun generateRandomBoardsStressTest() {
        assertTimeoutPreemptively(ofSeconds(40)) {
            repeat(1000) {
                val boardPresenter = BoardBuilder(BoardPresenter(null).buildEmptyBoard())
                boardPresenter.build(
                    arrayOf(
                        WordAvailable(BoardSharedExamples.randomString()),
                        WordAvailable(BoardSharedExamples.randomString()),
                        WordAvailable(BoardSharedExamples.randomString()),
                        WordAvailable(BoardSharedExamples.randomString()),
                        WordAvailable(BoardSharedExamples.randomString()),
                        WordAvailable(BoardSharedExamples.randomString())
                    )
                )
//                println(boardPresenter.board)
            }

            assertTrue(true)
        }
    }
}
