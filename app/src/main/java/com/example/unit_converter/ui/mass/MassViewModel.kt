package com.example.unit_converter.ui.mass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MassViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is mass Fragment"
    }
    val text: LiveData<String> = _text
}