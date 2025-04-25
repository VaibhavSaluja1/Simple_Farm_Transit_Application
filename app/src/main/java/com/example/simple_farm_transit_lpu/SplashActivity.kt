package com.example.simple_farm_transit_lpu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.simple_farm_transit_lpu.R

class SplashActivity : AppCompatActivity() {
    
    private lateinit var logoImageView: ImageView
    private lateinit var appNameTextView: TextView
    private lateinit var progressBar: ProgressBar
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        // Hide the action bar  ? is used to check if the action bar is null and next function(hide method) is not called {NullPointer Exceptional} Prevents Crash.
        
        logoImageView = findViewById(R.id.iv_logo)
        appNameTextView = findViewById(R.id.tv_app_name)
        progressBar = findViewById(R.id.progress_bar)
        
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        
        appNameTextView.startAnimation(slideUp)
        
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
