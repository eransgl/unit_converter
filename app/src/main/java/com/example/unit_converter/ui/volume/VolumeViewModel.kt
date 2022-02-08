package com.example.unit_converter.ui.volume

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VolumeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is volume Fragment"
    }
    val text: LiveData<String> = _text
}