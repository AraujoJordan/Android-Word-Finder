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

package com.araujo.jordan.wordfindify.presenter.storage

import android.annotation.SuppressLint
import android.content.Context
import com.araujo.jordan.wordfindify.BuildConfig
import com.araujo.jordan.wordfindify.models.Player
import com.araujo.jordan.wordfindify.presenter.level.LevelBuilder

/**
 * Used to preserve game status across app restarts
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
class StorageUtils {

    private val wordFinderAlias = BuildConfig.APPLICATION_ID

    /**
     * Get player information from SharedPreferences
     * @param context used to access SharedPref
     * @return the player of the game
     */
    fun getPlayer(ctx: Context): Player {
        val sharedPreferences = ctx.getSharedPreferences(wordFinderAlias, Context.MODE_PRIVATE)
        return Player(
            level = sharedPreferences.getInt("level", 1)
        )
    }

    /**
     * Save the player information on SharedPreferences
     * @param context used to access SharedPref
     * @param player player to be saved
     */
    fun savePlayer(ctx: Context, player: Player) {
        require(player.level in 0..LevelBuilder().getLevels().size)
        val sharedPreferences = ctx.getSharedPreferences(wordFinderAlias, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("level", player.level)
        editor.apply()
    }

    /**
     * Full delete game saved preferences
     */
    @SuppressLint("ApplySharedPref")
    fun deleteData(ctx: Context) {
        val sharedPreferences = ctx.getSharedPreferences(wordFinderAlias, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().commit()
    }
}