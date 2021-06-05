package com.nurbk.ps.notificationapp



data class NotificationParent(
    val data: NotificationData = NotificationData(),
    val to: String = ""
)

data class NotificationData(val name: String = "",
                            val content: String = "")