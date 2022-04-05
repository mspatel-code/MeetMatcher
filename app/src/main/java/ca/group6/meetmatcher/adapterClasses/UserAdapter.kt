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


        //holder.usernameText.isChecked =checkSelected(user.getUid().toString())
        //Log.i("checked state", checkSelected(user.getUid().toString()).toString())


        database.reference.child("Teams").child(myUid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null) {
                        val HashMapOfAllThings = snapshot.value as HashMap<String, Any?>

                        //anyKey = team name
                        for (anyKey in HashMapOfAllThings.keys) {
                            val value = snapshot.child(anyKey).child(user.getUid().toString()).child("selected").value

                            if (value != null) {
                                if (value.toString() == "true") {
                                    holder.usernameText.isChecked = true
                                    Log.i(user.getUsername().toString(), value.toString())
                                } else {
                                    holder.usernameText.isChecked = false
                                }
                            } else {
                                holder.usernameText.isChecked = false
                            }


                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        holder.usernameText.setOnCheckedChangeListener { _, b ->
            if (holder.usernameText.isChecked) {
                holder.itemView.setBackgroundColor(rgb(20, 202, 184))
                user.isSelected(true)
                updateUser(user.getUsername().toString(),user.getUid().toString(), user.getStatus().toString(),true)


            }
            else if (!holder.usernameText.isChecked) {
                holder.itemView.setBackgroundColor(rgb(48,236,212))
                user.isSelected(false)

                updateUser(user.getUsername().toString(),user.getUid().toString(), user.getStatus().toString(), false)

            }

        }

    }

    fun updateUser(username: String, uid : String, status: String, checked : Boolean) {
        database.reference.child("Teams").child(myUid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null) {
                        Log.i("updateUser", "snapshot value is not null")
                        val HashMapOfAllThings = snapshot.value as HashMap<String, Any?>

                        for (anyKey in HashMapOfAllThings.keys) {
                            val teamName = anyKey
                            var teamMap = mapOf("username" to username,
                                "status" to status,
                                "selected" to checked)
                            var childUpdate = HashMap<String, Any?>()

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