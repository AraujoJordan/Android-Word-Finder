package com.araujo.jordan.wordfindify.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.presenter.BoardListener
import com.araujo.jordan.wordfindify.presenter.CharacterController
import kotlinx.android.synthetic.main.board_activity.*


class BoardActivity : AppCompatActivity(),
    BoardListener {

    private var boardController = CharacterController(this)
    private val boardAdapter by lazy { BoardAdapter(this, boardController) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board_activity)

        activityBoardGrid.adapter = boardAdapter
        boardController.board = buildBoard()
        boardController.reset()
        boardAdapter.updateGrid(boardController.board)
        activityBoardGrid.getDrawingRect(boardController.boardRect)
    }

    private fun buildBoard(): ArrayList<ArrayList<BoardChararacter>> {
        val grid = ArrayList<ArrayList<BoardChararacter>>()
        for (y in 0..9) {
            val line = ArrayList<BoardChararacter>()
            for (x in 0..9) {
                line.add(BoardChararacter("", arrayOf(x, y), false))
            }
            grid.add(line)
        }
        return grid
    }

    override fun onVictory() {

    }

    override fun removeWord(charactersBetween: ArrayList<BoardChararacter>) {

    }
}
