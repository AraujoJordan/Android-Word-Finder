package com.araujo.jordan.wordfindify.views

//import kotlinx.android.synthetic.main.board_activity.*
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.models.WordAvailable
import com.araujo.jordan.wordfindify.presenter.BoardListener
import com.araujo.jordan.wordfindify.presenter.BoardPresenter
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


/**
 * Designed and developed by Jordan Lira (@AraujoJordan)
 *
 * 19th January, 2020
 */

class BoardActivity : AppCompatActivity(),
    BoardListener {

    var boardPresenter = BoardPresenter(this)
    private var boardAdapter: BoardAdapter? = null
    private val wordsAvailableAdapter by lazy { WordListAdapter(boardPresenter.allWords) }

    //Necessary for rotation
    private var activityBoardSelectedWord: TextView? = null
    private var viewKonfetti: KonfettiView? = null
    private var activityBoardGrid: RecyclerView? = null
    private var activityBoardAvailableWordsGrid: RecyclerView? = null
    private var activityBoardResetButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board_activity)

        boardAdapter = BoardAdapter(this, boardPresenter)
        linkBoard()
        reset()

        Log.d("DLSDIJALSDJ", "EITA " + boardPresenter.board[0][0].char)
    }

    private fun buildBoard(): ArrayList<ArrayList<BoardChararacter>> {
        if (boardPresenter.board.isNotEmpty()) return boardPresenter.board
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

    override fun updateSelectedWord(
        selectingWord: ArrayList<BoardChararacter>?,
        acceptedWord: Boolean
    ) {
        Log.d("BoardActivity", "selectingWord()")
        if (acceptedWord) {
            activityBoardSelectedWord?.animate()?.setInterpolator(DecelerateInterpolator())
                ?.scaleX(2f)
                ?.scaleY(2f)?.alpha(0f)?.withEndAction {
                    activityBoardSelectedWord?.text = ""
                    activityBoardSelectedWord?.alpha = 1f
                    activityBoardSelectedWord?.scaleX = 1f
                    activityBoardSelectedWord?.scaleY = 1f
                }?.start()
            return
        } else {
            var word = ""
            selectingWord?.forEach { word += it.char }
            activityBoardSelectedWord?.text = word
        }
    }

    override fun onVictory() {
        viewKonfetti?.build()?.apply {
            addColors(Color.YELLOW, Color.BLUE, Color.MAGENTA)
            setDirection(0.0, 359.0)
            setSpeed(1f, 5f)
            setFadeOutEnabled(true)
            setTimeToLive(1500L)
            addShapes(Shape.RECT, Shape.CIRCLE)
            addSizes(Size(12))
            setPosition(-50f, (viewKonfetti?.width ?: 0) + 50f, -50f, -50f)
            streamFor(300, 1500L)
            showDialog(true)
        }
    }

    private fun reset() {
        boardPresenter.reset()
        wordsAvailableAdapter.updateList(boardPresenter.allWords)
        boardAdapter?.updateGrid(boardPresenter.board)
    }


    private fun showDialog(victory: Boolean) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(if (victory) getString(R.string.dialogYouWinTitle) else "")
        dialog.setMessage(getString(R.string.dialogYouWinOrLooseDesc))
        dialog.setPositiveButton(getString(R.string.dialogYouWinButton)) { _, _ ->
            reset()
        }
        dialog.setCancelable(true)
        dialog.show()
    }

    override fun updateWordList(words: ArrayList<WordAvailable>) {
        wordsAvailableAdapter.updateList(words)
    }


    fun linkBoard(localPresenter: BoardPresenter? = null) {

        //necessary linking after rotation
        activityBoardSelectedWord = findViewById(R.id.activityBoardSelectedWord)
        viewKonfetti = findViewById(R.id.viewKonfetti)
        activityBoardGrid = findViewById(R.id.activityBoardGrid)
        activityBoardAvailableWordsGrid = findViewById(R.id.activityBoardAvailableWordsGrid)
        activityBoardResetButton = findViewById(R.id.activityBoardResetButton)

        activityBoardGrid?.adapter = boardAdapter
        (localPresenter ?: boardPresenter).board = buildBoard()
        boardAdapter?.updateGrid((localPresenter ?: boardPresenter).board)
        activityBoardAvailableWordsGrid?.adapter = wordsAvailableAdapter
        activityBoardResetButton?.setOnClickListener {
            it.animate().rotation(360f).setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction { it.rotation = 0f }.start()
            reset()
        }



        activityBoardAvailableWordsGrid?.adapter?.notifyDataSetChanged()
        activityBoardGrid?.adapter?.notifyDataSetChanged()
//
//        activityBoardAvailableWordsGrid?.layoutManager =
//            GridLayoutManager(this, resources.getInteger(R.integer.wordlist_spancount))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val localPresenter = boardPresenter


        boardPresenter = localPresenter
        Log.d("DLSDIJALSDJ", "EITA " + localPresenter.board[0][0].char)
        setContentView(R.layout.board_activity)
        Log.d("DLSDIJALSDJ", "EITA " + localPresenter.board[0][0].char)
//        activityBoardAvailableWordsGrid = findViewById(R.id.activityBoardAvailableWordsGrid)
        boardPresenter = localPresenter

        linkBoard()
        Log.d("DLSDIJALSDJ", "EITA " + localPresenter.board[0][0].char)
        boardPresenter = localPresenter
//        reset()
    }

}
