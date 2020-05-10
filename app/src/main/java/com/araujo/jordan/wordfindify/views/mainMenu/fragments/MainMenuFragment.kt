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

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.views.mainMenu.MainMenuActivity
import kotlinx.android.synthetic.main.fragment_main_menu.*

/**
 * Fragment that show first menu in the MainMenuActivity
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
class MainMenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, view: ViewGroup?, bundle: Bundle?) =
        inflater.inflate(R.layout.fragment_main_menu, view, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainMenuLogo.startAnimation(
            AnimationUtils.loadAnimation(context, R.anim.slide_logo_appearing)
        )


        mainMenuPlayButton.setOnClickListener {
            (activity as? MainMenuActivity)?.replaceFragment(
                it,
                MenuDifficultyChooserFragment()
            )

        }

        mainMenuAboutButton.setOnClickListener {
            (activity as? MainMenuActivity)?.replaceFragment(
                it,
                MenuAboutFragment()
            )
        }
        mainMenuExitButton.setOnClickListener { activity?.finish() }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ||
            newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
        ) {
            try {
                val ft = fragmentManager?.beginTransaction()
                if (Build.VERSION.SDK_INT >= 26) ft?.setReorderingAllowed(false)
                ft?.detach(this)?.attach(this)?.commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


}