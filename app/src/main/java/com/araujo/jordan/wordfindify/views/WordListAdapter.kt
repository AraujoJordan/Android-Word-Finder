package com.araujo.jordan.wordfindify.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.models.WordAvailable
import kotlinx.android.synthetic.main.item_words.view.*

/**
 * Designed and developed by Jordan Lira (@AraujoJordan)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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