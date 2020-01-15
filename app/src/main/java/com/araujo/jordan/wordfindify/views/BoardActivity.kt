package com.araujo.jordan.wordfindify.views

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.models.WordAvailable
import com.araujo.jordan.wordfindify.presenter.BoardListener
import com.araujo.jordan.wordfindify.presenter.BoardPresenter
import kotlinx.android.synthetic.main.board_activity.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


class BoardActivity : AppCompatActivity(),
    BoardListener {

    private var boardPresenter = BoardPresenter(this)
    private val boardAdapter by lazy { BoardAdapter(this, boardPresenter) }
    private val wordsAvailableAdapter by lazy { WordListAdapter(boardPresenter.allWords) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board_activity)

        activityBoardGrid.adapter = boardAdapter
        boardPresenter.board = buildBoard()
        boardPresenter.reset()
        boardAdapter.updateGrid(boardPresenter.board)

        activityBoardAvailableWordsGrid.adapter = wordsAvailableAdapter
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

    override fun selectingWord(selectingWord: ArrayList<BoardChararacter>, acceptedWord: Boolean) {
        Log.d("BoardActivity","selectingWord()")
        if (acceptedWord) {
            activityBoardSelectedWord.animate().setInterpolator(DecelerateInterpolator()).scaleX(2f)
                .scaleY(2f).alpha(0f).withEndAction {
                    activityBoardSelectedWord.text = ""
                    activityBoardSelectedWord.alpha = 1f
                    activityBoardSelectedWord.scaleX = 1f
                    activityBoardSelectedWord.scaleY = 1f
                }.start()
            return
        } else {
            var word = ""
            selectingWord.forEach { word += it.char }
            activityBoardSelectedWord.text = word
        }
    }

    override fun onVictory() {
        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.BLUE, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(1500L)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes(Size(12))
            .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
            .streamFor(300, 1500L)
    }

    override fun updateWordList(words: ArrayList<WordAvailable>) {
        wordsAvailableAdapter.updateList(words)
    }

}
