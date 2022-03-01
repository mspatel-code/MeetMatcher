package ca.group6.meetmatcher.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.TeamPage
import ca.group6.meetmatcher.databinding.FragmentTeamListPageBinding

//import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private var _binding: FragmentTeamListPageBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamListPageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addTeamButtonTeamPage.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, AddTeamFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
        binding.buttonTeamPage.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, TeamPage(), "teamPageTag")
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
    }
}