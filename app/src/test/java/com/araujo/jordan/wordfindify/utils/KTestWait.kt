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

package com.araujo.jordan.wordfindify.utils

import android.os.Looper
import org.robolectric.Shadows
import java.util.*

/**
 * Unit Test helper for async parts of code on Roboeletric
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
class KTestWait<T>(private var millisseconds: Long = 3000) {

    private var timeOfStarted: Long? = null
    private var waitObject: T? = null

    /**
     * This method will idle the the app that are using RoboEletric until the send method send
     * the required object or it reach the timeout defined in the millisseconds variable
     *
     * It's looks slow as it is a busy wait. But this code release all the background threads that
     * are pending, so the code will run it on background will rapidly be executed, even if have multiple
     * threads OR Coroutines to run.
     */
    fun receive(): T? {
        timeOfStarted = Date().time
        while (waitObject == null && !overtime()) Shadows.shadowOf(Looper.getMainLooper()).idle()
        return waitObject
    }

    /**
     * Interrupt the receive() loop
     */
    fun send(waitObject: T) {
        this.waitObject = waitObject
    }

    /**
     * Force stop the loop (it will be as timetout)
     */
    fun stop() {
        timeOfStarted = timeOfStarted?.plus(millisseconds)
    }

    /**
     * Check for overtime for reach the timeout
     */
    private fun overtime(): Boolean {
        return Date().time >= (timeOfStarted ?: Date().time) + millisseconds
    }
}