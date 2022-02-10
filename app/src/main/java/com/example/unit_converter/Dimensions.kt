package com.example.unit_converter

import android.icu.text.MeasureFormat
import android.icu.text.MeasureFormat.FormatWidth
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.MeasureUnit.*
import android.icu.util.ULocale
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.math.pow


enum class Dimensions {
    TEMPERATURE,
    MASS,
    LENGTH,
    AREA,
    VOLUME;

    enum class System {
        METRIC, USA
    }

    interface DimensionUnit {
        fun getMeasureUnit(): MeasureUnit
        fun convertToThis(standardizedValue: Double): Double
        fun convertToStandard(inputValue: Double): Double
        fun getDimension(): Dimensions
        fun getSystem(): System
    }

    class UnitValueContainerHandler(private val containers: List<UnitValueContainer>) {
//        fun getListOfSystem(system: System): List<UnitValueContainer> {
//            val systemList: MutableList<UnitValueContainer> = mutableListOf<UnitValueContainer>()
//            for (con in containers) {
//                if (con.unit.getSystem() == system) {
//                    systemList.add(con)
//                }
//            }
//            return systemList
//        }


//        val inputMetric: EditText = binding.InputMetric
//        temperatureViewModel.inputMetricText.observe(viewLifecycleOwner, Observer {
//            inputMetric.text = it
//        })

        fun initOnEditorActionListeners() {
            this.containers.forEach {
                    unitContainer ->
                run {
                    unitContainer.editText.setOnEditorActionListener { textView, _, _ ->
                        run {
                            this.getContainerOf(textView).updateValueFromInput()
                            this.convertFrom(textView)
                            true
                        }
                        false
                    }
                }
            }
        }

        private fun getContainerOf(textView: TextView): UnitValueContainer {
            for (con in containers) {
                if (con.editText.id == textView.id) {
                    return con
                }
            }
            throw java.lang.Error("no such TextView")
        }

//        public val metricUnits = getListOfSystem(System.METRIC)
//        public val usaUnits = getListOfSystem(System.USA)

        private fun convertFrom(otherUnitContainer: UnitValueContainer) {
            containers.forEach { container ->
                run {
                    if (container !== otherUnitContainer) {
                        container.convertFrom(otherUnitContainer)
                    }
                }
            }
        }

        private fun convertFrom(textView: TextView) {
            convertFrom(getContainerOf(textView))
        }

        fun clearAll() {
            containers.forEach { con -> con.clearText() }
        }
    }

    class UnitValueContainer(val editText: EditText, private val unit: DimensionUnit) {
        fun initObserver(liveData: LiveData<String>, fragment: Fragment) {
            liveData.observe(fragment.viewLifecycleOwner) {
                editText.setText(it)
            }
        }

        private val logger: Logger = Logger.getLogger("dimensionsLogger")

        private var standardizedValue: Double = 0.0

        private val formatter: MeasureFormat = MeasureFormat.getInstance(ULocale.US, FormatWidth.SHORT)

        private fun parse(value: Double): String {
            return formatter.formatMeasures(Measure(value, unit.getMeasureUnit())).toString()
        }

        private fun getInputText(): String {
            return this.editText.text.toString()
        }

        private fun getInputNumber(): Double {
            return getInputText().toDoubleOrNull() ?: 0.0
        }

        fun clearText() {
            this.editText.text.clear()
            logger.log(Level.INFO, "UnitContainer(unit[${unit}]).editText.text.clear()")
        }

        private fun updateInputFromValue() {
//            val converted: Double = this.unit.convertToThis(this.standardizedValue)
            this.editText.setText(this.parse(this.standardizedValue))
            logger.log(Level.INFO, "UnitContainer(unit[${unit}]).updateInputFromValue(standardizedValue[${this.parse(this.standardizedValue)}] -> currentInput[${getInputText()}]")

        }

