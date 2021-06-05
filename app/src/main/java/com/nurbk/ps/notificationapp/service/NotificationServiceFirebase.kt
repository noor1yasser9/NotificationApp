package com.nurbk.ps.notificationapp.service

import android.util.Log

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.nurbk.ps.notificationapp.util.NotificationUtils

class NotificationServiceFirebase : FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM", "Token: $token")
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        val data = remoteMessage.data
        Log.e("tttttttttt", data.toString())
        NotificationUtils.createMainNotificationChannel(this)
        try {
            NotificationUtils.showBasicNotification(
                this,
                data["name"],
                data["content"],
                "",
                "0"
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun getUser(id: String, message: String, idN: String) {

    }

}