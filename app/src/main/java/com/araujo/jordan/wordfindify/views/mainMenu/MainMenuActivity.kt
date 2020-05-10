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

package com.araujo.jordan.wordfindify.views.mainMenu

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.views.mainMenu.fragments.MainMenuFragment

/**
 * First Activity of the app and it's shows the menus for start the game
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
class MainMenuActivity : AppCompatActivity() {


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

    fun replaceFragment(view: View, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addSharedElement(view, view.transitionName)
            .replace(R.id.menuFragmentContainer, fragment)
            .addToBackStack(view.transitionName)
            .commit()
    }

}
