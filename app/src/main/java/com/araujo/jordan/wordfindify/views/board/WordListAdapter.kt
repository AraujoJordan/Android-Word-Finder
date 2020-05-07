package com.araujo.jordan.wordfindify.views.board

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.models.WordAvailable
import kotlinx.android.synthetic.main.item_words.view.*


/**
 * Designed and developed by Jordan Lira (@araujojordan)
 *
 * Copyright (C) 2020 Jordan Lira de Araujo Junior
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * KtList is a RecyclerView.Adapter implementation that make easier to implement hard stuffs like
 * HeaderView, EmptyView, InfiniteScroll and so on. It will also make it easy to implement the
 * adapter itself as you don't need to implement ViewHolders and others boilerplate methods won't
 * change in most of implementations.
 */
class WordListAdapter(val words: ArrayList<WordAvailable>) :
    RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context),
            parent
        )

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
                if (!word.didAnimation) {
                    val anim = ScaleAnimation(
                        0.0f, 1.0f, 1.0f, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                    )
                    anim.duration = 1000
                    itemStrikethough.startAnimation(anim)
                    word.didAnimation = true
                }
                itemStrikethough.alpha = 1.0f
                itemView.alpha = 0.5f
            } else {
                itemStrikethough.alpha = 0.0f
                itemView.alpha = 1f
            }
        }

    }

}