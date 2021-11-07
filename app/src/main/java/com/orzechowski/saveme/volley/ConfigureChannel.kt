package com.orzechowski.saveme.volley

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.orzechowski.saveme.R

class ConfigureChannel
{
    fun configureNotificationChannel(activity: AppCompatActivity)
    {
        if (Build.VERSION.SDK_INT >= 26) {
            val channelId = activity.getString(R.string.default_notification_channel_id)
            val channelName = activity.getString(R.string.upload_channel_name)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.enableVibration(true)
            channel.description = activity.getString(R.string.upload_channel_description)
            val notificationManager = activity
                .getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
