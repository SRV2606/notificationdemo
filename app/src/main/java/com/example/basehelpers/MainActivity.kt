package com.example.basehelpers

import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import at.grabner.circleprogress.Direction
import com.example.basehelpers.base.BaseBindingActivity
import com.example.basehelpers.data.repository.RoomRepository
import com.example.basehelpers.databinding.ActivityMainBinding
import com.example.basehelpers.util.NotificationUtil
import com.example.basehelpers.util.NotificationUtil.Companion.ACTION_PAUSE
import com.example.basehelpers.util.NotificationUtil.Companion.ACTION_STOP
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private lateinit var countDownTimer: CountDownTimer

    @Inject
    lateinit var roomRepository: RoomRepository

    @Inject
    lateinit var notificationUtil: NotificationUtil

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    private var num = 0
    private var label = "Inserted$num"

    private val mainViewModel: MainViewModel by viewModels()

    private var remainingPercentage = 100f
    private var decreasePercentage = 10f
    private var counter = 0

    companion object {

    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun readArguments(extras: Intent) {
    }

    override fun setupUi() {

    }

    fun startTimer(remainingValue: Float? = 100f) {
        if (remainingValue != null) {
            remainingPercentage = remainingValue
        }
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "finished", Toast.LENGTH_SHORT).show()
                remainingPercentage = 100f
                counter = 0
            }

            override fun onTick(millisUntilFinished: Long) {
                updateProgress()
            }
        }.start()
    }

    fun stopTimer(isPause: Boolean) {
        if (isPause) {
            stopAndSetTimerValue()
        } else {
            countDownTimer.cancel()
            remainingPercentage = 100f
            counter = 0
        }
    }

    private fun stopAndSetTimerValue() {
        sharedPreferenceUtil.setTimerValue(remainingPercentage)
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }

    private fun updateProgress() {
        val value = remainingPercentage - decreasePercentage
        binding.countdownCPV.setValueAnimated(value, 1000)
        binding.countdownCPV.setDirection(Direction.CCW)
        remainingPercentage = value
        counter++
        binding.counterTV.text = "$counter"
    }

    override fun observeData() {
    }

    private fun resumeTimer(it: Float?) {
        if (it != null && it < 100f) {
            startTimer(it)
        }
    }

    override fun setListener() {
        binding.buttonStart.setOnClickListener {
            startTimer(100f)
            notificationUtil.showTimerRunning(this, 0)
        }

        binding.buttonStop.setOnClickListener {
            stopTimer(false)
        }
    }

    override fun onResume() {
        super.onResume()
        if (::countDownTimer.isInitialized) {
            resumeTimer(sharedPreferenceUtil.getTimerValue())
        }
    }

    override fun onPause() {
        super.onPause()
        if (::countDownTimer.isInitialized) {
            stopTimer(true)
        }
    }

    override fun onStop() {
        super.onStop()
        if (::countDownTimer.isInitialized) {
            stopTimer(false)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        Log.d("CHECK_T", "onNewIntent: $intent")
        processIntentAction(intent)
        super.onNewIntent(intent)

    }

    private fun processIntentAction(intent: Intent?) {
        if (intent?.action != null) {
            when (intent.action) {
                ACTION_STOP -> {
                    Toast.makeText(this, "Stop :)", Toast.LENGTH_SHORT).show()
                }
                ACTION_PAUSE -> {
                    Toast.makeText(this, "Pause :|", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

}