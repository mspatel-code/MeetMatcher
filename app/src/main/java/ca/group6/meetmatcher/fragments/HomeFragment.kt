package ca.group6.meetmatcher.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.rgb
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.TeamPage
import ca.group6.meetmatcher.databinding.FragmentTeamListPageBinding
import ca.group6.meetmatcher.model.Team
import ca.group6.meetmatcher.model.User
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
        var statusList = ArrayList<String>()
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
        //memberList = arrayListOf("Member A", "Member B", "Member C", "Member D")


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
        Log.i("retrieveTeam", "Starting")
        val path = database.reference.child("Teams").child(myUid)
            if (path != null) {
                Log.i("retrieveTeam", "path is not null")
                path.addListenerForSingleValueEvent(object : ValueEventListener {

                        override fun onDataChange(p0: DataSnapshot) {
                            memberList.clear()
                            statusList.clear()
                            // val myMap = p0.value as HashMap<String, String>

//                        val myMap = p0.value as HashMap<String, String>
//                        val myKey = myMap.keys.first()

                            //Get the values from p0 into a hashmap
                            if (p0.value != null) {
                                val HashMapOfAllThings = p0.value as HashMap<String, Any>

                                //For each key in the hashmap
                                for (anyKey in HashMapOfAllThings.keys) {

                                    //Retrieve team name
                                    Log.i("retrieveTeam", anyKey)
                                    if (anyKey != null) {
                                        binding.teamTitle.text = anyKey
                                    } else {
                                        binding.teamTitle.text = "My Teams"
                                    }


                                    val one = HashMapOfAllThings.get(anyKey) as HashMap<String, Any>
                                    //Log.i("printHashMap", one.toString())

                                    for (keys in one.keys) {
                                        val two = one.get(keys) as HashMap<String, Any>
                                        //Log.i("Two", two["username"].toString())
                                        if (two["username"] != null) {
                                            memberList.add(two["username"].toString())
                                            statusList.add(two["status"].toString())
                                            binding.myRv.adapter!!.notifyDataSetChanged()
                                        } else {
                                            memberList.add("You do not have any team members.")
                                            binding.myRv.adapter!!.notifyDataSetChanged()
                                        }

                                    }
                                    //Log.i("printHashMap", one["username"].toString())
                                }
                            } else {
                                Log.i("null", "p0 is null")
                                binding.teamTitle.text = "My Teams"
                                memberList.add("You do not have any team members.")
                                binding.myRv.adapter!!.notifyDataSetChanged()
                            }

                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
            } else {
                Log.i("null", "path is null")
                binding.teamTitle.text = "My Teams"
                memberList = arrayListOf("You do not have any team members.")

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
            val online = holder.itemView.findViewById<ImageView>(R.id.status_online)
            val offline = holder.itemView.findViewById<ImageView>(R.id.status_offline)
            val usernameText = holder.itemView.findViewById<TextView>(R.id.teamName)

            if (memberList.contains("You do not have any team members.")) {
                online.visibility = View.GONE
                offline.visibility = View.GONE
                usernameText.setTextColor(rgb(150, 150, 150))

            } else {
                var currentStatus: String = statusList.get(position)
                if (currentStatus == "offline") {
                    online.visibility = View.GONE
                    offline.visibility = View.VISIBLE
                }
                if (currentStatus == "online") {
                    online.visibility = View.VISIBLE
                    offline.visibility = View.GONE
                }
                usernameText.setTextColor(Color.BLACK)

            }

        }

        override fun getItemCount() = array.size

    }

}
