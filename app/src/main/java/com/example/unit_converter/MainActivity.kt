package com.example.unit_converter

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.unit_converter.databinding.ActivityMainBinding
//import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


//        val logger: Logger = Logger.getLogger("MainActivityLogger")
//        logger.log(Level.INFO, "R.id.nav_host_fragment[${R.id.nav_host_fragment_activity_main}], binding.navHostFragment.id[${binding.navHostFragmentActivityMain.id}]")
//
//        val navHostFragment: NavController = findNavController(binding.navHostFragment.id)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.



        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_temperature, R.id.navigation_mass, R.id.navigation_length, R.id.navigation_area, R.id.navigation_volume
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}