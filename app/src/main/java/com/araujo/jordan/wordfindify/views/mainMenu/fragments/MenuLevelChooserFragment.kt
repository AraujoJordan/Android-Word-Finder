package com.araujo.jordan.wordfindify.views.mainMenu.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.views.board.BoardActivity
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_level_chooser.*

class MenuLevelChooserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(inflater: LayoutInflater, view: ViewGroup?, bundle: Bundle?) =
        inflater.inflate(R.layout.fragment_level_chooser, view, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goBackButton.setOnClickListener { activity?.onBackPressed() }

        menuDifficultyEasy.setOnClickListener {
            startActivity(Intent(context, BoardActivity::class.java).apply {
                putExtra("difficulty", "easy")
            })
        }
        menuDifficultyMedium.setOnClickListener {
            startActivity(Intent(context, BoardActivity::class.java).apply {
                putExtra("difficulty", "medium")
            })
        }
        menuDifficultyHard.setOnClickListener {
            startActivity(Intent(context, BoardActivity::class.java).apply {
                putExtra("difficulty", "hard")
            })
        }


    }

}