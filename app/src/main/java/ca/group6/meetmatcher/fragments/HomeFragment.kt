package ca.group6.meetmatcher.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.TeamPage
import ca.group6.meetmatcher.databinding.FragmentTeamListPageBinding
import ca.group6.meetmatcher.model.Team
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

//import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    companion object {
        var auth: FirebaseAuth = FirebaseAuth.getInstance()
        var memberList = ArrayList<String>()
        public var myTeams : MutableList<String> = ArrayList()
        val database = Firebase.database
    }

    private var _binding: FragmentTeamListPageBinding? = null
    private val binding get() = _binding!!

    private val myUid = auth.currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myTeams = ArrayList()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamListPageBinding.inflate(inflater, container, false)
        val view = binding.root

        //Arraylist
        retrieveTeam()
        Log.i("Home", "Retrieved team")
        memberList = arrayListOf("Member A", "Member B", "Member C", "Member D")


        binding.myRv.layoutManager = LinearLayoutManager (activity as Context)
        binding.myRv.adapter = MyAdapter(memberList)
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

    fun retrieveTeam() {
        myTeams.add("Team B")

        for (each in myTeams) {
            database.reference.child(myUid).child("Teams").child(myUid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        Log.i("retrieveTeam", snapshot.getValue().toString())
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        }

    }



    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_team_list, parent, false))

    internal inner class MyAdapter(private val array:ArrayList<String>) :
        RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return MyViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            (holder.itemView as LinearLayout).findViewById<TextView>(R.id.teamName).text = array[position]

        }

        override fun getItemCount() = array.size

    }

}
