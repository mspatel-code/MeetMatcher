package ca.group6.meetmatcher.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.adapterClasses.UserAdapter
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.databinding.FragmentAddTeamBinding
import ca.group6.meetmatcher.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class AddTeamFragment : Fragment() {
    private var _binding: FragmentAddTeamBinding? = null
    private val binding get() = _binding!!
    private var userAdapter : UserAdapter? = null
    private var myUsers : List<User>? = null
    private lateinit var recyclerView : RecyclerView
    private var enterMemberId : EditText? = null

    companion object {
        lateinit var auth: FirebaseAuth
        //lateinit var list : ArrayList<User>
        val database = Firebase.database
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTeamBinding.inflate(inflater, container, false)
        val view = binding.root

        enterMemberId = view.findViewById(R.id.enter_member_id)

        myUsers = ArrayList()


        recyclerView = binding.memberList
        recyclerView!!.setHasFixedSize(true)
        Log.i("TAG", "Layout manager")
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        Log.i("TAG", "Before instantiating useradapter")
        userAdapter = UserAdapter(requireContext(), myUsers!!)
        Log.i("TAG", "before recyclerview adapter = userAdapter")
        recyclerView!!.adapter = userAdapter
        Log.i("TAG", "after adapter = useradapter")
//        Log.i("TAG", "Layout manager")
//        binding.memberList!!.layoutManager = LinearLayoutManager(context)
//        Log.i("TAG", "Before useradapter")
//        binding.memberList!!.adapter = UserAdapter(requireContext(),myUsers!!)
//        Log.i("TAG", "after adapter = useradapter")
//        binding.memberList!!.setHasFixedSize(true)
//        Log.i("TAG", "list has fixed size")
        binding.memberList.visibility = View.GONE
        retrieveAllUsers()

            binding.enterMemberId!!.addTextChangedListener(object  : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    searchForUsers(cs.toString())
                    Log.i("TAG", "recyclerView is visible")
                    recyclerView!!.visibility = View.VISIBLE


                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

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

        var firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid
        var teamName : String = binding.enterTeamName.text.toString()
        database.getReference("Teams").child(firebaseUserID).child(teamName)
            .setValue(teamName)


        for (i in myUsers!!.indices) {
            Log.i("log", myUsers!![i].toString())
            var u = myUsers!!.get(i)
            if (u.checkSelected()) {
                var name = u.getUsername().toString()
                database.getReference("Teams").child(firebaseUserID).child(teamName).child(u.getUid().toString())
            .setValue(u)
            }
        }

        //Log.i("TAG", "Hi")

    }

    private fun retrieveAllUsers() {

        var firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val refUsers = FirebaseDatabase.getInstance().reference.child("Users").child((firebaseUserID))
        Log.i("TAG", "retrieved ID")

        refUsers.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {

                (myUsers as ArrayList<User>).clear()


                if (enterMemberId!!.text.toString() == "") {

                    for (snapshot in p0.children) {

                        val username : String? = snapshot.child("username").getValue(String::class.java)
                        val uid = snapshot.child("uid").getValue(String::class.java)
                        val status = snapshot.child("status").getValue(String::class.java)
                        val user : User? = User(username.toString(), uid.toString(), status.toString())

                        Log.i("refUsers", "get snapshot and make a user")
                        //Add all users from firebase except your own
                        if (!(user!!.getUid()).equals(firebaseUserID)) {
                            (myUsers as ArrayList<User>).add(user)
                            Log.i("refUsers", "add myUsers in arraylist")
                        }
                    }
                    userAdapter = UserAdapter(context!!, myUsers!!)
                    recyclerView!!.adapter = userAdapter

                    Log.i("refUsers", "Adapter added")
                }

            }


            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun searchForUsers(str: String) {
        var firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val queryUsers = FirebaseDatabase.getInstance().reference
            .child("Users").orderByChild("username")
            .startAt(str)
            .endAt(str + "\uf8ff")

        queryUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                (myUsers as ArrayList<User>).clear()

                for (snapshot in p0.children) {
                    val user: User? = snapshot.getValue(User::class.java)

                    //Add all users from firebase except your own
                    if (!(user!!.getUid()).equals(firebaseUserID)) {
                        (myUsers as ArrayList<User>).add(user)
                    }
                }
                userAdapter = UserAdapter(context!!, myUsers!!)
                binding.memberList!!.adapter = userAdapter
                //recyclerView!!.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}



