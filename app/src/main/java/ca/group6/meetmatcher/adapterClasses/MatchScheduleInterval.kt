package ca.group6.meetmatcher.adapterClasses

object GFG {
    // Function to print the intersection
    fun findIntersection(intervals: Array<IntArray>, N: Int) {
        // First interval
        var l = intervals[0][0]
        var r = intervals[0][1]

        // Check rest of the intervals
        // and find the intersection
        for (i in 1 until N) {

            // If no intersection exists
            if (intervals[i][0] > r ||
                intervals[i][1] < l
            ) {
                println(-1)
                return
            } else {
                l = Math.max(l, intervals[i][0])
                r = Math.min(r, intervals[i][1])
            }
        }
        println("[$l, $r]")
    }
}