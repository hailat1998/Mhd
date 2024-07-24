package com.hd.misaleawianegager.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.presentation.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


private const val CHANNEL_ID = "notification_channel"

@HiltWorker
class MisaleWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
//      workerTextService: WorkerTextService,
//     @ApplicationContext contextApp: Context
) : Worker(context, workerParams) {


    override  fun doWork(): Result {
        //  val text = workerTextService.readSingleText(applicationContext)
        Log.i("WORKER" , "doing work")
       showNotification(applicationContext, "text")
        return Result.success()
    }


    private fun showNotification(context: Context, message: String) {
        val notificationManager: NotificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "የቀኑ ሚሳለያዊ አነጋገር",
                NotificationManager.IMPORTANCE_HIGH // Set importance to high
            ).apply {
                description = "Channel for Misale notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("ሚሳለያዊ አነጋገር")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE) // Set the notification category
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Use all default notification settings
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

}