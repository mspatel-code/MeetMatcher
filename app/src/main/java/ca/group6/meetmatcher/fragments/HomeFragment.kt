package ca.group6.meetmatcher.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.TeamPage
import ca.group6.meetmatcher.databinding.FragmentTeamListPageBinding

//import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private var _binding: FragmentTeamListPageBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamListPageBinding.inflate(inflater, container, false)
        val view = binding.root

        //Arraylist
        memberList = arrayListOf("Member A", "Member B,", "Member C", "Member D")


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
    companion object {
        var memberList = ArrayList<String>()
    }
}
