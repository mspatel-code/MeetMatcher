package ca.group6.meetmatcher.adapterClasses

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.model.Event
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class EventAdapter(private val events: ArrayList<Event>):
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var eventStartTime: TextView
        var eventEndTime: TextView
        var eventDetail: TextView

        init {
            eventStartTime = view.findViewById(R.id.event_start_time)
            eventEndTime = view.findViewById(R.id.event_end_time)
            eventDetail = view.findViewById(R.id.event_detail)

            view.setOnClickListener {
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.event_card, viewGroup, false)
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.eventStartTime.text = getTime(event.getStartTime())
        holder.eventEndTime.text = getTime(event.getEndTime())
        holder.eventDetail.text = events[position].getEventDetail()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime (date: Date): String {
        return SimpleDateFormat("HH:mm").format(date)
    }
    override fun getItemCount(): Int {
        return events.size
    }
}