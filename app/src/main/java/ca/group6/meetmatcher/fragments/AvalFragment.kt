package ca.group6.meetmatcher.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.adapterClasses.EventAdapter
import ca.group6.meetmatcher.databinding.FragmentAvalBinding
import ca.group6.meetmatcher.model.Event
import ca.group6.meetmatcher.model.EventSchedule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class AvalFragment : Fragment() {
    private var _binding: FragmentAvalBinding? = null
    private val binding get() = _binding!!

    private var events = EventSchedule()
    private var calendar = Calendar.getInstance()
    private var currentTime = ""

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAvalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        database = Firebase.database.reference

        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)
        events.setEventDate(getDate(y, m, d))
        currentTime = "$y-$m-$d"
        readSchedule()
        setRecyclerList()

        val calendar = binding.calendarView
        calendar.setOnDateChangeListener { c, y, m, d ->
            events.setEventDate(getDate(y, m, d))
            currentTime = "$y-$m-$d"

            readSchedule()
        }
        binding.addEvent.setOnClickListener {
            addEvent()
        }
    }

    private fun readSchedule() {
        val ref = database.child("Schedules")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
        val listener = object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(currentTime)) {
                    events = snapshot.child(currentTime).getValue<EventSchedule>()!!
                } else {
                    events.setEventList(ArrayList())
                }
                setRecyclerList()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "readSchedule:onCancelled", error.toException())
            }
        }
        ref.addValueEventListener(listener)
    }

    private fun setRecyclerList() {
        val recyclerView = binding.dailyEvents
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = EventAdapter(events.getEventList())
        recyclerView.adapter = adapter
    }

    @SuppressLint("InflateParams")
    private fun addEvent() {
        val dialogBuilder = AlertDialog.Builder(activity)
        val popOutView = layoutInflater.inflate(R.layout.pop_out, null)

        val start = popOutView.findViewById<Button>(R.id.start_time)
        val end = popOutView.findViewById<Button>(R.id.end_time)

        val detail = popOutView.findViewById<TextView>(R.id.input_event_detail)

        val save = popOutView.findViewById<Button>(R.id.save_event)
        val cancel = popOutView.findViewById<Button>(R.id.cancel_create_event)

        dialogBuilder.setView(popOutView)
        val dialog = dialogBuilder.create()
        dialog.show()

        val startTime = events.getEventDate()
        start.setOnClickListener{
            val startTimeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                startTime.hours = hour
                startTime.minutes = minute
            }
            TimePickerDialog(requireContext(),startTimeSetListener,
                startTime.hours, startTime.minutes,false).show()
        }

        val endTime = events.getEventDate()
        end.setOnClickListener{
            val endTimeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                endTime.hours = hour
                endTime.minutes = minute
            }
            TimePickerDialog(requireContext(),endTimeSetListener,
                endTime.hours, endTime.minutes,false).show()
        }

        save.setOnClickListener {
            val newEvent = Event(startTime, endTime, detail.text.toString())
            events.addNewEvent(newEvent)
            writeSchedule()

            dialog.dismiss()
        }

        cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun writeSchedule() {
        database.child("Schedules")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(currentTime).setValue(events)
    }

    private fun getDate(y: Int, m:Int, d:Int): Date {
        return Date(y, m+1, d)
    }
}