package com.araujo.jordan.wordfindify.presenter.requests


import android.util.Log
import com.araujo.jordan.wordfindify.models.WordAvailable
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

class DataMuseAPI {

    suspend fun getRandomWordList(subject: String, size: Int = 6): List<WordAvailable> =
        suspendCoroutine {
            try {
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
                it.resume(list)
            } catch (err: Exception) {
                err.printStackTrace()
            }
        }
}