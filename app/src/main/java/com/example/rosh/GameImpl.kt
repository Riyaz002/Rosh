package com.example.rosh

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*

class GameImpl(val numbersOfBlock: Int) : Game {

    private var timerOn: Boolean = false
    private var gameState: Boolean = false
    private val gameScope = CoroutineScope(Dispatchers.Default)
    override val _timeElapsed = MutableLiveData<Long>()
    val _blockNumber = MutableLiveData<Int>()
    override val blockNumber: LiveData<Int>
        get() = _blockNumber

    override fun startGame() {
        gameState = true
        val random = Random()
        val rand = random.nextInt(numbersOfBlock) + 1
        gameScope.launch {
            Thread.sleep(3000)
            if(gameState){
                _blockNumber.postValue(rand)
            }
        }
    }

    override fun startTimer() {
        _timeElapsed.value = 0L
        timerOn = true
        var i = 0L;
        gameScope.launch {
            while(timerOn) {
                Thread.sleep(1)
                _timeElapsed.postValue(++i)
            }
        }
    }

    override fun stopTimer() {
        timerOn = false
    }

    override fun reset() {
        stopTimer()
        resetValues()
    }

    override fun resetValues() {
        gameState=false
        _timeElapsed.postValue(0L)
        _blockNumber.value = 0
    }

    override fun destroy() {
        gameScope.cancel()
    }
}