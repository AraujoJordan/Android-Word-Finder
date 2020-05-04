package com.araujo.jordan.wordfindify.views.board

import android.content.Context
import android.media.MediaPlayer
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.presenter.BoardPresenter
import com.araujo.jordan.wordfindify.utils.bounceAnimation
import com.araujo.jordan.wordfindify.utils.dragListener.DragSelectReceiver
import com.araujo.jordan.wordfindify.utils.dragListener.DragSelectTouchListener
import com.araujo.jordan.wordfindify.utils.dragListener.Mode
import kotlinx.android.synthetic.main.item_board.view.*

/**
 * Designed and developed by Jordan Lira (@AraujoJordan)
 *
 * 19th January, 2020
 */

class BoardAdapter(context: Context, private val presenter: BoardPresenter) :
    RecyclerView.Adapter<BoardAdapter.ViewHolder>(), DragSelectReceiver {

    var grid = ArrayList<ArrayList<BoardChararacter>>()
    val touchListener = DragSelectTouchListener.create(context, this) {
        disableAutoScroll()
        mode = Mode.PATH
    }

    fun updateGrid(grid: ArrayList<ArrayList<BoardChararacter>>) {
        this.grid = grid
        notifyDataSetChanged()
        touchListener.setIsActive(true)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnItemTouchListener(touchListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context),
            parent
        )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(grid[position % 10][if (position < 10) 0 else position / 10], grid)
    override fun getItemCount(): Int = grid.size * grid.size
    override fun isIndexSelectable(index: Int) = true

    override fun releaseSelection() {
        presenter.checkForWord()
        notifyDataSetChanged()
        touchListener.setIsActive(true)
    }

    override fun isSelected(index: Int) =
        grid[index % 10][if (index < 10) 0 else index / 10].isOnSelection

    override fun setSelected(index: Int, selected: Boolean) {
        grid[index % 10][index / 10].isOnSelection = true
        presenter.addCharacter(grid[index % 10][if (index < 10) 0 else index / 10])
        notifyItemChanged(index)
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_board, parent, false)) {

        val boardElement = itemView.boardItem

        val selectedColor by lazy {
            AppCompatResources.getColorStateList(
                itemView.context,
                R.color.colorPrimary
            )
        }
        val unselectedColor by lazy {
            AppCompatResources.getColorStateList(
                itemView.context,
                R.color.unselectCharColor
            )
        }
        val removedLetterColor by lazy {
            AppCompatResources.getColorStateList(
                itemView.context,
                R.color.colorAccent
            )
        }

        fun bind(boardChararacter: BoardChararacter, grid: ArrayList<ArrayList<BoardChararacter>>) {

            boardElement.text = boardChararacter.char

            if (boardChararacter.selected) {
                if (boardElement?.textColors != removedLetterColor) {
                    playNote(boardElement.context, grid)
                    boardElement.setTextColor(removedLetterColor)
                    bounceAnimation(boardElement)
                }
            } else {
                if (boardChararacter.isOnSelection) {
                    if (boardElement.textColors != selectedColor) {
                        playNote(boardElement.context, grid)
                        boardElement.setTextColor(selectedColor)
                        boardElement.isHapticFeedbackEnabled = true
                        boardElement.performHapticFeedback(
                            HapticFeedbackConstants.LONG_PRESS,
                            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                        )

                        bounceAnimation(boardElement)
                    }
                } else {
                    boardElement.setTextColor(unselectedColor)
                }
            }

            if (itemView.tag == null) {
                itemView.tag = boardChararacter
            }
        }


        fun playNote(ctx: Context?, grid: ArrayList<ArrayList<BoardChararacter>>) {

            if (grid.isEmpty() || ctx == null) return

            var count = 0
            grid.forEach { it.forEach { count += if (it.isOnSelection) 1 else 0 } }

            MediaPlayer.create(
                ctx, when (count) {
                    1 -> R.raw.a3
                    2 -> R.raw.c3
                    3 -> R.raw.d3
                    4 -> R.raw.e3
                    5 -> R.raw.f3
                    6 -> R.raw.g3
                    7 -> R.raw.a4
                    8 -> R.raw.c4
                    9 -> R.raw.d4
                    10 -> R.raw.e4
                    11 -> R.raw.f4
                    12 -> R.raw.g4
                    13 -> R.raw.a5
                    14 -> R.raw.c5
                    15 -> R.raw.d5
                    16 -> R.raw.e5
                    17 -> R.raw.f5
                    else -> R.raw.g5
                }
            ).apply {
                setOnPreparedListener { mediaToPlay ->
                    mediaToPlay.setVolume(1f, 1f)
                    mediaToPlay.setOnCompletionListener { mediaForRelease ->
                        mediaForRelease.release()
                    }
                    mediaToPlay.start()
                }
            }
        }
    }

}