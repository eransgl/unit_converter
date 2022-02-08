package com.example.unit_converter.ui.mass

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
import com.example.unit_converter.Dimensions.MassUnit.*
import com.example.unit_converter.databinding.FragmentMassBinding

class MassFragment : Fragment() {

    private lateinit var massViewModel: MassViewModel
    private var _binding: FragmentMassBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        massViewModel =
            ViewModelProvider(this).get(MassViewModel::class.java)

        _binding = FragmentMassBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMass
        massViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val clearButton: Button = binding.clearButton

        val unitHandler: Dimensions.UnitValueContainerHandler =
            Dimensions.UnitValueContainerHandler(
                listOf(
                    Dimensions.UnitValueContainer(binding.InputMetricGram, Gram),
                    Dimensions.UnitValueContainer(binding.InputMetricKilogram, KiloGram),
                    Dimensions.UnitValueContainer(binding.InputMetricTon, MetricTon),
                    Dimensions.UnitValueContainer(binding.InputUsaOunce, Ounce),
                    Dimensions.UnitValueContainer(binding.InputUsaPound, Pound),
                    Dimensions.UnitValueContainer(binding.InputUsaShortTon, ShortTon)
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