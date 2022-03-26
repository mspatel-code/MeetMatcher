package ca.group6.meetmatcher.model

import java.util.*

class Event (start: Date, end: Date, detail: String) {
    private var startTime: Date = start
    private var endTime: Date = end
    private var eventDetail: String = detail

    constructor() : this(Date(), Date(), "")

    fun getStartTime(): Date {
        return this.startTime
    }

    fun setStartTime(newStart: Date) {
        this.startTime = newStart
    }

    fun getEndTime(): Date {
        return this.endTime
    }

    fun setEndTime(newEnd: Date) {
        this.endTime = newEnd
    }

    fun getEventDetail(): String {
        return this.eventDetail
    }

    fun setEventDetail(newDetail: String) {
        this.eventDetail = newDetail
    }
}