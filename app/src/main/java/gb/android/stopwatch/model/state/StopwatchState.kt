package gb.android.stopwatch.model.state


sealed class StopwatchState {

    data class Stopped(
        val elapsedTime: Long
    ) : StopwatchState()

    data class Running(
        val startTime: Long,
        val elapsedTime: Long
    ) : StopwatchState()

}