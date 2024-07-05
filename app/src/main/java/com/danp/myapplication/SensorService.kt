package com.danp.myapplication

import android.app.Service
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.mutableFloatStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import android.hardware.Sensor
import kotlinx.coroutines.delay
import android.content.Intent
import android.hardware.SensorEvent
import android.os.IBinder
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.atan2


class SensorService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private val scope = CoroutineScope(Dispatchers.Default)
    private val _rotationAngle = MutableStateFlow(0f)
    val rotationAngle: StateFlow<Float> get() = _rotationAngle


//    override fun onCreate() {
//        super.onCreate()
//        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
//        sensorManager.registerListener(
//            this,
//            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//            SensorManager.SENSOR_DELAY_NORMAL
//        )
//    }
    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val x = event.values[0]
            val y = event.values[1]

            val angle = atan2(y.toDouble(), x.toDouble()).toFloat() * 180 / Math.PI.toFloat()
            _rotationAngle.value = angle
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
        scope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}