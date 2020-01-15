package com.araujo.jordan.wordfindify.views

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.models.WordAvailable
import com.araujo.jordan.wordfindify.utils.bounceAnimation
import kotlinx.android.synthetic.main.item_words.view.*

class WordListAdapter(val words: ArrayList<WordAvailable>) :
    RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context), parent)

    override fun getItemCount() = words.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(words[position])

    /**
     * Update the word list, without recreate the ArrayList
     * @param words the word list as exactly size of the one that initialized this class
     */
    fun updateList(words: ArrayList<WordAvailable>) {
        words.forEachIndexed { index, wordAvailable ->
            this.words[index].strikethrough = wordAvailable.strikethrough
        }
        notifyDataSetChanged()
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_words, parent, false)) {

        val textView = itemView.itemText
        val itemStrikethough = itemView.itemStrikethough

        fun bind(word: WordAvailable) {

            textView.text = word.word


            if (word.strikethrough) {
                itemStrikethough.alpha = 1.0f
                itemView.alpha = 0.5f
            } else {
                itemStrikethough.alpha = 0.0f
                itemView.alpha = 1f
            }
        }

    }

}