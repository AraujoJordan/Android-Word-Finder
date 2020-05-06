package com.araujo.jordan.wordfindify.views.mainMenu.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.presenter.level.LevelBuilder
import com.araujo.jordan.wordfindify.presenter.storage.StorageUtils
import com.araujo.jordan.wordfindify.views.board.BoardActivity
import com.araujo.jordan.wordfindify.views.mainMenu.MainMenuActivity
import com.araujojordan.ktlist.KtList
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_difficulty_chooser.goBackButton
import kotlinx.android.synthetic.main.fragment_level_chooser.*
import kotlinx.android.synthetic.main.item_level.view.*

class MenuLevelSelectionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(inflater: LayoutInflater, view: ViewGroup?, bundle: Bundle?) =
        inflater.inflate(R.layout.fragment_level_chooser, view, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goBackButton.setOnClickListener { activity?.onBackPressed() }

        val player = StorageUtils().getPlayer(view.context)

        levelGrid.adapter = KtList(
            LevelBuilder().getLevels(),
            R.layout.item_level,
            clickListener = { level, _, _ ->
                if (player.level >= level.level) {
                    val act = (activity as? MainMenuActivity)
                    startActivity(Intent(context, BoardActivity::class.java).apply {
                        putExtra("difficulty", act?.difficulty)
                        putExtra("level", level.level)
                    })
                }
            }
        ) { level, view ->
            view.alpha = if (player.level >= level.level) 1.0f else 0.5f
            view.itemLevelNumber.text = level.level.toString()
        }

    }

    override fun onResume() {
        super.onResume()
        levelGrid?.adapter?.notifyDataSetChanged()
    }
}