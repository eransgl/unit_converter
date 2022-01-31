package com.example.unitconverter

import android.icu.text.MeasureFormat
import android.icu.text.MeasureFormat.FormatWidth
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.MeasureUnit.*
import android.icu.util.ULocale
import android.widget.EditText
import android.widget.TextView
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

    class UnitValueContainerHandler(val containers: List<UnitValueContainer>) {
//        fun getListOfSystem(system: System): List<UnitValueContainer> {
//            val systemList: MutableList<UnitValueContainer> = mutableListOf<UnitValueContainer>()
//            for (con in containers) {
//                if (con.unit.getSystem() == system) {
//                    systemList.add(con)
//                }
//            }
//            return systemList
//        }

        fun getContainerOf(textView: TextView): UnitValueContainer {
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
                    container.convertFrom(otherUnitContainer)
                }
            }
        }

        fun convertFrom(textView: TextView) {
            convertFrom(getContainerOf(textView))
        }

        fun clearAll() {
            for (con in containers) {
                con.editText.text.clear()
            }
        }
    }

    class UnitValueContainer(val editText: EditText, private val unit: DimensionUnit) {
        private val logger: Logger = Logger.getLogger("dimensionsLogger")

        private var standardizedValue: Double = 0.0

        private val formatter: MeasureFormat = MeasureFormat.getInstance(ULocale.US, FormatWidth.NARROW)

        private fun parse(value: Double): String {
            return formatter.formatMeasures(Measure(value, unit.getMeasureUnit())).toString()
        }

        private fun getInputText(): String {
            return this.editText.text.toString()
        }

        private fun getInputNumber(): Double {
            return getInputText().toDoubleOrNull() ?: 0.0
        }

        fun updateInputFromValue() {
//            val parsedText = if (this.unit.getDimension() == TEMPERATURE) {
//                this.parse(this.standardizedValue)
//            } else {
//                this.parse(this.unit.convertToThis(this.standardizedValue))
//            }
            this.editText.setText(this.parse(this.standardizedValue))
            logger.log(Level.INFO, "UnitContainer(unit[${unit}]).updateInputFromValue(${this.parse(this.standardizedValue)}) -> currentInput[${editText.text}]")

        }

        fun updateValueFromInput() {
            logger.log(Level.INFO, "UnitContainer(unit[${unit}]).updateValueFromInput(${getInputNumber()})")
            this.standardizedValue = this.unit.convertToStandard(getInputNumber())
        }

        fun convertFrom(otherUnitContainer: UnitValueContainer) {
            if (this.unit.getDimension() != otherUnitContainer.unit.getDimension()) {
                throw Error("Trying to convert different dimensions unit[${otherUnitContainer.unit}] of dimension[${otherUnitContainer.unit.getDimension()}] to unit[${this.unit}] of dimension[${this.unit.getDimension()}]")
            }
            val otherValue: Double = otherUnitContainer.standardizedValue
//            val standardizedOtherValue: Double = otherUnitContainer.unit.convertToStandard(otherValue)
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

    enum class MassUnit(private val system: System, private val measureUnit: MeasureUnit, private val dimension: Dimensions, val toKG: Double): DimensionUnit {
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
        SquareKM(System.METRIC, SQUARE_KILOMETER, AREA, LengthUnit.KiloMeter.toMeter.pow(2)),
        SquareInch(System.USA, SQUARE_INCH, AREA, LengthUnit.Inch.toMeter.pow(2)),
        SquareFoot(System.USA, SQUARE_FOOT, AREA,  LengthUnit.Foot.toMeter.pow(2)),
        SquareYard(System.USA, SQUARE_YARD, AREA,  LengthUnit.Yard.toMeter.pow(2)),
        Acre(System.USA, ACRE, AREA,  4046.8564224),
        Section(System.USA, SQUARE_MILE, AREA, LengthUnit.Mile.toMeter.pow(2));

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
        CubicInch(System.USA, CUBIC_INCH, VOLUME, LengthUnit.Inch.toMeter.pow(3)),
        CubicFoot(System.USA, CUBIC_FOOT, VOLUME, LengthUnit.Foot.toMeter.pow(3)),
        CubicYard(System.USA, CUBIC_YARD, VOLUME, LengthUnit.Yard.toMeter.pow(3)),
        USFluidOunce(System.USA, FLUID_OUNCE, VOLUME,VolumeUnit.USGallon.toKiloLiter / 128),
        USCup(System.USA, CUP, VOLUME, VolumeUnit.USGallon.toKiloLiter / 16),
        USPint(System.USA, PINT, VOLUME, VolumeUnit.USGallon.toKiloLiter / 8),
        USQuart(System.USA, QUART, VOLUME, VolumeUnit.USGallon.toKiloLiter / 4),
        USGallon(System.USA, GALLON, VOLUME, LengthUnit.Inch.toMeter.pow(3) * 231);

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