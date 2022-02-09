package com.example.basehelpers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import at.grabner.circleprogress.Direction
import com.example.basehelpers.base.BaseBindingActivity
import com.example.basehelpers.data.repository.RoomRepository
import com.example.basehelpers.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private lateinit var countDownTimer: CountDownTimer

    @Inject
    lateinit var roomRepository: RoomRepository

    private lateinit var mBuilder: NotificationCompat.Builder
    private lateinit var mNotificationChannel: NotificationChannel
    private lateinit var mChannelId: String
    private lateinit var notificationManager: NotificationManager


    private var num = 0
    private var label = "Inserted$num"

    private val mainViewModel: MainViewModel by viewModels()

    private var remainingPercentage = 100f
    private var decreasePercentage = 10f
    private var counter = 0

    companion object {
        const val SIMPLE_CHANNEL_ID = "com.instasolv.instasolv-simple"
        const val SIMPLE_CHANNEL_NAME = "Reminder Notification"
        const val SIMPLE_CHANNEL_DESC = "Provide daily reminders in notification bar"
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun readArguments(extras: Intent) {
    }

    override fun setupUi() {
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "finished", Toast.LENGTH_SHORT).show()
            }

            override fun onTick(millisUntilFinished: Long) {
                updateProgress()
            }
        }.start()
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
        mainViewModel.someQueryLD.observe(this) {
            if (it != null) {
//                binding.queryTextTV.text=it.label
            }
        }
    }

    override fun setListener() {
        binding.buttonPanel.setOnClickListener {
            startTimer()

//            createChannel(SIMPLE_CHANNEL_ID, SIMPLE_CHANNEL_NAME, SIMPLE_CHANNEL_DESC,NotificationManager.IMPORTANCE_HIGH)

            addNotifiction()
        }
    }

    private fun addNotifiction() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createChannel(
            SIMPLE_CHANNEL_ID,
            SIMPLE_CHANNEL_NAME,
            SIMPLE_CHANNEL_DESC,
            NotificationManager.IMPORTANCE_HIGH
        )
        mBuilder = NotificationCompat.Builder(this, SIMPLE_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText("Notif Rev")
            .setSmallIcon(R.drawable.ic_app_icon_new)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)


        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("messeage", "First Notif")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(pendingIntent)
        notificationManager.notify(1, mBuilder.build())
        Log.d("CHECK_NOTIF", "addNotifiction: ")


    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }


    fun createChannel(
        id: String,
        name: String,
        desc: String,
        @SuppressLint("InlinedApi") importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ) {
        mChannelId = id
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationChannel = NotificationChannel(id, name, importance).apply {
                description = desc
            }
            notificationManager.createNotificationChannel(mNotificationChannel)
        }
    }


}