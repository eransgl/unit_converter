package com.example.unit_converter.ui.temperature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TemperatureViewModel : ViewModel() {

    private val _titleText = MutableLiveData<String>().apply {
        value = "This is temperature Fragment"
    }
//
//    private val _inputCelsiusText = MutableLiveData<String>().apply {
//        value = "@string/input_celsius"
//    }
//
//    private val _inputFahrenheitText = MutableLiveData<String>().apply {
//        value = "@string/input_fahrenheit"
//    }


    val titleText: LiveData<String> = _titleText
//    val inputCelsiusText: LiveData<String> = _inputCelsiusText
//    val inputFahrenheitText: LiveData<String> = _inputFahrenheitText
}