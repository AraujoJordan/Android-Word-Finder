package com.araujo.jordan.wordfindify.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.presenter.CharacterController
import com.araujo.jordan.wordfindify.utils.dragListener.DragSelectReceiver
import com.araujo.jordan.wordfindify.utils.dragListener.DragSelectTouchListener
import com.araujo.jordan.wordfindify.utils.dragListener.Mode
import kotlinx.android.synthetic.main.item_board.view.*

class BoardAdapter(context: Context, val controller: CharacterController) :
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
        ViewHolder(LayoutInflater.from(parent.context), parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(grid[position % 10][if (position < 10) 0 else position / 10])

    override fun getItemCount(): Int = grid.size * grid.size

    override fun isIndexSelectable(index: Int) = true
    override fun releaseSelection() {
        controller.checkForWord()
        notifyDataSetChanged()
        touchListener.setIsActive(true)
    }

    override fun isSelected(index: Int) =
        grid[index % 10][if (index < 10) 0 else index / 10].isOnSelection

    override fun setSelected(index: Int, selected: Boolean) {
        grid[index % 10][if (index < 10) 0 else index / 10].isOnSelection = true
        controller.addCharacter(grid[index % 10][if (index < 10) 0 else index / 10])
//        grid.forEach {
//            it.forEach { letter ->
//                letter.isOnSelection = false
//            }
//        }
//        controller.getCharactersBetween(controller.selectingWord.first(),controller.selectingWord.last())?.forEach {
//            it.isOnSelection = true
//        }
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

        fun bind(boardChararacter: BoardChararacter) {
            boardElement.text = boardChararacter.char
            if (boardChararacter.selected)
                boardElement.setTextColor(removedLetterColor)
            else
                boardElement.setTextColor(if (boardChararacter.isOnSelection) selectedColor else unselectedColor)

            if (itemView.tag == null) {
                itemView.tag = boardChararacter
            }
        }
    }

}