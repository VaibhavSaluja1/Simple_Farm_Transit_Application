package com.example.simple_farm_transit_lpu

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerLink = findViewById<TextView>(R.id.registerLink)

        loginButton.setOnClickListener {
            val phone = phoneInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (phone.length != 10 || password.length < 6) {
                Toast.makeText(this, "Enter valid phone and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usersRef = database.reference.child("users")
            usersRef.orderByChild("phone").equalTo(phone)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            var found = false
                            for (userSnap in snapshot.children) {
                                val storedPassword = userSnap.child("password").getValue(String::class.java)
                                if (storedPassword == password) {
                                    val email = userSnap.child("email").getValue(String::class.java)
                                    if (email != null) {
                                        // Sign in with Firebase Auth
                                        auth.signInWithEmailAndPassword(email, password)
                                            .addOnSuccessListener {
                                                Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                                                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                                                finish()
                                            }
                                            .addOnFailureListener { e ->
                                                Toast.makeText(this@LoginActivity, "Authentication failed: ${e.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        found = true
                                        break
                                    }
                                }
                            }
                            if (!found) {
                                Toast.makeText(this@LoginActivity, "Incorrect password", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@LoginActivity, "No account found with this phone number", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@LoginActivity, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}
