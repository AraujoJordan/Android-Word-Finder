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
import com.araujo.jordan.latest.scrollUntilFindText
import com.araujo.jordan.latest.textClick
import com.araujo.jordan.latest.textExists
import com.araujo.jordan.latest.uiDevice
import com.araujo.jordan.wordfindify.models.Player
import com.araujo.jordan.wordfindify.presenter.level.LevelBuilder
import com.araujo.jordan.wordfindify.presenter.storage.StorageUtils
import com.araujo.jordan.wordfindify.views.mainMenu.MainMenuActivity
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
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
class MenuInstrumentedTest {

    @get:Rule
    val activityRule =
        object : ActivityTestRule<MainMenuActivity>(MainMenuActivity::class.java, false, false) {}

    @Before
    fun registerIdlingResource() {
        IdlingPolicies.setMasterPolicyTimeout(3, TimeUnit.SECONDS)
        IdlingPolicies.setIdlingResourceTimeout(1, TimeUnit.SECONDS)
    }

    @Test
    fun testMenuAbout() {
        launchApp()
        uiDevice().apply {
            textClick("About")
            assertTrue(scrollUntilFindText("Contact me")?.isEnabled == true)
        }
    }

    @Test
    fun testMenuExit() {
        launchApp()
        uiDevice().apply {
            textClick("Exit")
            assertFalse(textExists("Play"))
        }
    }

    @Test
    fun testMenuDifficulty() {
        launchApp()
        uiDevice().apply {
            textClick("Play")
            assertTrue(textExists("Easy"))
            assertTrue(textExists("Medium"))
            assertTrue(textExists("Hard"))
        }
    }

    @Test
    fun testMenuLevels() {
        launchApp()
        uiDevice().apply {
            StorageUtils().savePlayer(activityRule.activity, Player(12))
            textClick("Play")
            textClick("Easy")
            repeat(LevelBuilder().getLevels().size) {
                assertTrue(textExists("${it + 1}"))
            }
        }
    }

    @Test
    fun testAccessAllLevelsAndDifficulties() {
        launchApp()
        uiDevice().apply {
            StorageUtils().savePlayer(activityRule.activity, Player(12))
            textClick("Play")
            arrayOf("Easy", "Medium", "Hard").forEach { difficulty ->
                textClick(difficulty)
                repeat(LevelBuilder().getLevels().size) {
                    textClick("${it + 1}")
                    assertTrue(textExists("Level ${it + 1}", 8000))
                    pressBack()
                }
                pressBack()
            }
        }
    }

    @Test
    fun testMenuLevels5Enabled() {
        launchApp()
        uiDevice().apply {
            StorageUtils().savePlayer(activityRule.activity, Player(5))
            textClick("Play")
            textClick("Easy")
            textClick("6")
            assertFalse(textExists("Level 6"))
            textClick("5")
            assertTrue(textExists("Level 5"))
        }
    }

    private fun launchApp() {
        val i = Intent()
        activityRule.launchActivity(i)
    }

}
