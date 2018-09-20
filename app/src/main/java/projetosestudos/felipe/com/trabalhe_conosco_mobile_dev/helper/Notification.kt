package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.helper

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.support.v4.app.NotificationCompat
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.MainActivity

class Notification(context: Context, contentText: String) {

    private var mContext: Context? = null
    private var mNotificationManager: NotificationManager? = null
    private val NOTIFICATION_ID: Int = 0
    private var mContentText: String? = null

    init {
        mContext = context
        mContentText = contentText
    }

    fun createNotification() {

        val intent = Intent(mContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(mContext, NOTIFICATION_ID, intent, PendingIntent.FLAG_ONE_SHOT)

        mNotificationManager = mContext!!.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(mContext).
                setContentTitle("PicPay").
                setContentText(mContentText).
                setSmallIcon(R.drawable.ic_notification).
                setContentIntent(pendingIntent).
                setPriority(NotificationCompat.PRIORITY_HIGH).
                setDefaults(NotificationCompat.DEFAULT_ALL).
                setStyle(NotificationCompat.BigTextStyle().bigText(mContentText)).
                setAutoCancel(true)

        val myNotification = notificationBuilder.build()
        mNotificationManager!!.notify(NOTIFICATION_ID, myNotification)

    }

}