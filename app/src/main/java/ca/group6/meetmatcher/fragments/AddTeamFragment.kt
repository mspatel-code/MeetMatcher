package ca.group6.meetmatcher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.group6.meetmatcher.R
import kotlinx.android.synthetic.main.fragment_add_team.*
import kotlinx.android.synthetic.main.fragment_team_list_page.*

//import kotlinx.android.synthetic.main.fragment_home.*

class AddTeamFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // go back to home page (list of teams)
        form_team.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, HomeFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
    }
}