package gb.android.stopwatch.model

import gb.android.stopwatch.model.state.StopwatchState
import gb.android.stopwatch.model.time.TimestampProvider


class StopwatchStateCalculator(
    private val timestampProvider: TimestampProvider,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
) {

    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
        when (oldState) {
            is StopwatchState.Running -> oldState
            is StopwatchState.Stopped -> {
                StopwatchState.Running(
                    startTime = timestampProvider.getMilliseconds(),
                    elapsedTime = oldState.elapsedTime
                )
            }
        }

    fun calculateStopState(oldState: StopwatchState): StopwatchState.Stopped =
        when (oldState) {
            is StopwatchState.Running -> {
                val elapsedTime = elapsedTimeCalculator.calculate(oldState)
                StopwatchState.Stopped(elapsedTime = elapsedTime)
            }
            is StopwatchState.Stopped -> oldState
        }
}