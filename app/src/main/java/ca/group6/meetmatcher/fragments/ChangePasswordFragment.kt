package ca.group6.meetmatcher.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.group6.meetmatcher.databinding.FragmentChangePasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = FirebaseAuth.getInstance().currentUser

        binding.submitChangePassword.setOnClickListener {
            val newPassword = binding.inputNewPassword.text.toString()
            val repeatNewPassword = binding.inputRepeatPassword.text.toString()

            var errorMsg = ""
            if (newPassword != repeatNewPassword) {
                errorMsg = "Please reconfirm your password."
            } else if (newPassword.isEmpty()) {
                errorMsg = "Please enter new password."
            }

            if (errorMsg.isNotEmpty()) {
                Toast.makeText(activity, errorMsg, Toast.LENGTH_LONG).show()
            } else {
                user!!.updatePassword(newPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity, "Updated!", Toast.LENGTH_LONG).show()
                        transToProfile()
                    } else {
                        Log.w(ContentValues.TAG, "ChangePassword:taskFail", task.exception)
                    }
                }
            }
        }

        binding.cancelChangePassword.setOnClickListener {
            transToProfile()
        }
    }

    private fun transToProfile() {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.remove(this)
        transaction?.commit()
    }
}