package com.araujo.jordan.wordfindify.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun runAsync(asyncRunning: () -> Unit) =
    runBlocking { withContext(Dispatchers.IO) { asyncRunning() } }