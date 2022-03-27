package ca.group6.meetmatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ca.group6.meetmatcher.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import java.time.format.SignStyle

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.SignUp.setOnClickListener {
            val intent = Intent(this, RegistrationPage::class.java)
            startActivity(intent)
        }

        binding.SignIn.setOnClickListener {
            userSingIn()
        }

    }

    private fun userSingIn() {
        val email: String = binding.email.text.toString()
        val password: String = binding.password.text.toString()

        var errorMsg = ""
        if (email.isEmpty()) {
            errorMsg = "Please enter username."
        }
        if (password.isEmpty()) {
            errorMsg = "Please enter password."
        }

        if (errorMsg.isNotEmpty()) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, ToolbarActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error: " + task.exception!!.toString(),
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}