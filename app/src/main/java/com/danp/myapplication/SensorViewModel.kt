package com.danp.myapplication

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SensorViewModel(private val sensorService: SensorService) : ViewModel() {

    private val _rotationAngle = mutableStateOf(0f)
    val rotationAngle: State<Float> get() = _rotationAngle

    init {
        viewModelScope.launch {
            sensorService.rotationAngle.collect { angle ->
                _rotationAngle.value = angle
            }
        }
    }
}