package com.example.unitconverter.ui.Length

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.unitconverter.Dimensions.*
import com.example.unitconverter.databinding.FragmentLengthBinding

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
            ViewModelProvider(this).get(LengthViewModel::class.java)

        _binding = FragmentLengthBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textLength
        lengthViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val clearButton: Button = binding.clearButton


        val unitHandler: UnitValueContainerHandler = UnitValueContainerHandler(
            listOf(
                UnitValueContainer(binding.InputMetricCentiMeter, LengthUnit.CentiMeter),
                UnitValueContainer(binding.InputMetricMeter, LengthUnit.Meter),
                UnitValueContainer(binding.InputMetricKiloMeter, LengthUnit.KiloMeter),
                UnitValueContainer(binding.inputUsaInch, LengthUnit.Inch),
                UnitValueContainer(binding.InputUsaFoot, LengthUnit.Foot),
                UnitValueContainer(binding.inputUsaYard, LengthUnit.Yard),
                UnitValueContainer(binding.inputUsaMile, LengthUnit.Mile)
            )
        )

        clearButton.setOnClickListener(View.OnClickListener { v ->
            run {
                unitHandler.clearAll()
            }
        })

        unitHandler.containers.forEach {
            container ->
            run {
                container.editText.setOnEditorActionListener(TextView.OnEditorActionListener { textView, i, keyEvent ->
                    run {
                        unitHandler.getContainerOf(textView).updateValueFromInput()
                        unitHandler.convertFrom(textView)
                        unitHandler.getContainerOf(textView).updateInputFromValue()
                        true
                    }
                    false
                })
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}