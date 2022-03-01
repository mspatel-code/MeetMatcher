package ca.group6.meetmatcher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.databinding.FragmentAddTeamBinding

//import kotlinx.android.synthetic.main.fragment_home.*

class AddTeamFragment : Fragment() {
    private var _binding: FragmentAddTeamBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTeamBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // go back to home page (list of teams)
        binding.formTeam.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, HomeFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
    }
}