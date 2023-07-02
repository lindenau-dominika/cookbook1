package com.example.cookbook1

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

class TimerFragment : Fragment() {
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var remainingTimeTextView: TextView
    private lateinit var vibrator: Vibrator
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var editTimeEditText: EditText
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var cancelButton: Button
    private var remainingTime: Long = 0

    companion object {
        fun newInstance(): TimerFragment {
            return TimerFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_timer, container, false)
        return view
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            remainingTime = savedInstanceState.getLong("remainingTime")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("remainingTime", remainingTime)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        remainingTimeTextView = view.findViewById(R.id.remainingTimeTextView)
        editTimeEditText = view.findViewById(R.id.editTimeEditText)
        editTimeEditText.setText((remainingTime / 1000).toString())
        startButton = view.findViewById(R.id.startButton)
        stopButton = view.findViewById(R.id.stopButton)
        cancelButton = view.findViewById(R.id.cancelButton)

        vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.dzwiek)

        remainingTimeTextView.text = (remainingTime / 1000).toString()

        startButton.setOnClickListener {
            val enteredTime = editTimeEditText.text.toString().toLongOrNull()
            if (enteredTime != null && enteredTime > 0) {
                remainingTime = enteredTime * 1000
                startTimer(remainingTime)
            }
        }

        stopButton.setOnClickListener {
            stopTimer()
        }

        cancelButton.setOnClickListener {
            cancelTimer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vibrator.cancel()
        mediaPlayer.release()
    }

    private fun startTimer(totalTimeInMillis: Long) {
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }

        countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                val seconds = millisUntilFinished / 1000
                remainingTimeTextView.text = seconds.toString()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFinish() {
                playSoundAndVibrate()
                cancelTimer()
            }
        }

        countDownTimer.start()
        editTimeEditText.isEnabled = false
        startButton.isEnabled = false
        stopButton.isEnabled = true
        cancelButton.isEnabled = true
    }

    private fun stopTimer() {
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
            remainingTimeTextView.text = (remainingTime / 1000).toString()
            editTimeEditText.isEnabled = true
            editTimeEditText.setText((remainingTime / 1000).toString())
            startButton.isEnabled = true
            stopButton.isEnabled = false
            cancelButton.isEnabled = false
        }
    }

    private fun cancelTimer() {
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
            remainingTime = 60000
            remainingTimeTextView.text = ""
            editTimeEditText.isEnabled = true
            startButton.isEnabled = true
            stopButton.isEnabled = false
            cancelButton.isEnabled = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun playSoundAndVibrate() {
        mediaPlayer.start()
        if (vibrator.hasVibrator()) {
            val pattern = longArrayOf(0, 1000, 1000, 1000, 1000)
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        }
    }
}
