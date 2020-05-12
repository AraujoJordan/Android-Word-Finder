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

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.araujo.jordan.wordfindify.models.Player
import com.araujo.jordan.wordfindify.presenter.level.LevelBuilder
import com.araujo.jordan.wordfindify.presenter.storage.StorageUtils
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode


/**
 * Unit tests for the app storage
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class StorageUnitTest {

    @Test
    @DisplayName("Get Empty player")
    fun getEmptyPlayer() {
        val ctx = ApplicationProvider.getApplicationContext() as Context
        val stUtils = StorageUtils()
        stUtils.deleteData(ctx)
        val player = stUtils.getPlayer(ctx)
        assertTrue(player.level == 1)
    }

    @Test
    @DisplayName("Get Stored player level 5")
    fun getPlayer() {
        val ctx = ApplicationProvider.getApplicationContext() as Context
        val stUtils = StorageUtils()
        stUtils.deleteData(ctx)
        stUtils.savePlayer(ctx, Player(5))
        val player = stUtils.getPlayer(ctx)
        assertTrue(player.level == 5)

    }

    @Test
    @DisplayName("Check is player is being stored")
    fun savePlayer() {
        val ctx = ApplicationProvider.getApplicationContext() as Context
        val stUtils = StorageUtils()
        stUtils.deleteData(ctx)

        stUtils.savePlayer(ctx, Player(5))
        val player = stUtils.getPlayer(ctx)
        assertTrue(player.level == 5)
    }

    @Test
    @DisplayName("Fail to save user with strange level")
    fun savePlayerFail() {

        val ctx = ApplicationProvider.getApplicationContext() as Context
        val stUtils = StorageUtils()

        Assertions.assertThrows(Exception::class.java) {
            stUtils.deleteData(ctx)
            stUtils.savePlayer(ctx, Player(-1))
            assertTrue(true)
        }
        Assertions.assertThrows(Exception::class.java) {
            stUtils.deleteData(ctx)
            stUtils.savePlayer(ctx, Player(LevelBuilder().getLevels().size + 1))
            assertTrue(true)
        }
    }
}
