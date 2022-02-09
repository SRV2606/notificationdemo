package com.example.basehelpers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NotificationActivity : AppCompatActivity() {

    companion object {
        const val SIMPLE_CHANNEL_ID = "com.instasolv.instasolv-simple"
        const val SIMPLE_CHANNEL_NAME = "Reminder Notification"
        const val SIMPLE_CHANNEL_DESC = "Provide daily reminders in notification bar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)


    }
}