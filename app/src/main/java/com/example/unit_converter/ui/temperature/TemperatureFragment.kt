package com.example.unit_converter.ui.temperature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.unit_converter.Dimensions.*
import com.example.unit_converter.Dimensions.TemperatureUnit.*
import com.example.unit_converter.databinding.FragmentTemperatureBinding

class TemperatureFragment : Fragment() {

    private lateinit var temperatureViewModel: TemperatureViewModel
    private var _binding: FragmentTemperatureBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        temperatureViewModel = ViewModelProvider(this)[TemperatureViewModel::class.java]

        _binding = FragmentTemperatureBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTemperature
        temperatureViewModel.titleText.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val clearButton: Button = binding.clearButton

        val unitHandler = UnitValueContainerHandler(
            listOf(
                UnitValueContainer(binding.InputMetricCelsius, Celsius),
                UnitValueContainer(binding.InputUsaFahrenheit, Fahrenheit)
            )
        )

        clearButton.setOnClickListener {
            run {
                unitHandler.clearAll()
            }
        }

        unitHandler.initOnEditorActionListeners()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
