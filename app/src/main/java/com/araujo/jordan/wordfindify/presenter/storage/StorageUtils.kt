package com.araujo.jordan.wordfindify.presenter.storage

import android.content.Context
import com.araujo.jordan.wordfindify.BuildConfig
import com.araujo.jordan.wordfindify.models.Player

class StorageUtils {

    private val wordFinderAlias = BuildConfig.APPLICATION_ID


    fun getPlayer(ctx: Context): Player {
        val sharedPreferences = ctx.getSharedPreferences(wordFinderAlias, Context.MODE_PRIVATE)
        return Player(
            level = sharedPreferences.getInt("level", 1)
        )
    }

    fun savePlayer(ctx: Context, player: Player) {
        val sharedPreferences = ctx.getSharedPreferences(wordFinderAlias, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("level", player.level)
        editor.apply()
    }
}