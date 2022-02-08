package com.example.unit_converter.ui.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.unit_converter.Dimensions
import com.example.unit_converter.Dimensions.AreaUnit.*
import com.example.unit_converter.databinding.FragmentAreaBinding

class AreaFragment : Fragment() {

    private lateinit var areaViewModel: AreaViewModel
    private var _binding: FragmentAreaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        areaViewModel =
            ViewModelProvider(this).get(AreaViewModel::class.java)

        _binding = FragmentAreaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textArea
        areaViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val clearButton: Button = binding.clearButton

        val unitHandler: Dimensions.UnitValueContainerHandler =
            Dimensions.UnitValueContainerHandler(
                listOf(
                    Dimensions.UnitValueContainer(binding.InputMetricSquareMeter, SquareMeter),
                    Dimensions.UnitValueContainer(binding.InputMetricSquareKiloMeter, SquareKilometer),
                    Dimensions.UnitValueContainer(binding.InputUsaSquareInch, SquareInch),
                    Dimensions.UnitValueContainer(binding.InputUsaSquareFoot, SquareFoot),
                    Dimensions.UnitValueContainer(binding.InputUsaSquareYard, SquareYard),
                    Dimensions.UnitValueContainer(binding.InputUsaAcre, Acre),
                    Dimensions.UnitValueContainer(binding.inputUsaSquareMile, SquareMile)
                )
            )

        clearButton.setOnClickListener(View.OnClickListener { _ ->
            run {
                unitHandler.clearAll()
            }
        })

        unitHandler.initOnEditorActionListeners()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}