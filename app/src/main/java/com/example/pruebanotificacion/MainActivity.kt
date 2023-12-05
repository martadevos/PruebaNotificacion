package com.example.pruebanotificacion

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivities
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.NonDisposableHandle
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn=findViewById<Button>(R.id.button)
        createChannel()
        btn.setOnClickListener{

            scheduleNotification()

        }
    }
private fun scheduleNotification(){
    val calendar= Calendar.getInstance()
    calendar.set(2023,11,5,2,15)
    val intent=Intent(applicationContext,AlarmNotification::class.java)
    val intentFilter = IntentFilter(Intent.ACTION_SCREEN_OFF)
    val pendingIntent = PendingIntent.getBroadcast(
        applicationContext,
        1,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val alarmManager=getSystemService(Context.ALARM_SERVICE) as AlarmManager
    Log.d(TAG,Calendar.getInstance().timeInMillis.toString())
    alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pendingIntent)

}

    fun createChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "myChannel",
                "MySupperChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply{
                description="Default_description"
            }
            val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            this
        }
    }




}