package com.example.unit_converter.ui.length

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LengthViewModel : ViewModel() {

    private val _titleText = MutableLiveData<String>().apply {
        value = "This is length Fragment"
    }
//
//    private val _inputCentiMeterText = MutableLiveData<String>().apply {
//        value = "@string/input_centimeter"
//    }
//
//    private val _inputMeterText = MutableLiveData<String>().apply {
//        value = "@string/input_meter"
//    }
//
//    private val _inputKiloMeterText = MutableLiveData<String>().apply {
//        value = "@string/input_kilometer"
//    }
//
//    private val _inputInchText = MutableLiveData<String>().apply {
//        value = "@string/input_inch"
//    }
//
//    private val _inputFootText = MutableLiveData<String>().apply {
//        value = "@string/input_foot"
//    }
//
//    private val _inputYardText = MutableLiveData<String>().apply {
//        value = "@string/input_yard"
//    }
//
//    private val _inputMileText = MutableLiveData<String>().apply {
//        value = "@string/input_mile"
//    }

    val titleText: LiveData<String> = _titleText
//    val inputCentiMeterText: LiveData<String> = _inputCentiMeterText
//    val inputMeterText: LiveData<String> = _inputMeterText
//    val inputKiloMeterText: LiveData<String> = _inputKiloMeterText
//    val inputInchText: LiveData<String> = _inputInchText
//    val inputFootText: LiveData<String> = _inputFootText
//    val inputYardText: LiveData<String> = _inputYardText
//    val inputMileText: LiveData<String> = _inputMileText
}