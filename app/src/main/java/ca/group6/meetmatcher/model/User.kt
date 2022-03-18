package ca.group6.meetmatcher.model

import com.google.firebase.database.IgnoreExtraProperties

class User(uid: String, username: String, status: String) {
    private var uid: String? = uid
    private var username: String? = username
    private var status: String? = status

    constructor() : this("", "", "")

    fun getUid (): String? {
        return uid
    }

    fun getUsername (): String? {
        return username
    }

    fun getStatus (): String? {
        return status
    }
}