        private fun parseInput() {
            logger.log(Level.INFO, "UnitContainer(unit[${unit}]).parseInput(${this.getInputText()}) -> parsed[${this.parse(this.getInputNumber())}]")
            this.editText.setText(this.parse(this.getInputNumber()))
        }

        fun updateValueFromInput() {
            logger.log(Level.INFO, "UnitContainer(unit[${unit}]).updateValueFromInput(${getInputNumber()}) -> standardizedValue[${standardizedValue}]")
            this.standardizedValue = this.unit.convertToStandard(getInputNumber())
            parseInput()
        }

        fun convertFrom(otherUnitContainer: UnitValueContainer) {
            if (this.unit.getDimension() !== otherUnitContainer.unit.getDimension()) {
                throw Error("Trying to convert different dimensions unit[${otherUnitContainer.unit}] of dimension[${otherUnitContainer.unit.getDimension()}] to unit[${this.unit}] of dimension[${this.unit.getDimension()}]")
            }

            val otherValue: Double = otherUnitContainer.standardizedValue
            logger.log(Level.INFO, "UnitContainer(unit[${unit}]).convertFrom(otherUnit[${otherUnitContainer.unit}], otherValue[${otherValue}])")
            this.standardizedValue = unit.convertToThis(otherValue)
            updateInputFromValue()
        }
    }

    enum class TemperatureUnit(private val system: System, private val measureUnit: MeasureUnit, private val dimension: Dimensions, val toThisConverter: (Double) -> Double):  DimensionUnit {
        Celsius(System.METRIC, CELSIUS, TEMPERATURE, { number -> (number-32.0)*5.0/9.0 }),
        Fahrenheit(System.USA, FAHRENHEIT, TEMPERATURE, { number -> (number*9.0/5.0)+32.0 });

        override fun getMeasureUnit(): MeasureUnit {
            return this.measureUnit
        }

        override fun convertToThis(standardizedValue: Double): Double {
            return toThisConverter(standardizedValue)
        }

        override fun convertToStandard(inputValue: Double): Double {
            return inputValue
        }

        override fun getDimension(): Dimensions {
            return this.dimension
        }

        override fun getSystem(): System {
            return this.system
        }

    }

    enum class LengthUnit(private val system: System, private val measureUnit: MeasureUnit, private val dimension: Dimensions, val toMeter: Double): DimensionUnit {
        CentiMeter(System.METRIC, CENTIMETER, LENGTH, 0.01),
        Meter(System.METRIC, METER, LENGTH, 1.0),
        KiloMeter(System.METRIC, KILOMETER, LENGTH, 1000.0),
        Inch(System.USA, INCH, LENGTH, 0.0254),
        Foot(System.USA, FOOT, LENGTH, 0.3048),
        Yard(System.USA, YARD, LENGTH, 0.9144),
        Mile(System.USA, MILE, LENGTH, 1609.34),

        Decimeter(System.METRIC, DECIMETER, LENGTH, 0.1);

        override fun getMeasureUnit(): MeasureUnit {
            return this.measureUnit
        }

        override fun convertToThis(standardizedValue: Double): Double {
            return standardizedValue / this.toMeter
        }

        override fun convertToStandard(inputValue: Double): Double {
            return inputValue * this.toMeter
        }

        override fun getDimension(): Dimensions {
            return this.dimension
        }

        override fun getSystem(): System {
            return this.system
        }
    }

    enum class MassUnit(private val system: System, private val measureUnit: MeasureUnit, private val dimension: Dimensions, private val toKG: Double): DimensionUnit {
        Gram(System.METRIC, GRAM, MASS, 0.001),
        KiloGram(System.METRIC, KILOGRAM, MASS, 1.0),
        MetricTon(System.METRIC, METRIC_TON, MASS, 1000.0),
        Ounce(System.USA, OUNCE, MASS, 0.028349523125),
        Pound(System.USA, POUND, MASS, 0.45359237),
        ShortTon(System.USA, TON, MASS, 907.18474);

