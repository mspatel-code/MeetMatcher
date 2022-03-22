package ca.group6.meetmatcher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.group6.meetmatcher.databinding.FragmentChangeNicknameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChangeNicknameFragment : Fragment() {
    private var _binding: FragmentChangeNicknameBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeNicknameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = FirebaseAuth.getInstance().currentUser

        database = Firebase.database.reference

        binding.submitChangeNickname.setOnClickListener {
            val nickname = binding.inputNewNickname.text.toString()

            if (nickname.isEmpty()) {
                Toast.makeText(activity, "Please enter nickname", Toast.LENGTH_LONG).show()
            } else {
                database.child("Users").child(user!!.uid).child("username").setValue(nickname)
                Toast.makeText(activity, "Updated!", Toast.LENGTH_LONG).show()
                transToProfile()
            }
        }
        binding.cancelChangeNickname.setOnClickListener {
            transToProfile()
        }
    }

    private fun transToProfile() {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.remove(this)
        transaction?.commit()
    }
}