package ca.group6.meetmatcher.adapterClasses

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.model.Event

class EventAdapter(private val events: ArrayList<Event>):
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var startTime: TextView = view.findViewById(R.id.start_time)
        var endTime: TextView = view.findViewById(R.id.end_time)
        var eventDetail: TextView = view.findViewById(R.id.event_detail)

        init {
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
        holder.startTime.text = getStartTime1(event.getStartTime().hours, event.getStartTime().minutes)
        holder.endTime.text = getEndTime1(event.getEndTime().hours, event.getEndTime().minutes)
        holder.eventDetail.text = events[position].getEventDetail()
    }

    private fun getStartTime1 (hour: Int, minute: Int): String {
        return "Start Time-$hour:$minute"
    }

    private fun getEndTime1 (hour: Int, minute: Int): String {
        return "End Time-$hour:$minute"
    }

    override fun getItemCount(): Int {
        return events.size
    }
}