package ca.group6.meetmatcher.adapterClasses

import android.content.Context
import android.graphics.Color
import android.graphics.Color.rgb
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.fragments.AddTeamFragment
import ca.group6.meetmatcher.fragments.HomeFragment
import ca.group6.meetmatcher.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserAdapter(myContext : Context,
                  myUsers: List<User>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder?>(){

    private val myContext : Context
    private val myUsers : List<User>
    private val checkedUsers : ArrayList<User> = ArrayList()
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = Firebase.database
    private val myUid = HomeFragment.auth.currentUser!!.uid


    init {
        this.myContext = myContext
        this.myUsers = myUsers

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(myContext).inflate((R.layout.member_search_layout), viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = myUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user : User? = myUsers[position]
        holder.usernameText.text = user!!.getUsername()
        //holder.usernameText.isChecked = user!!.checkSelected()



        //Using arrayList to check
//        if (checkedList().contains(user)) {
//            holder.usernameText.isChecked
//        }
//        else {
//            !holder.usernameText.isChecked
//        }

        //list of users who's been checked, and check if current user is in that list

        holder.usernameText.setOnCheckedChangeListener { _, b ->
            if (holder.usernameText.isChecked) {
                holder.itemView.setBackgroundColor(rgb(20, 202, 184))
                user.isSelected(true)
                updateUser(user.getUsername().toString(),user.getUid().toString(), user.getStatus().toString(),true)
//                    .child("selected")
//                    .setValue(true)

                checkedUsers.add(user)
                Log.i("onBindViewHolder", "added checkedUsers")

            }
            else if (!holder.usernameText.isChecked) {
                holder.itemView.setBackgroundColor(rgb(3, 218, 197))
                user.isSelected(false)

                updateUser(user.getUsername().toString(),user.getUid().toString(), user.getStatus().toString(), false)
//                database.reference.child("Teams").child(user.getUid().toString())
//                    .child("selected")
//                    .setValue(false)

                if (checkedList().contains(user)) {
                    checkedUsers.remove(user)
                }
            }

        }

    }

    fun updateUser(username: String, uid : String, status: String, checked : Boolean) {
        database.reference.child("Teams").child(myUid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot != null) {
                        val HashMapOfAllThings = snapshot.value as HashMap<String, Any>

                        for (anyKey in HashMapOfAllThings.keys) {
                            val teamName = anyKey
                            var teamMap = mapOf("username" to username,
                                "status" to status,
                                "selected" to checked)
                            var childUpdate = HashMap<String, Any>()
                            childUpdate["/Teams/$myUid/${teamName}/$uid"] = teamMap
                            AddTeamFragment.database.reference.updateChildren(childUpdate)
                            //AddTeamFragment.database.reference.updateChildren("\"/Teams/$myUid/$teamName/${status = true}\"")
                        }
                    } else {
                        Log.i("null", "Can't retrieve team datasnapshot")
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

    }

    fun checkedList() :ArrayList<User> {
        return checkedUsers
    }

        class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
            var usernameText : CheckBox
            var onlineStatus : ImageView
            var offlineStatus : ImageView

            init {
                usernameText = itemview.findViewById(R.id.username)
                onlineStatus = itemview.findViewById(R.id.status_online)
                offlineStatus = itemview.findViewById(R.id.status_offline)
            }
        }




}