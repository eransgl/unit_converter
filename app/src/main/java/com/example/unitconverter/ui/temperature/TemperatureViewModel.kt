package com.example.unitconverter.ui.temperature

import android.text.Editable
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitconverter.R

class TemperatureViewModel : ViewModel() {


    private val _titleText = MutableLiveData<String>().apply {
        value = "This is temperature Fragment"
    }

//    private val _inputMetricText = MutableLiveData<Editable>().apply {
//    }
//
//    private val _inputUSAText = MutableLiveData<Editable>().apply {
//    }
//
//    private val _directionButton = MutableLiveData<Boolean>().apply {
//    }

    val titleText: LiveData<String> = _titleText
//    val inputMetricText: LiveData<Editable> = _inputMetricText
//    val inputUSAText: LiveData<Editable> = _inputUSAText
//    val directionButton: LiveData<Boolean> = _directionButton
}