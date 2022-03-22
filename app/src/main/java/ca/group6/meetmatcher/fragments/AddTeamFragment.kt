package ca.group6.meetmatcher.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.databinding.FragmentAddTeamBinding
import ca.group6.meetmatcher.model.Team
import ca.group6.meetmatcher.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddTeamFragment : Fragment() {
    private var _binding: FragmentAddTeamBinding? = null
    private val binding get() = _binding!!

    companion object {
        lateinit var auth: FirebaseAuth
        lateinit var list : ArrayList<User>
        val database = Firebase.database
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        //getUsers()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTeamBinding.inflate(inflater, container, false)
        val view = binding.root

            list = arrayListOf(User("", "Dummy member", "offline"))
//        binding.memberList.layoutManager = LinearLayoutManager (activity as Context)
//        binding.memberList.adapter = MyAdapter(list)

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
        val team = Team(list)
        team.myList = list

        var teamName : String = binding.enterTeamName.text.toString()
        database.getReference("Teams").child(teamName)
            .setValue(teamName)
            //team.addTeam()
        Log.i("TAG", "Hi")

    }

//    fun getUsers() {
//
//
//        val myUid : String = auth.currentUser!!.uid
//        val myListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                binding.testVew.text = snapshot.value.toString()


//
//                val readValue : Map<String, Any> = snapshot.value as Map<String, Any>
//                val username = readValue["username"]
//                val uid = readValue["uid"]
//                val status = readValue["status"]
//
//                val fetchedUser = User(username.toString(), uid.toString(), status.toString())
//            }

//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        }
//
//        database.reference.child("Users").child(myUid)
//            .addListenerForSingleValueEvent(myListener)

//
//    }

//    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
//        RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_add_team, parent, false))
//
//    internal inner class MyAdapter(private val array:ArrayList<String>) :
//        RecyclerView.Adapter<MyViewHolder>() {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//            val inflater = LayoutInflater.from(parent.context)
//            return MyViewHolder(inflater, parent)
//        }
//
//        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//            (holder.itemView as LinearLayout).text = array[position]
//
//
//        }
//
//        override fun getItemCount() = array.size
//
//    }

}

