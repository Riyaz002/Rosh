package com.example.rosh.fragment


import android.icu.text.SimpleDateFormat
import android.os.Build
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.rosh.Game
import com.example.rosh.GameImpl

const val NUMBER_OF_BLOCKS = 2
class EasyFragmentViewModel : ViewModel() {

    private var game: Game = GameImpl(NUMBER_OF_BLOCKS)

    @RequiresApi(Build.VERSION_CODES.O)
    val timeElapsed: LiveData<String> = Transformations.map(game.timerElapsed) {
        val formatter = SimpleDateFormat("mm:ss.SSS")
        DateUtils.formatElapsedTime(it).format(formatter)
    }

    val blockNumber: LiveData<Int> get() = game.blockNumber


    fun startGame() {
        game.startGame()
    }

    fun startTimer() {
        game.startTimer()
    }

    fun stopTimer() {
        game.stopTimer()
    }

    fun reset() {
        game.reset()
    }

    override fun onCleared() {
        super.onCleared()
        game.destroy()
    }
}
