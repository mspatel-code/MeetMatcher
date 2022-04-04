package ca.group6.meetmatcher.model

import com.google.android.gms.maps.model.LatLng

class LocationPlace(var name: String, var latLng: LatLng) {
    override fun toString(): String {
        return "$name ${latLng.latitude}"
    }
}