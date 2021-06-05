package com.nurbk.ps.notificationapp.util

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import com.nurbk.ps.notificationapp.ui.MainActivity

import com.nurbk.ps.notificationapp.R

object NotificationUtils {

    private val MAIN_CHANNEL_ID = "main_channel_id"

    fun createMainNotificationChannel(context: Context) {
        val soundUri =
            Uri.parse("android.resource://" + context.packageName + "/" + R.raw.notification)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = context.getString(R.string.main_channel)
            val channelDescription = context.getString(R.string.main_channel_description)
            val notificationChannel = NotificationChannel(
                MAIN_CHANNEL_ID, channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = channelDescription
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationChannel.setShowBadge(true)
            notificationChannel.vibrationPattern = longArrayOf(0, 500, 700, 900, 700, 500, 0)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            notificationChannel.setSound(soundUri, audioAttributes)
            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


    fun showBasicNotification(
        context: Context, name: String?,
        body: String?, image: String?, id: String?
    ) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("id", id)
        //        PendingIntent pendingIntent = TaskStackBuilder.create(context)
//                .addNextIntentWithParentStack(intent)
//                .getPendingIntent(0, 0);
        val stackBuilder = TaskStackBuilder.create(context)
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity::class.java)
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent)
        val resultPendingIntent = stackBuilder.getPendingIntent(
            System.currentTimeMillis().toInt(),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val soundUri =
            Uri.parse("android.resource://" + context.packageName + "/" + R.raw.notification)
        val builder = NotificationCompat.Builder(context, MAIN_CHANNEL_ID)
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
        builder.setContentTitle(name)
        builder.setContentText(body)
        builder.setTicker(name)
        builder.priority = NotificationCompat.PRIORITY_MAX
        builder.setVibrate(longArrayOf(0, 500, 700, 900, 700, 500, 0))
        builder.setSound(soundUri)
        builder.setContentIntent(resultPendingIntent)
        builder.setAutoCancel(true)
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            val pBuilder1 = Person.Builder()
            pBuilder1.setName(name)
            pBuilder1.setKey(id)
            val message = NotificationCompat.MessagingStyle.Message(
                body,
                System.currentTimeMillis(),
                pBuilder1.build()
            )
            builder.setStyle(
                NotificationCompat.MessagingStyle(pBuilder1.build())
                    .addMessage(message)
            )
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManagerCompat.notify(
                System.currentTimeMillis().toInt(),
                builder.build()
            )

    }


}