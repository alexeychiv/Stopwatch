package gb.android.stopwatch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gb.android.stopwatch.model.StopwatchStateHolder
import gb.android.stopwatch.model.time.StopwatchTimeModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class MainViewModel(
    private val stopwatchStateHolder: StopwatchStateHolder,
) : ViewModel() {

    companion object {
        const val DELAY: Long = 20
    }

    private val liveData: MutableLiveData<StopwatchTimeModel> = MutableLiveData()

    fun subscribe(): LiveData<StopwatchTimeModel> {
        coroutineScope.launch {
            ticker.collect {
                liveData.value = it
            }
        }

        return liveData
    }

    private val coroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
    )

    private var job: Job? = null
    private val mutableTicker = MutableStateFlow(StopwatchTimeModel())
    val ticker: StateFlow<StopwatchTimeModel> = mutableTicker


    fun start() {
        if (job == null) startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        coroutineScope.launch {
            while (isActive) {
                mutableTicker.value = stopwatchStateHolder.getStopwatchTimeModel()
                liveData.value = mutableTicker.value
                delay(DELAY)
            }
        }
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
    }

    fun reset() {
        stopwatchStateHolder.reset()
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        coroutineScope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = StopwatchTimeModel("00", "00", "000")
        liveData.value = mutableTicker.value
    }

    override fun onCleared() {
        super.onCleared()
        stopJob()
    }
}