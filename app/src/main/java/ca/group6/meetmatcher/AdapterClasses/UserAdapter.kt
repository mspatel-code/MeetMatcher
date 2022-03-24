package ca.group6.meetmatcher.AdapterClasses

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.model.User

class UserAdapter(myContext : Context,
                  myUsers: List<User>, isChecked : Boolean)
    : RecyclerView.Adapter<UserAdapter.ViewHolder?>(){

    private val myContext : Context
    private val myUsers : List<User>
    private var isChecked : Boolean

    init {
        this.myContext = myContext
        this.myUsers = myUsers
        this.isChecked = isChecked
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(myContext).inflate((R.layout.member_search_layout), viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = myUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user : User? = myUsers[position]
        holder.usernameText.text = user!!.getUsername()

    }

        class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
            var usernameText : TextView
            var onlineStatus : ImageView
            var offlineStatus : ImageView

            init {
                usernameText = itemview.findViewById(R.id.username)
                onlineStatus = itemview.findViewById(R.id.status_online)
                offlineStatus = itemview.findViewById(R.id.status_offline)
            }
        }




}