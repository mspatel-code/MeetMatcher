package ca.group6.meetmatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ca.group6.meetmatcher.databinding.ActivityMainBinding
import java.time.format.SignStyle

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.SignUp.setOnClickListener {
            val intent = Intent(this, RegistrationPage::class.java)
            startActivity(intent)
        }

        binding.SignIn.setOnClickListener {
            val intent2 = Intent(this, ToolbarActivity::class.java)
            startActivity(intent2)
        }
    }

}