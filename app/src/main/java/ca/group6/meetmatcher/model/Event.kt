package ca.group6.meetmatcher.model

class Event (start: String, end: String, detail: String) {
    private var startTime: String = "00:00"
    private var endTime: String = "00:00"
    private var eventDetail: String = ""

    init {
        this.startTime = start
        this.endTime = end
        this.eventDetail = detail
    }

    fun getStartTime(): String {
        return this.startTime
    }

    fun getEndTime(): String {
        return this.endTime
    }

    fun getEventDetail(): String {
        return this.eventDetail
    }
}