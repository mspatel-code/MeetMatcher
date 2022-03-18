package ca.group6.meetmatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ca.group6.meetmatcher.databinding.ActivityRegistrationConfirmationPageBinding

class RegistrationConfirmationPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationConfirmationPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationConfirmationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SignIn3.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}