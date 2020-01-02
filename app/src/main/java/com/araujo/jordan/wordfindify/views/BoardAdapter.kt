package com.araujo.jordan.wordfindify.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.models.BoardChararacter
import kotlinx.android.synthetic.main.item_board.view.*

class BoardAdapter(private val touchListener: View.OnTouchListener?) :
    RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    var grid = ArrayList<ArrayList<BoardChararacter>>()

    fun updateGrid(grid: ArrayList<ArrayList<BoardChararacter>>) {
        this.grid = grid
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context), parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(grid[position % 10][if (position < 10) 0 else position / 10], touchListener)

    override fun getItemCount(): Int = grid.size * grid.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_board, parent, false)) {

        val boardElement = itemView.boardItem

        val selectedColor by lazy {
            AppCompatResources.getColorStateList(
                itemView.context,
                R.color.selectCharColor
            )
        }
        val unselectedColor by lazy {
            AppCompatResources.getColorStateList(
                itemView.context,
                R.color.unselectCharColor
            )
        }

        fun bind(boardChararacter: BoardChararacter, touchListener: View.OnTouchListener?) {
            boardElement.text = boardChararacter.char
            boardElement.setTextColor(if (boardChararacter.selected) selectedColor else unselectedColor)

            if (itemView.tag != true) {
                itemView.setOnTouchListener(touchListener)
                itemView.tag = true
            }
        }
    }

}