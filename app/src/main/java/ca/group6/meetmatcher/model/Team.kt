package ca.group6.meetmatcher.model

import ca.group6.meetmatcher.MainActivity
import ca.group6.meetmatcher.fragments.AddTeamFragment
import com.google.firebase.database.DatabaseReference

class Team(val teamName : String, val users: ArrayList<User>) {
    lateinit var meeting: Meeting
    var myTeam : String = teamName
    var myList : ArrayList<User> = users



}