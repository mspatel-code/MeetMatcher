package ca.group6.meetmatcher.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.TeamPage
import kotlinx.android.synthetic.main.fragment_team_list_page.*

//import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_list_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        add_team_buttonTeamPage.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, AddTeamFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
        buttonTeamPage.setOnClickListener {
            val intent = Intent (activity, TeamPage::class.java)
            activity?.startActivity(intent)
        }
    }
}