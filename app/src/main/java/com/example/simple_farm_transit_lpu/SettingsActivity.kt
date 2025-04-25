package com.example.simple_farm_transit_lpu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<Button>(R.id.notificationButton).setOnClickListener {
            NotificationHelper(this).apply {
                createNotificationChannel()
                showSampleNotification()
            }
            Toast.makeText(this, "Notification sent", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.title = "Settings"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
