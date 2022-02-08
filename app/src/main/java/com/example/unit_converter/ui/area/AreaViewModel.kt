package com.example.unit_converter.ui.area

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AreaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is area Fragment"
    }
    val text: LiveData<String> = _text
}