package ca.group6.meetmatcher.adapterClasses

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.model.User

class UserAdapter(myContext : Context,
                  myUsers: List<User>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder?>(){

    private val myContext : Context
    private val myUsers : List<User>


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

        holder.itemView.setOnClickListener {
            holder.usernameText.isChecked = !holder.usernameText.isChecked
            Log.i("Tag", "Checked")
            holder.itemView.setBackgroundColor(Color.argb(255, 255, 0, 0))
//            if (holder.usernameText.isChecked) {
//
//            }
        }


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