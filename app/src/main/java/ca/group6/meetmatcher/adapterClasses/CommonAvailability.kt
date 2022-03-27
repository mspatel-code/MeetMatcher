package ca.group6.meetmatcher.adapterClasses

import java.util.ArrayList

internal class Interval(var start: Int, var end: Int)
internal object IntervalsIntersection {
    fun merge(arr1: Array<Interval>, arr2: Array<Interval>): Array<Interval> {
        val result: MutableList<Interval> = ArrayList()
        var i = 0
        var j = 0
        while (i < arr1.size && j < arr2.size) {
            // check if the interval arr[i] intersects with arr2[j]
            // check if one of the interval's start time lies within the other interval
            if (arr1[i].start >= arr2[j].start && arr1[i].start <= arr2[j].end
                || arr2[j].start >= arr1[i].start && arr2[j].start <= arr1[i].end
            ) {
                // store the intersection part
                result.add(
                    Interval(
                        Math.max(arr1[i].start, arr2[j].start), Math.min(
                            arr1[i].end, arr2[j].end
                        )
                    )
                )
            }

            // move next from the interval which is finishing first
            if (arr1[i].end < arr2[j].end) i++ else j++
        }
        return result.toTypedArray()
    }
}