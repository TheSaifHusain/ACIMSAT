package com.altamash.acimsat.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.altamash.acimsat.R
import com.altamash.acimsat.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channel_Id="notification_channel"
const val channel_name="com.altamash.acimsat"
@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService()
{
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null)
        {
            //yaha Null Safe "!!" laga lena
            generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
        }
    }
    @SuppressLint("UnspecifiedImmutableFlag")
    fun generateNotification(title:String, message:String){
    //Making Intent for Click to move on that
    val intent= Intent(this,MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

    //Kyo ki ye intent bad me aay ga islie pending intent
    val pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)

    //notification Builder
    var builder:NotificationCompat.Builder=NotificationCompat.Builder(applicationContext, channel_Id)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setAutoCancel(true)
        .setVibrate(longArrayOf(1000,1000,1000,1000))
        .setContentIntent(pendingIntent)

    //ab jo Custom Notification layout banaya tha "notification_view" ko attach krege
    builder=builder.setContent(getRemoteView(title,message))

    //make Notification manager
    val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    //Android Oreo ke bad ye jhamela aya ki check karo
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
    {
        val notificationChannel=NotificationChannel(channel_Id, channel_name,NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(0,builder.build())
    }
}
    //Custom View ko set Karne ka function
    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String, message: String): RemoteViews {
     val remoteView=RemoteViews("com.altamash.acimsat.notification",R.layout.notification_view)
        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.description,message)
        remoteView.setImageViewResource(R.id.img,R.mipmap.ic_launcher)

        return remoteView
    }
}