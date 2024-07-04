package com.danp.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.compose.ui.graphics.Path
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danp.myapplication.ui.theme.OrientationAppTheme
import kotlin.math.atan2

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private var _x by mutableFloatStateOf(0f)
    private var _y by mutableFloatStateOf(0f)
    private var _z by mutableFloatStateOf(0f)
    private var _angle by mutableFloatStateOf(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        setContent {
            OrientationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AccelerometerDisplay(x = _x, y = _y, z = _z, angle = _angle)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var heading:Float
        event?.let {
            _x = it.values[0]
            _y = it.values[1]
            _z = it.values[2]
            //Roll
            _angle = atan2(_y.toDouble(), _x.toDouble()).toFloat() * 180 / Math.PI.toFloat()
           
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    @Composable
    fun AccelerometerDisplay(x: Float, y: Float, z: Float, angle: Float) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "X: $x", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Y: $y", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Z: $z", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Rotation Angle: $angle", style = MaterialTheme.typography.bodyLarge)
            DrawRectangle()
            TriangleMov(angle = angle, modifier =Modifier.fillMaxSize() )
        }
    }
}

@Composable
fun TriangleMov(angle:Float, modifier: Modifier) {
        Canvas(modifier) {
            val path = Path().apply {
                moveTo(x = 200.dp.toPx(), y = 150.dp.toPx())
                lineTo(x = 100.dp.toPx(), y = 350.dp.toPx())
                lineTo(x = 200.dp.toPx(), y = 300.dp.toPx())
                lineTo(x = 300.dp.toPx(), y = 350.dp.toPx())
                close()
            }
            rotate(degrees = angle, pivot = Offset(x = 200.dp.toPx(), y = 250.dp.toPx())) {
                drawPath(path, color = Color.Red)
            }
        }


}

@Composable
fun DrawRectangle(modifier: Modifier = Modifier) {

    Canvas(modifier = Modifier) {
        val rect = Rect(
            offset = Offset(x = 100.dp.toPx(), y = 400.dp.toPx()),
            size = Size(width = 200.dp.toPx(), height = 100.dp.toPx())
        )
        drawRect(
            color = Color.Blue,
            topLeft = rect.topLeft,
            size = rect.size
        )
    }

}

//@Preview(showBackground = true)
//@Composable
//fun PreviewRect() {
//    val rotationAngle = remember { mutableFloatStateOf(0f) }
//    TriangleMov(angle = rotationAngle, modifier = Modifier.fillMaxSize())
//
//}
