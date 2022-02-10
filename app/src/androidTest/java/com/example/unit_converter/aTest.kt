package com.example.unit_converter

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppContextTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.unit_converter", appContext.packageName)
    }
}

// TODO: 08/02/2022 Add tests: scrolling between tabs, for each Dimension: test with several values

//fun enterValue(value: Double, editTextId: Int) {
//    
//}
//
//class TemperatureTest {
//
//    @Test
//    fun simpleConversion() {
//
//    }
//
//}