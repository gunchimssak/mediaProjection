package com.example.myapplication

import android.content.Intent
import android.hardware.display.VirtualDisplay
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myapplication.databinding.ActivityMainBinding

@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity() {
    private lateinit var myService: MyService
    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaProjection: MediaProjection
    private val mediaProjectionManager by lazy {
        getSystemService(MediaProjectionManager::class.java)
    }
    private lateinit var virtualDisplay: VirtualDisplay

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = mediaProjectionManager.createScreenCaptureIntent()
        myService = MyService()
        Log.e("intent", "$intent")
        startActivityForResult(intent, 1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            Log.e("requestCode", "$requestCode")
            val intent = Intent(this,myService::class.java)
            intent.putExtra("code", resultCode)
            Log.e("requestCode", "$data")
            intent.putExtra("data", data)

            val metrics = getResources().getDisplayMetrics();

            intent.putExtra("height", metrics.heightPixels)
            intent.putExtra("width", metrics.widthPixels)
            intent.putExtra("dpi", metrics.densityDpi)
            intent.putExtra("surface",binding.surface.holder.surface)
            startForegroundService(intent)
        }
    }
}