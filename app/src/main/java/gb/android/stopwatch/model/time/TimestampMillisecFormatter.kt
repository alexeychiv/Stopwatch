package gb.android.stopwatch.model.time


class TimestampMillisecondsParser {

    fun format(timestamp: Long): StopwatchTimeModel {
        val millisecondsFormatted = (timestamp % 1000).pad(3)
        val seconds = timestamp / 1000
        val secondsFormatted = (seconds % 60).pad(2)
        val minutes = seconds / 60
        val minutesFormatted = (minutes % 60).pad(2)

        return StopwatchTimeModel(minutesFormatted, secondsFormatted, millisecondsFormatted)
    }

    private fun Long.pad(desiredLength: Int) =
        this.toString().padStart(desiredLength, '0')

    companion object {
        const val DEFAULT_TIME = "00:00:000"
    }
}