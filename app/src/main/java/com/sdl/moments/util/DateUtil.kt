package com.sdl.moments.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val msFormat = SimpleDateFormat("mm:ss", Locale.CHINA)

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
    fun timeParse(duration: Long): String {
        var time = ""
        if (duration > 1000) {
            time = timeParseMinute(duration)
        } else {
            val minute = duration / 60000
            val seconds = duration % 60000
            val second = Math.round(seconds.toFloat() / 1000).toLong()
            if (minute < 10) {
                time += "0"
            }
            time += minute.toString() + ":"
            if (second < 10) {
                time += "0"
            }
            time += second
        }
        return time
    }

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
    fun timeParseMinute(duration: Long): String {
        try {
            return msFormat.format(duration)
        } catch (e: Exception) {
            e.printStackTrace()
            return "0:00"
        }

    }
}