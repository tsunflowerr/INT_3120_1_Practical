package com.example.waterme.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import android.Manifest
import com.example.waterme.CHANNEL_ID
import com.example.waterme.MainActivity
import com.example.waterme.NOTIFICATION_ID
import com.example.waterme.NOTIFICATION_TITLE
import com.example.waterme.R
import com.example.waterme.REQUEST_CODE
import com.example.waterme.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.waterme.VERBOSE_NOTIFICATION_CHANNEL_NAME

fun makePlantReminderNotification(
    message: String,
    context: Context
) {
    android.util.Log.d("WaterUtils", "Making notification with message: $message")

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(
            CHANNEL_ID,
            VERBOSE_NOTIFICATION_CHANNEL_NAME,
            importance
        )
        channel.description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
        android.util.Log.d("WaterUtils", "Notification channel created: $CHANNEL_ID")
    }

    val pendingIntent: PendingIntent = createPendingIntent(context)

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    // From Android 13 (Tiramisu), POST_NOTIFICATIONS is a runtime permission
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        android.util.Log.d("WaterUtils", "Permission granted, showing notification")
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    } else {
        android.util.Log.w("WaterUtils", "Notification permission denied")
    }
}

fun createPendingIntent(appContext: Context): PendingIntent {
    val intent = Intent(appContext, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    // Use immutable flag on Android M and higher
    val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

    return PendingIntent.getActivity(
        appContext,
        REQUEST_CODE,
        intent,
        flags
    )
}