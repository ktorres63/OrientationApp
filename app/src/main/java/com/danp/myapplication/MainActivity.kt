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
import kotlin.math.abs
import kotlin.math.atan2

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager

    private var accelerometer: Sensor? = null
    private var rotationVectorSensor: Sensor? = null


    private var _x by mutableFloatStateOf(0f)
    private var _y by mutableFloatStateOf(0f)
    private var _z by mutableFloatStateOf(0f)

    private var _azimuth by mutableFloatStateOf(0f)
    private var _azimuthClear by mutableFloatStateOf(0f)

    private var _pitch by mutableFloatStateOf(0f)
    private var _roll by mutableFloatStateOf(0f)

    private var _angle by mutableFloatStateOf(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR)

        setContent {
            OrientationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //AccelerometerDisplay(x = _x, y = _y, z = _z, angle = _angle)
                    OrientationDisplay(roll = _roll, pitch = _pitch, yaw = _azimuth)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        rotationVectorSensor?.also { rotationVector ->
            sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
//        var heading:Float
//        event?.let {
//            _x = it.values[0]
//            _y = it.values[1]
//            _z = it.values[2]
//            //Roll
//            _angle = (atan2(_y.toDouble(), _x.toDouble()).toFloat() * 180 / Math.PI.toFloat())+90f
//        }
//        Log.i("ACC", "X=$_x, Y=$_y Z=$_z")


        event?.let {
            if (event.sensor.type == Sensor.TYPE_GAME_ROTATION_VECTOR) {
                val rotationMatrix = FloatArray(9)
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)

                val orientationAngles = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientationAngles)

                val azimuth = orientationAngles[0] // Yaw
                val pitch = orientationAngles[1]
                val roll = orientationAngles[2]

                // Convierte los valores de radianes a grados
                val azimuthDegrees = Math.toDegrees(azimuth.toDouble()).toFloat()
                val pitchDegrees = Math.toDegrees(pitch.toDouble()).toFloat()
                val rollDegrees = Math.toDegrees(roll.toDouble()).toFloat()

                _azimuth = abs(azimuthDegrees)-90

//                _azimuth = when(azimuthDegrees){
//                    in  0f..180f -> abs(azimuthDegrees)-90
//                    else -> abs(azimuthDegrees)+90
//                }

                _azimuthClear = azimuthDegrees
                _pitch = pitchDegrees
                _roll = rollDegrees

//                _azimuth = when (azimuthDegrees) {
//                    in -90f..0f -> azimuthDegrees + 90
//                    in 0f..180f -> azimuthDegrees - 90
//                    in -180f..-90f -> 90f
//                    else -> 90f // Esto cubre el caso de -180 o 180
//                }

            }
        }
        Log.i("Deg", "Raw Azimuth: $_azimuthClear")
        //Log.i("Deg", "Raw Azimuth: ${abs(_azimuth)+90}")



    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}


@Composable
fun OrientationDisplay(roll: Float, pitch: Float, yaw: Float) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Roll: $roll", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Pitch: $pitch", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Yaw: $yaw", style = MaterialTheme.typography.bodyLarge)
        // Text(text = "Rotation Angle: $angle", style = MaterialTheme.typography.bodyLarge)
        DrawRectangle()
        TriangleMov(angle = yaw, modifier =Modifier.fillMaxSize() )
    }
}

@Composable
fun AccelerometerDisplay(x: Float, y: Float, z: Float, angle: Float) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "X: $x", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Y: $y", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Z: $z", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Rotation Angle: $angle", style = MaterialTheme.typography.bodyLarge)
        DrawRectangle()
        TriangleMov(angle = angle, modifier = Modifier.fillMaxSize())
    }
}


@Composable
fun TriangleMov(angle: Float, modifier: Modifier) {
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
