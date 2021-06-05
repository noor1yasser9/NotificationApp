package com.nurbk.ps.notificationapp.reposioty

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nurbk.ps.notificationapp.NotificationParent
import com.nurbk.ps.notificationapp.network.NotificationInterface
import com.nurbk.ps.notificationapp.other.ResultRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    val notificationInterface: NotificationInterface,
) {
    private val notificationMutableLiveData: MutableLiveData<ResultRequest<Any>> = MutableLiveData()


    fun sendRemoteMessage(notification: NotificationParent) {
        CoroutineScope(Dispatchers.IO).launch {
            notificationMutableLiveData.postValue(ResultRequest.loading("loading"))
            val response = notificationInterface.sendRemoteMessage(notification)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            notificationMutableLiveData.postValue(ResultRequest.success(it))
                            Log.e("tttttttisSuccessful", it.toString())
                        }

                    } else {
                        Log.e(
                            "tttttttsdfsdfsdf",
                            response.errorBody()?.charStream()?.readLines().toString()
                        )
                        notificationMutableLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("ttttttt", e.message().toString())
                    notificationMutableLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("ttttttt", t.message.toString())
                    notificationMutableLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }


    val notificationPostGetLiveData: LiveData<ResultRequest<Any>> = notificationMutableLiveData

}