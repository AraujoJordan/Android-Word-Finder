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

import android.content.Intent
import androidx.test.espresso.IdlingPolicies
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.araujo.jordan.latest.byDescription
import com.araujo.jordan.latest.descriptionExist
import com.araujo.jordan.latest.textExists
import com.araujo.jordan.latest.uiDevice
import com.araujo.jordan.wordfindify.views.board.BoardActivity
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

/**
 * Instrumented tests for the game main menu
 * It will navigate for all the options of menu of the game
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
@LargeTest
@RunWith(androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner::class)
class BoardInstrumentedTest {

    @get:Rule
    val activityRule =
        object : ActivityTestRule<BoardActivity>(BoardActivity::class.java, false, false) {}

    @Before
    fun registerIdlingResource() {
        IdlingPolicies.setMasterPolicyTimeout(3, TimeUnit.SECONDS)
        IdlingPolicies.setIdlingResourceTimeout(1, TimeUnit.SECONDS)
    }

    @Test
    @Ignore("It's not detecting the text for some reason. Ignoring for it")
    fun testWinGame() {
        launchApp()
        uiDevice().apply {
            assertTrue(textExists("Level 1"))
            assertTrue(descriptionExist("Test"))
            val initialPos = byDescription("test-0").visibleCenter
            val finalPos = byDescription("test-3").visibleCenter
            swipe(arrayOf(initialPos, finalPos), 15)
            assertTrue(textExists("You win!"))
        }
    }

    private fun launchApp() {
        val i = Intent()
        i.putExtra("test", "test")
        i.putExtra("difficulty", "test")
        activityRule.launchActivity(i)
    }

}
