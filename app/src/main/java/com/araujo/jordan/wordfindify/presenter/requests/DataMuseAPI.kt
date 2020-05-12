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
 */

package com.araujo.jordan.wordfindify.presenter.requests


import android.util.Log
import com.araujo.jordan.wordfindify.models.WordAvailable
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random

/**
 * Presenter that make the Network requests to the Datamuse API
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
class DataMuseAPI {

    /**
     * Make a OkHttp request to the Datamuse API to get a list of words based on given category
     * @param subject category of the word list to be searched
     * @param size size of the desired list
     */
    fun getRandomWordList(subject: String, size: Int = 6): List<WordAvailable> {
//        suspendCoroutine {

        val client = OkHttpClient()
        val request =
            Request.Builder().url("https://api.datamuse.com/words?ml=$subject").build()
        val jsonArrayString = client.newCall(request).execute().body?.string()
        val jsonArray = JSONArray(jsonArrayString)

        val list = mutableListOf<WordAvailable>()

        while (list.size < size) {
            val json = jsonArray.remove(Random.nextInt(jsonArray.length())) as? JSONObject
            val word = json?.getString("word") ?: "Fail"
            if (word.contains(' ') || word.contains('-') || word.length > 10) continue
            list.add(WordAvailable(word))
            Log.d("DataMuseAPI", "getRandomWordList() $word")
        }
        require(list.size == size)
        return list
    }
}