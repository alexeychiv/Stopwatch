package gb.android.stopwatch.model.time

class TimestampProviderImpl : TimestampProvider {

    override fun getMilliseconds(): Long {
        return System.currentTimeMillis()
    }

}