package com.nurbk.ps.notificationapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.google.firebase.messaging.FirebaseMessaging
import com.nurbk.ps.notificationapp.NotificationData
import com.nurbk.ps.notificationapp.NotificationParent
import com.nurbk.ps.notificationapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: NotificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                txtTokenUser.text = it.result.toString()
            }
        }

        btnUseToken.setOnClickListener {
            txtToken.setText(txtTokenUser.text.toString())
        }

        btnSend.setOnClickListener {
            val token = txtToken.text.toString()
            val message = txtMessage.text.toString()
            val name = txtName.text.toString()
            when {
                token.isNullOrEmpty() -> {
                    onCheckText(txtToken)
                }
                name.isNullOrEmpty() -> {
                    onCheckText(txtMessage)
                }
                message.isNullOrEmpty() -> {
                    onCheckText(txtName)
                }
                else -> {
                    viewModel.sendNotification(
                        NotificationParent(
                            data = NotificationData(
                                name = name, content = message
                            ), token
                        )
                    )
                    txtToken.text.clear()
                    txtMessage.text.clear()
                    txtName.text.clear()
                }
            }
        }

    }

    private fun onCheckText(editText: EditText) {
        editText.error = "this filed is required"
        editText.requestFocus()
    }
}