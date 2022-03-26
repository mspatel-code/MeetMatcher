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
        var startTime: TextView
        var endTime: TextView
        var eventDetail: TextView

        init {
            startTime = view.findViewById(R.id.start_time)
            endTime = view.findViewById(R.id.end_time)
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
        var event = events[position]
        holder.startTime.text = getTime(event.getStartTime().hours, event.getStartTime().minutes)
        holder.endTime.text = getTime(event.getEndTime().hours, event.getStartTime().minutes)
        holder.eventDetail.text = events[position].getEventDetail()
    }

    fun getTime (hour: Int, minute: Int): String {
        return "$hour:$minute"
    }
    override fun getItemCount(): Int {
        return events.size
    }
}