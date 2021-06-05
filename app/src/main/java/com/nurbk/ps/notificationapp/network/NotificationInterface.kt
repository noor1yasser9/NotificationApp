package com.nurbk.ps.notificationapp.network


import com.nurbk.ps.notificationapp.NotificationParent
import com.nurbk.ps.notificationapp.other.AUTH_VALUE
import com.nurbk.ps.notificationapp.other.VALUE_TYPE
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationInterface {

    @Headers(
        "Authorization: key=${AUTH_VALUE}",
        "Content-Type:${VALUE_TYPE}"
    )
    @POST("send")
    suspend fun sendRemoteMessage(
        @Body notification: NotificationParent
    ): Response<ResponseBody>

}