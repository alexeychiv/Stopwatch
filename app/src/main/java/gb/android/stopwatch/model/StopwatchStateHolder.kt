package gb.android.stopwatch.model

import gb.android.stopwatch.model.state.StopwatchState
import gb.android.stopwatch.model.time.StopwatchTimeModel
import gb.android.stopwatch.model.time.TimestampMillisecondsParser


class StopwatchStateHolder(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsParser
) {

    var currentState: StopwatchState = StopwatchState.Stopped(0)
        private set

    fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    fun stop() {
        currentState = stopwatchStateCalculator.calculateStopState(currentState)
    }

    fun reset() {
        currentState = StopwatchState.Stopped(0)
    }

    fun getStopwatchTimeModel(): StopwatchTimeModel {
        val elapsedTime = when (val currentState = currentState) {
            is StopwatchState.Stopped -> currentState.elapsedTime
            is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState)
        }
        return timestampMillisecondsFormatter.format(elapsedTime)
    }
}