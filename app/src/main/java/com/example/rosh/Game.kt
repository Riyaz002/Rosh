package com.example.rosh

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import java.util.*

interface Game {
    val _timeElapsed: MutableLiveData<Long>
    val timerElapsed: LiveData<Long> get() = _timeElapsed
    val blockNumber: LiveData<Int>

    fun startGame()

    fun startTimer()

    fun stopTimer()

    fun reset()

    fun resetValues()

    fun destroy()
}