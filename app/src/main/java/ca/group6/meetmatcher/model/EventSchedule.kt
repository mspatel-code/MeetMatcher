package ca.group6.meetmatcher.model

import java.util.*
import kotlin.collections.ArrayList

class EventSchedule (date: Date, events: ArrayList<Event>) {
    private var eventDate: Date = date
    private var eventList: ArrayList<Event> = events

    constructor(): this (Date(), ArrayList())

    fun getEventDate (): Date {
        eventDate.hours = 0
        eventDate.minutes = 0
        eventDate.seconds = 0
        return eventDate
    }

    fun setEventDate (newDate: Date) {
        eventDate = newDate
    }

    fun getEventList (): ArrayList<Event> {
        return eventList
    }

    fun setEventList (newList: ArrayList<Event>) {
        eventList = newList
    }

    fun addNewEvent(event: Event) {
        eventList.add(event)
    }
}