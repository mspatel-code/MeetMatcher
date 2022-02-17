package ca.group6.meetmatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.time.format.SignStyle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Sign_Up.setOnClickListener{
            val intent = Intent(this, RegistrationPage::class.java)
            startActivity(intent)
        }

        Sign_In.setOnClickListener{
            val intent2 = Intent(this, TeamsPage::class.java)
            startActivity(intent2)
        }
    }

}