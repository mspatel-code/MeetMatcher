package ca.group6.meetmatcher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.adapterClasses.EventAdapter
import ca.group6.meetmatcher.databinding.FragmentAvalBinding
import ca.group6.meetmatcher.model.Event

class AvalFragment : Fragment() {
    private var _binding: FragmentAvalBinding? = null
    private val binding get() = _binding!!

    private var events = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAvalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val calendar = binding.calendarView
        events.add(Event("00:00", "00:30", "meeting"))
        calendar.setOnDateChangeListener { c, i, j, k ->
            Toast.makeText(activity, "$k/$j/$i", Toast.LENGTH_LONG).show()
            val recyclerView = binding.dailyEvents
            recyclerView.layoutManager = LinearLayoutManager(activity)
            val adapter = EventAdapter(events)
            recyclerView.adapter = adapter
        }
    }
}