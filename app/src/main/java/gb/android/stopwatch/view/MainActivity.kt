package gb.android.stopwatch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import gb.android.stopwatch.databinding.ActivityMainBinding
import gb.android.stopwatch.model.time.StopwatchTimeModel
import gb.android.stopwatch.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private lateinit var vModel: MainViewModel

    private val liveData: LiveData<StopwatchTimeModel> by lazy {
        vModel.subscribe()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: MainViewModel by viewModel()
        vModel = viewModel

        binding.btnStart.setOnClickListener {
            viewModel.start()
        }
        binding.btnStop.setOnClickListener {
            viewModel.stop()
        }
        binding.btnReset.setOnClickListener {
            viewModel.reset()
        }

        liveData.observe(this@MainActivity, {
            binding.tvMiliseconds.text = it.milliseconds
            binding.tvSeconds.text = it.seconds
            binding.tvMinutes.text = it.minutes
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}