package gb.android.stopwatch.di

import gb.android.stopwatch.model.ElapsedTimeCalculator
import gb.android.stopwatch.model.StopwatchStateCalculator
import gb.android.stopwatch.model.StopwatchStateHolder
import gb.android.stopwatch.model.time.TimestampMillisecondsParser
import gb.android.stopwatch.model.time.TimestampProvider
import gb.android.stopwatch.model.time.TimestampProviderImpl
import gb.android.stopwatch.viewmodel.MainViewModel
import org.koin.dsl.module


val application = module {

    single<TimestampProvider> {
        TimestampProviderImpl()
    }

    single<TimestampMillisecondsParser> {
        TimestampMillisecondsParser()
    }

    single<ElapsedTimeCalculator> {
        ElapsedTimeCalculator(timestampProvider = get())
    }

    single<StopwatchStateCalculator> {
        StopwatchStateCalculator(
            timestampProvider = get(),
            elapsedTimeCalculator = get()
        )
    }

    single<StopwatchStateHolder> {
        StopwatchStateHolder(
            stopwatchStateCalculator = get(),
            elapsedTimeCalculator = get(),
            timestampMillisecondsFormatter = get()
        )
    }
}

val mainScreen = module {
    factory { MainViewModel(stopwatchStateHolder = get()) }
}