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

package com.araujo.jordan.wordfindify.models

import java.util.*

/**
 * Model class that represent a character in the board
 *
 * @author Jordan L. Araujo Jr. (araujojordan)
 * @param char The char itself in a string (so it easier to concatenate and generate string word)
 * @param position the position where index 0 is X and index 1 is Y.
 * @param isOnSelection is used to show animations on board when swiping
 * @param selected the char was already selected in a previously word
 */
data class BoardCharacter(
    var char: String,
    val position: Array<Int>,
    var isOnSelection: Boolean = false,
    var selected: Boolean = false,
    var id: Long = Date().time
) {
    /**
     * Postion in X terms
     */
    fun x() = position[1]

    /**
     * Position in Y terms
     */
    fun y() = position[0]
    override fun toString() = char
}