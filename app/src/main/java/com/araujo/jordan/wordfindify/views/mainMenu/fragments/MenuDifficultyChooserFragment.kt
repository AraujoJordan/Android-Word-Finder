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

package com.araujo.jordan.wordfindify.views.mainMenu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.views.mainMenu.MainMenuActivity
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_difficulty_chooser.*

/**
 * Fragment that show the difficulty menu in the MainMenuActivity
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
class MenuDifficultyChooserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(inflater: LayoutInflater, view: ViewGroup?, bundle: Bundle?) =
        inflater.inflate(R.layout.fragment_difficulty_chooser, view, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goBackButton.setOnClickListener { activity?.onBackPressed() }

        menuLevel1.setOnClickListener {
            val act = (activity as? MainMenuActivity)
            act?.difficulty = "easy"
            act?.replaceFragment(view, MenuLevelSelectionFragment())
        }
        menuDifficultyMedium.setOnClickListener {
            val act = (activity as? MainMenuActivity)
            act?.difficulty = "medium"
            act?.replaceFragment(view, MenuLevelSelectionFragment())
        }
        menuDifficultyHard.setOnClickListener {
            val act = (activity as? MainMenuActivity)
            act?.difficulty = "hard"
            act?.replaceFragment(view, MenuLevelSelectionFragment())
        }


    }

}