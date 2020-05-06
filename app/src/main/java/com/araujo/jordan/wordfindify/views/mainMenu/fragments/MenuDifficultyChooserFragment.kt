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


//            startActivity(Intent(context, BoardActivity::class.java).apply {
//                putExtra("difficulty", "easy")
//            })
        }
        menuDifficultyMedium.setOnClickListener {

            val act = (activity as? MainMenuActivity)
            act?.difficulty = "medium"
            act?.replaceFragment(view, MenuLevelSelectionFragment())

//            startActivity(Intent(context, BoardActivity::class.java).apply {
//                putExtra("difficulty", "medium")
//            })
        }
        menuDifficultyHard.setOnClickListener {

            val act = (activity as? MainMenuActivity)
            act?.difficulty = "hard"
            act?.replaceFragment(view, MenuLevelSelectionFragment())

//            startActivity(Intent(context, BoardActivity::class.java).apply {
//                putExtra("difficulty", "hard")
//            })
        }


    }

}