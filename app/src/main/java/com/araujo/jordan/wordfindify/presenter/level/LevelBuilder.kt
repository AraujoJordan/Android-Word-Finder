package com.araujo.jordan.wordfindify.presenter.level

import com.araujo.jordan.wordfindify.models.Level

class LevelBuilder {

    private val levels = listOf(
        Level(1, "Shopify"),
        Level(2, "food"),
        Level(3, "job"),
        Level(4, "tech"),
        Level(5, "phone"),
        Level(6, "car"),
        Level(7, "play"),
        Level(8, "fun"),
        Level(9, "time"),
        Level(10, "animal"),
        Level(11, "movie"),
        Level(12, "history")
    )

    fun getLevels() = levels
    fun getCategory(level: Int) = levels.firstOrNull { it.level == level }?.category ?: "Shopify"
}