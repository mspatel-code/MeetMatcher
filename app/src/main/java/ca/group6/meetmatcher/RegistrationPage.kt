package ca.group6.meetmatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ca.group6.meetmatcher.databinding.ActivityRegistrationPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationPageBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var userRef: DatabaseReference
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.SignIn2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.Register.setOnClickListener {
            registerInfo()
        }
    }

    private fun registerInfo() {
        val email: String = binding.EnterEmail.text.toString()
        val password: String = binding.EnterPassword.text.toString()
        val confirmPassword: String = binding.ConfirmPassword.text.toString()

        var errorMsg = ""
        if (email.isEmpty()) {
            errorMsg = "Please enter username."
        }
        if (password.isEmpty()) {
            errorMsg = "Please enter password."
        }
        if (confirmPassword != password) {
            errorMsg = "Please reconfirm your password."
        }

        if (errorMsg.isNotEmpty()) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userId = auth.currentUser!!.uid
                    userRef = FirebaseDatabase.getInstance().reference.child(userId)

                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = userId
                    userHashMap["username"] = email
                    userHashMap["image"] = R.drawable.ic_baseline_person_24
                    userHashMap["status"] = "offline"

                    userRef.updateChildren(userHashMap).addOnCompleteListener { dataTask ->
                        if (dataTask.isSuccessful) {
                            val intent = Intent(this, Registration_Confirmation_Page::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(this, "Error: " + task.exception!!.toString(),
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}