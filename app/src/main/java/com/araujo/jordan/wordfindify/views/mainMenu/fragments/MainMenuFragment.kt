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