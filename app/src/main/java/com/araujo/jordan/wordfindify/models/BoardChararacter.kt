package com.araujo.jordan.wordfindify.models

/**
 * Designed and developed by Jordan Lira (@AraujoJordan)
 *
 * 19th January, 2020
 */
data class BoardChararacter(
    var char: String,
    val position: Array<Int>,
    var isOnSelection: Boolean = false,
    var selected: Boolean = false
)