        override fun getMeasureUnit(): MeasureUnit {
            return this.measureUnit
        }

        override fun convertToThis(standardizedValue: Double): Double {
            return standardizedValue / this.toKG
        }

        override fun convertToStandard(inputValue: Double): Double {
            return inputValue * this.toKG
        }

        override fun getDimension(): Dimensions {
            return this.dimension
        }

        override fun getSystem(): System {
            return this.system
        }
    }

    enum class AreaUnit(private val system: System, private val measureUnit: MeasureUnit, private val dimension: Dimensions, private val toSquareMeter: Double): DimensionUnit {
        SquareMeter(System.METRIC, SQUARE_METER, AREA, 1.0),
//        Dunam(System.METRIC, DUNAM, AREA, 1000.0),
//        TODO: adopt or discard Dunam
        SquareKilometer(System.METRIC, SQUARE_KILOMETER, AREA, LengthUnit.KiloMeter.toMeter.pow(2)),
        SquareInch(System.USA, SQUARE_INCH, AREA, LengthUnit.Inch.toMeter.pow(2)),
        SquareFoot(System.USA, SQUARE_FOOT, AREA,  LengthUnit.Foot.toMeter.pow(2)),
        SquareYard(System.USA, SQUARE_YARD, AREA,  LengthUnit.Yard.toMeter.pow(2)),
        Acre(System.USA, ACRE, AREA,  4046.8564224),
        SquareMile(System.USA, SQUARE_MILE, AREA, LengthUnit.Mile.toMeter.pow(2));

        override fun getMeasureUnit(): MeasureUnit {
            return this.measureUnit
        }

        override fun convertToThis(standardizedValue: Double): Double {
            return standardizedValue / this.toSquareMeter
        }

        override fun convertToStandard(inputValue: Double): Double {
            return inputValue * this.toSquareMeter
        }

        override fun getDimension(): Dimensions {
            return this.dimension
        }

        override fun getSystem(): System {
            return this.system
        }
    }

    enum class VolumeUnit(private val system: System, private val measureUnit: MeasureUnit, private val dimension: Dimensions, private val toKiloLiter: Double): DimensionUnit {
        MilliLiter(System.METRIC, MILLILITER, VOLUME, LengthUnit.CentiMeter.toMeter.pow(3)),
        Liter(System.METRIC, LITER, VOLUME, LengthUnit.Decimeter.toMeter.pow(3) ),
//        KiloLiter(System.METRIC, KILOLITER, LengthUnit.Meter.toMeter.pow(3)),
//        TODO: adopt or discard Kiloliter
//        CubicInch(System.USA, CUBIC_INCH, VOLUME, LengthUnit.Inch.toMeter.pow(3)),
//        CubicFoot(System.USA, CUBIC_FOOT, VOLUME, LengthUnit.Foot.toMeter.pow(3)),
//        CubicYard(System.USA, CUBIC_YARD, VOLUME, LengthUnit.Yard.toMeter.pow(3)),
        USGallon(System.USA, GALLON, VOLUME, LengthUnit.Inch.toMeter.pow(3) * 231),
        USFluidOunce(System.USA, FLUID_OUNCE, VOLUME, USGallon.toKiloLiter / 128),
        USCup(System.USA, CUP, VOLUME, USGallon.toKiloLiter / 16),
        USPint(System.USA, PINT, VOLUME, USGallon.toKiloLiter / 8),
        USQuart(System.USA, QUART, VOLUME, USGallon.toKiloLiter / 4);


        override fun getMeasureUnit(): MeasureUnit {
            return this.measureUnit
        }

        override fun convertToThis(standardizedValue: Double): Double {
            return standardizedValue / this.toKiloLiter
        }

        override fun convertToStandard(inputValue: Double): Double {
            return inputValue * this.toKiloLiter
        }

        override fun getDimension(): Dimensions {
            return this.dimension
        }

        override fun getSystem(): System {
            return this.system
        }
    }
}