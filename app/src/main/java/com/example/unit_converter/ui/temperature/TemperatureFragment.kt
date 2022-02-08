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

//    private val logger: Logger = Logger.getLogger("temperatureFragmentLogger")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        temperatureViewModel = ViewModelProvider(this)[TemperatureViewModel::class.java]

        _binding = FragmentTemperatureBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTemperature
        temperatureViewModel.titleText.observe(viewLifecycleOwner) {
            textView.text = it
        }

//        val inputMetric: EditText = binding.InputMetric
//        temperatureViewModel.inputMetricText.observe(viewLifecycleOwner, Observer {
//            inputMetric.text = it
//        })

//        val inputUsa: EditText = binding.InputUsa
//        temperatureViewModel.inputUSAText.observe(viewLifecycleOwner, Observer {
//            inputMetric.text = it
//        })

        val clearButton: Button = binding.clearButton
//        temperatureViewModel.directionButton.observe(viewLifecycleOwner, Observer {
//            direction.isChecked = it
//        })

        val unitHandler = UnitValueContainerHandler(
            listOf(
                UnitValueContainer(binding.InputMetricCelsius, Celsius),
                UnitValueContainer(binding.InputUsaFahrenheit, Fahrenheit)
            )
        )


//        fun getUpdatedInput(textView: TextView): Double {
//            val textString: String = textView.text.toString()
//            val textAsDoubleOrNull: Double? = textString.toDoubleOrNull()
//            logger.log(Level.INFO, "< getInput(): textString[${textString}], textAsDoubleOrNull[${textAsDoubleOrNull}]>")
//            return textAsDoubleOrNull ?: 0.0
////            return textView.text.toString().toDoubleOrNull() ?: 0.0
//        }

//        fun convert(textView: TextView, converterUnit: TemperatureUnit, resultInput: EditText) {
//
//            logger.log(Level.INFO, "<convert() : inputValue[${getInput(textView)}], converterUnit[${converterUnit}]," +
//                    " inputIsUsa[${textView.id == binding.InputUsa.id}], resultIsUsa[${resultInput.id == binding.InputUsa.id}],  >")
//
//            resultInput.setText(converterUnit.convertParse(getInput(textView)), TextView.BufferType.NORMAL)
//        }

        clearButton.setOnClickListener {
            run {
                unitHandler.clearAll()
            }
        }

        unitHandler.initOnEditorActionListeners()
//
//        inputMetric.setOnEditorActionListener(TextView.OnEditorActionListener {v, keyCode, event ->
//            run {
//                celsiusValue.updateValueFromInput()
//                fahrenheitValue.convertFrom(celsiusValue)
//                celsiusValue.updateInputFromValue()
//            true
//        }
//            false
//        })

//        inputUsa.setOnEditorActionListener(TextView.OnEditorActionListener {v, keyCode, event ->
//            run {
//                fahrenheitValue.updateValueFromInput()
//                celsiusValue.convertFrom(fahrenheitValue )
//                fahrenheitValue.updateInputFromValue()
//        inputUsa.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
//                logger.log(Level.INFO, "InputUSA.onEditorAction is running")
//                v.text = TemperatureUnit.Fahrenheit.parse(getInput(v))
//                val convertUnit: TemperatureUnit = TemperatureUnit.Celsius;
//                val resultInput: EditText = inputMetric;
//                convert(v, convertUnit, resultInput);

//                true
//            }
//            false
//        })

        return root
    }


//    fun ClickSwitchDirection(v: View?) {
//        val resultText : Fragment = R.layout.fragment_temperature
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
