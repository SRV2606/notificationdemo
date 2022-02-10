package com.example.basehelpers.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.basehelpers.MainActivity
import com.example.basehelpers.R
import com.example.basehelpers.SharedPreferenceUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NotificationUtil @Inject constructor(
    private val sharedPreferenceUtil: SharedPreferenceUtil,
    @ApplicationContext private val context: Context
) {

    private lateinit var mBuilder: NotificationCompat.Builder
    private lateinit var mNotificationChannel: NotificationChannel
    private lateinit var mChannelId: String
    private lateinit var notificationManager: NotificationManager

    companion object {
        const val SIMPLE_CHANNEL_ID = "com.instasolv.instasolv-simple"
        const val SIMPLE_CHANNEL_NAME = "Reminder Notification"
        const val SIMPLE_CHANNEL_DESC = "Provide daily reminders in notification bar"
        const val ACTION_STOP = "stop"
        const val ACTION_PAUSE = "pause"
        const val ACTION_RESUME = "resume"
        const val ACTION_START = "start"
    }

    fun createSimpleNotification(title: String, desc: String): NotificationCompat.Builder {
        createChannel(
            SIMPLE_CHANNEL_ID,
            SIMPLE_CHANNEL_NAME,
            SIMPLE_CHANNEL_DESC,
            NotificationManagerCompat.IMPORTANCE_HIGH
        )
        mBuilder = NotificationCompat.Builder(context, SIMPLE_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(desc)
            .setSmallIcon(R.drawable.ic_app_icon_new)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(300L, 400L))
        return mBuilder
    }

    fun fireNotification() {
        notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, createSimpleNotification("Hey Sourav", "How Doin").build())
    }


    private fun <T> getPendingIntentWithStack(
        context: Context,
        javaClass: Class<T>
    ): PendingIntent {
        val resultIntent = Intent(context, javaClass)
        resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(javaClass)
        stackBuilder.addNextIntent(resultIntent)
        return stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    @Suppress("SameParameterValue")
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
            createNotificationChannel()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(mNotificationChannel)
        }
    }

    fun showTimerRunning(context: Context, wakeUpTime: Long) {
        val stopIntent = Intent(context, MainActivity::class.java)
        stopIntent.action = ACTION_STOP
        val stopPendingIntent = PendingIntent.getActivity(
            context,
            1, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val pauseIntent = Intent(context, MainActivity::class.java)
        pauseIntent.action = ACTION_PAUSE
        val pausePendingIntent = PendingIntent.getActivity(
            context,
            1, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            1, createSimpleNotification(
                "Timer Running",
                "Something"
            ).addAction(R.drawable.ic_app_icon_new, "STOP", stopPendingIntent)
                .addAction(R.drawable.ic_app_icon_new, "PAUSE", pausePendingIntent).build()
        )
    }

}