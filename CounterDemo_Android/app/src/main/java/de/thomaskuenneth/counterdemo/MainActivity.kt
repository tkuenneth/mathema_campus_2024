package de.thomaskuenneth.counterdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import de.thomaskuenneth.counterdemo.ui.theme.CounterDemoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startA = 10
        val endA = 20
        val delayA = 2000L
        val flowA: MutableStateFlow<Int> = MutableStateFlow(startA)

        val startB = 20
        val endB = 30
        val delayB = 4000L
        val flowB: MutableStateFlow<Int> = MutableStateFlow(startB)

        val jobA = lifecycleScope.launch(to = endA, delay = delayA, flow = flowA)
        val jobB = lifecycleScope.launch(to = endB, delay = delayB, flow = flowB)
        lifecycleScope.launch {
            jobA.join()
            jobB.cancelAndJoin()
        }

        setContent {
            val a: Int by flowA.collectAsState(startA)
            val b: Int by flowB.collectAsState(startB)
            CounterDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterDemo(a = a, b = b)
                }
            }
        }
    }
}

fun CoroutineScope.launch(to: Int, delay: Long, flow: MutableStateFlow<Int>): Job {
    return launch {
        while (flow.value < to) {
            delay(delay)
            flow.value += 1
        }
    }
}

@Composable
fun CounterDemo(a: Int, b: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        BigNumber(number = a)
        BigNumber(number = b)
    }
}

@Composable
fun BigNumber(number: Int) {
    Text(text = number.toString(), style = MaterialTheme.typography.displayMedium)
}
