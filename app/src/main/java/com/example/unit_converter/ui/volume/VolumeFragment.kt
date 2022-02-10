package com.example.unit_converter.ui.volume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.unit_converter.Dimensions
import com.example.unit_converter.databinding.FragmentVolumeBinding

class VolumeFragment : Fragment() {

    private lateinit var volumeViewModel: VolumeViewModel
    private var _binding: FragmentVolumeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        volumeViewModel =
            ViewModelProvider(this)[VolumeViewModel::class.java]

        _binding = FragmentVolumeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textVolume
        volumeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val clearButton: Button = binding.clearButton

        val unitHandler: Dimensions.UnitValueContainerHandler = Dimensions.UnitValueContainerHandler(
            listOf(
                Dimensions.UnitValueContainer(binding.InputMetricMilliLiter, Dimensions.VolumeUnit.MilliLiter),
                Dimensions.UnitValueContainer(binding.InputMetricLiter, Dimensions.VolumeUnit.Liter),
                Dimensions.UnitValueContainer(binding.InputUsaFluidOunce, Dimensions.VolumeUnit.USFluidOunce),
                Dimensions.UnitValueContainer(binding.InputUsaCup, Dimensions.VolumeUnit.USCup),
                Dimensions.UnitValueContainer(binding.InputUsaPint, Dimensions.VolumeUnit.USPint),
                Dimensions.UnitValueContainer(binding.InputUsaQuart, Dimensions.VolumeUnit.USQuart),
                Dimensions.UnitValueContainer(binding.InputUsaGallon, Dimensions.VolumeUnit.USGallon)
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