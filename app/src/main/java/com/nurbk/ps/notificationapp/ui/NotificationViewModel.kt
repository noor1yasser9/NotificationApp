package com.nurbk.ps.notificationapp.ui

import com.nurbk.ps.notificationapp.NotificationParent
import com.nurbk.ps.notificationapp.reposioty.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(val notificationRepository: NotificationRepository) {


    val notificationPostGetLiveData get() = notificationRepository.notificationPostGetLiveData


    fun sendNotification(notification: NotificationParent) {
        notificationRepository.sendRemoteMessage(notification)
    }

}