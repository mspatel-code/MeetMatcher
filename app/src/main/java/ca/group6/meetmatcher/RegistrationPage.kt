package ca.group6.meetmatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ca.group6.meetmatcher.databinding.ActivityRegistrationPageBinding

class RegistrationPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SignIn2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.Register.setOnClickListener {
            val intent = Intent(this, Registration_Confirmation_Page::class.java)
            startActivity(intent)
        }
    }
}