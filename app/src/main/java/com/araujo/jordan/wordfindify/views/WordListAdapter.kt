package com.araujo.jordan.wordfindify.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.models.WordAvailable
import kotlinx.android.synthetic.main.item_words.view.*

class WordListAdapter : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListAdapter.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: WordListAdapter.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_words, parent, false)) {

        val textView = itemView.itemText

        fun bind(word: WordAvailable) {

            textView.text = word.word

            WordAvailable("caludi")

        }

    }

}