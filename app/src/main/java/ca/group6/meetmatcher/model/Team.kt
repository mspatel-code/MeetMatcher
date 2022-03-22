package ca.group6.meetmatcher.model

import ca.group6.meetmatcher.MainActivity
import ca.group6.meetmatcher.fragments.AddTeamFragment
import com.google.firebase.database.DatabaseReference

class Team(val users: ArrayList<User>) {
    lateinit var meeting: Meeting
    lateinit var teamName : String
    var myList : ArrayList<User> = users



}