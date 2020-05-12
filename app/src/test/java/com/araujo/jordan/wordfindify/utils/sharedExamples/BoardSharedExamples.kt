package com.araujo.jordan.wordfindify.utils.sharedExamples

import kotlin.random.Random

class BoardSharedExamples {
    companion object {
        fun randomString(stringLength: Int = Random.nextInt(3, 10)): String {
            val list = "abcdefghijklmnopqrstuvwxyz".toCharArray()
            var randomS = ""
            for (i in 1..stringLength) {
                randomS += list[getRandomNumber(0, list.size - 1)]
            }
            return randomS
        }

        fun getRandomNumber(min: Int, max: Int) = Random.nextInt((max - min) + 1) + min
    }
}