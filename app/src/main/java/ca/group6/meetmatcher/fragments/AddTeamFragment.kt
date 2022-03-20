package ca.group6.meetmatcher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.databinding.FragmentAddTeamBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddTeamFragment : Fragment() {
    private var _binding: FragmentAddTeamBinding? = null
    private val binding get() = _binding!!

    companion object {
        lateinit var auth: FirebaseAuth
        const val memberName : String = ""
        const val memberId : String = ""

        val database = Firebase.database
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTeamBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.formTeam.setOnClickListener {
            writeTeamName()

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, HomeFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
    }

    fun writeTeamName() {
        var teamName : String = binding.enterTeamName.text.toString()
        database.getReference("Teams").child("team")
            .setValue(teamName)


    }

}