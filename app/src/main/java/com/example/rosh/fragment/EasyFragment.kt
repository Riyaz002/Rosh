package com.example.rosh.fragment

import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.rosh.R
import com.google.android.material.button.MaterialButton


/**
 * A simple [Fragment] subclass.
 * Use the [EasyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
enum class Button(val value: String) {
    START("START"),
    RESET("RESET")
}

class EasyFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var block1: ImageButton
    lateinit var block2: ImageButton
    lateinit var button: MaterialButton
    lateinit var timeElapsed: TextView
    lateinit var viewModel: EasyFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider.NewInstanceFactory().create(EasyFragmentViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_easy, container, false)

        //initialize view objects
        button = view.findViewById(R.id.btn_play)
        block1 = view.findViewById(R.id.square_1)
        block2 = view.findViewById(R.id.square_2)
        timeElapsed = view.findViewById(R.id.time_elapsed)
        val blockNuText = view.findViewById<TextView>(R.id.block_number)

        button.text = Button.START.value

        //set click listeners
        button.setOnClickListener {
            if (button.text == Button.START.value) {
                viewModel.startGame()
                button.text = Button.RESET.value

            } else if(button.text == Button.RESET.value) {
                button.text = Button.START.value
                resetGame()
            }
        }
        block1.setOnClickListener {
            if (viewModel.blockNumber.value == 1) {
                viewModel.stopTimer()
            }
        }
        block2.setOnClickListener {
            if (viewModel.blockNumber.value == 2) {
                viewModel.stopTimer()
            }
        }

        //Observers
        viewModel.timeElapsed.observe(viewLifecycleOwner, Observer {
            timeElapsed.text = it
        })
        viewModel.blockNumber.observe(viewLifecycleOwner, Observer {
            if(it!=0) {
                activateBlock(it)
                viewModel.startTimer()
                blockNuText.text = viewModel.blockNumber.value.toString()
            }
        })

        return view
    }

    private fun activateBlock(blockNumber: Int) {
        when (blockNumber) {
            1 -> block1.setBackgroundResource(R.drawable.active_square)
            2 -> block2.setBackgroundResource(R.drawable.active_square)
        }
    }

    private fun resetGame() {
        viewModel.reset()

        //resetting blocks color
        block1.setBackgroundResource(R.drawable.inactive_square)
        block2.setBackgroundResource(R.drawable.inactive_square)
    }

}