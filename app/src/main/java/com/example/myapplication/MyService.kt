package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Surface
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MyService : Service() {
    private lateinit var windowViewLayoutParams: WindowManager.LayoutParams
    private lateinit var binding: ActivityMainBinding
    override fun onBind(intent: Intent?): IBinder? = null
    lateinit var projectionManager: MediaProjectionManager
    lateinit var projection: MediaProjection
    lateinit var data: Intent
    var code = 1
    var height = 2285
    var width = 1180
    var dpi = 450
    var surface : Surface? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        data = intent?.getParcelableExtra("data")!!
        code = intent.getIntExtra("code", 114)
        height = intent.getIntExtra("height", 114)
        width = intent.getIntExtra("width", 114)
        dpi = intent.getIntExtra("dpi", 114)
        surface = intent?.getParcelableExtra("surface")
        Log.e("intentExtra", "${data} ${code} ${height} ${width} ${dpi} ${surface}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationManager =
                application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel("123", "123", NotificationManager.IMPORTANCE_DEFAULT)

            notificationManager.createNotificationChannel(channel)

            val notification = NotificationCompat.Builder(applicationContext, "123")
                .setContentText("123")
                .setContentTitle("123")
                .build()

            startForeground(1, notification)

        }
        startProjection()
        return START_STICKY
    }

    fun startProjection() {
        Log.e("startProjection", "startProjection")
        projectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        projection = projectionManager.getMediaProjection(code,data)
        projection.createVirtualDisplay(
            "recode",
            400,
            400,
            550,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
            surface,
            null,
            null
        )

    }
}