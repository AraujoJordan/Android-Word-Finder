package com.araujo.jordan.wordfindify.views.board

//import kotlinx.android.synthetic.main.board_activity.*
import android.content.res.Configuration
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.models.WordAvailable
import com.araujo.jordan.wordfindify.presenter.board.BoardListener
import com.araujo.jordan.wordfindify.presenter.board.BoardPresenter
import com.araujo.jordan.wordfindify.presenter.level.LevelBuilder
import com.araujo.jordan.wordfindify.presenter.storage.StorageUtils
import com.araujo.jordan.wordfindify.utils.hideSystemUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import java.util.concurrent.TimeUnit


/**
 * Designed and developed by Jordan Lira (@AraujoJordan)
 *
 * 19th January, 2020
 */

class BoardActivity : AppCompatActivity(),
    BoardListener {

    var boardPresenter =
        BoardPresenter(this)
    private var boardAdapter: BoardAdapter? = null
    private val wordsAvailableAdapter by lazy {
        WordListAdapter(
            boardPresenter.allWords
        )
    }

    private var countDownTimer: CountDownTimer? = null
    private var countDownMil = 300000L

    //Necessary for rotation
    private var activityBoardSelectedWord: TextView? = null
    private var viewKonfetti: KonfettiView? = null
    private var activityBoardGrid: RecyclerView? = null
    private var activityBoardAvailableWordsGrid: RecyclerView? = null
    private var activityBoardResetButton: ImageView? = null
    private var activityBoardTimer: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board_activity)

        boardAdapter = BoardAdapter(
            this,
            boardPresenter
        )
        linkBoard()
        reset()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI(window)
    }

    override fun onDestroy() {
        super.onDestroy()

        //nulling views reference to avoid memory leak
        activityBoardSelectedWord = null
        viewKonfetti = null
        activityBoardGrid = null
        activityBoardAvailableWordsGrid = null
        activityBoardResetButton = null
        activityBoardTimer = null
        countDownTimer?.cancel()
        countDownTimer = null
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

        if (selectingWord?.size ?: 20 > 10) {
            activityBoardSelectedWord?.text = ""
            return
        }

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

        val storage = StorageUtils()
        val player = storage.getPlayer(this)
        if (player.level < LevelBuilder().getLevels().size) {
            ++player.level
            storage.savePlayer(this, player)
        }

        viewKonfetti?.build()?.apply {
            addColors(Color.YELLOW, Color.BLUE, Color.MAGENTA)
            setDirection(0.0, 359.0)
            setSpeed(1f, 5f)
            setFadeOutEnabled(true)
            setTimeToLive(1500L)
            addShapes(Shape.Square, Shape.Circle)
            addSizes(Size(12))
            setPosition(-50f, (viewKonfetti?.width ?: 0) + 50f, -50f, -50f)
            streamFor(300, 1500L)
            showDialog(true)
        }
    }

    fun onLose() {
        showDialog(false)
    }

    fun reset() {
        GlobalScope.launch(Dispatchers.IO) {
            boardPresenter.reset(
                LevelBuilder().getCategory(
                    intent.getIntExtra("level", 1)
                )
            )
            Log.d("BoardActivity", "boardPresenter.allWords: " + boardPresenter.allWords.toString())
            updateBoard()
        }
    }

    private fun updateBoard() = CoroutineScope(Dispatchers.Main.immediate).launch {
        wordsAvailableAdapter.updateList(boardPresenter.allWords)
        boardAdapter?.updateGrid(boardPresenter.board)


        countDownMil = when (intent.getStringExtra("difficulty")) {
            "easy" -> 300000L
            "medium" -> 150000L
            "hard" -> 120000L
            else -> 300000L
        }

        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(countDownMil, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownMil -= 1000
                activityBoardTimer?.text = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                )
            }

            override fun onFinish() {
                if (boardPresenter.allWords.count { !it.strikethrough } > 0)
                    onLose()
            }
        }
        countDownTimer?.start()
    }


    private fun showDialog(victory: Boolean) {
        WinLoseDialog(this, victory)
    }

    override fun updateWordList(words: ArrayList<WordAvailable>) {
//        if (words.count { it.strikethrough } > wordsAvailableAdapter.words.count { it.strikethrough }) {

        MediaPlayer.create(this, R.raw.levelup).apply {
            setOnPreparedListener {
                setVolume(1f, 1f)
                it.start()
            }
        }
//        }
        wordsAvailableAdapter.updateList(words)
    }


    fun linkBoard(localPresenter: BoardPresenter? = null) {

        //necessary linking after rotation
        activityBoardSelectedWord = findViewById(R.id.activityBoardSelectedWord)
        viewKonfetti = findViewById(R.id.viewKonfetti)
        activityBoardGrid = findViewById(R.id.activityBoardGrid)
        activityBoardAvailableWordsGrid = findViewById(R.id.activityBoardAvailableWordsGrid)
        activityBoardResetButton = findViewById(R.id.activityBoardResetButton)
        activityBoardTimer = findViewById(R.id.activityBoardTimer)

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
        setContentView(R.layout.board_activity)
        boardPresenter = localPresenter

        linkBoard()
        boardPresenter = localPresenter
        hideSystemUI(window)
    }

}
