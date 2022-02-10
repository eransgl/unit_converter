package com.example.unit_converter.ui.length

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.unit_converter.Dimensions.*
import com.example.unit_converter.databinding.FragmentLengthBinding

class LengthFragment : Fragment() {

    private lateinit var lengthViewModel: LengthViewModel
    private var _binding: FragmentLengthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lengthViewModel =
            ViewModelProvider(this)[LengthViewModel::class.java]

        _binding = FragmentLengthBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textLength
        lengthViewModel.titleText.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val clearButton: Button = binding.clearButton


        val unitHandler = UnitValueContainerHandler(
            listOf(
                UnitValueContainer(binding.InputMetricCentimeter, LengthUnit.CentiMeter),
                UnitValueContainer(binding.InputMetricMeter, LengthUnit.Meter),
                UnitValueContainer(binding.InputMetricKiloMeter, LengthUnit.KiloMeter),
                UnitValueContainer(binding.InputUsaInch, LengthUnit.Inch),
                UnitValueContainer(binding.InputUsaFoot, LengthUnit.Foot),
                UnitValueContainer(binding.InputUsaYard, LengthUnit.Yard),
                UnitValueContainer(binding.InputUsaMile, LengthUnit.Mile)
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