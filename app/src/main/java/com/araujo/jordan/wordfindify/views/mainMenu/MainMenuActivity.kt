package com.araujo.jordan.wordfindify.views.mainMenu

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.views.mainMenu.fragments.MainMenuFragment

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
 * KtList is a RecyclerView.Adapter implementation that make easier to implement hard stuffs like
 * HeaderView, EmptyView, InfiniteScroll and so on. It will also make it easy to implement the
 * adapter itself as you don't need to implement ViewHolders and others boilerplate methods won't
 * change in most of implementations.
 */
class MainMenuActivity : AppCompatActivity() {

//    var restartAnim: Handler? = null
//    var restartRunnable: Runnable? = null

    var level: Int = 1
    var difficulty = "easy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        supportFragmentManager.beginTransaction()
            .add(
                R.id.menuFragmentContainer,
                MainMenuFragment()
            )
            .commit()
    }

    override fun onPause() {
        super.onPause()
//        restartRunnable?.let { restartAnim?.removeCallbacks(it) }
    }

    override fun onResume() {
        super.onResume()
//        restartRunnable = Runnable {

//            mainMenuKonfetti.build()
//                .addColors(
//                    Color.YELLOW,
//                    Color.GREEN,
//                    Color.MAGENTA,
//                    Color.BLUE
//                )
//                .setDirection(0.0, 359.0)
//                .setSpeed(2f, 18f)
//                .setFadeOutEnabled(true)
//                .setTimeToLive(2100L)
//                .addShapes(Shape.Square, Shape.Circle)
//                .addSizes(Size(12, 5f))
//                .setPosition(-50f, mainMenuKonfetti.width + 50f, -50f, -50f)
//                .streamFor(150, 10000L)
//            restartRunnable?.let { restartAnim?.postDelayed(it, 10000) }
//        }
//        restartAnim = Handler()
//        restartRunnable?.let { restartAnim?.post(it) }
    }

    fun replaceFragment(view: View, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addSharedElement(view, view.transitionName)
            .replace(R.id.menuFragmentContainer, fragment)
            .addToBackStack(view.transitionName)
            .commit()
    }

}
