package com.codemonkey.android.foursquaredemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.codemonkey.android.foursquaredemo.R
import com.codemonkey.android.foursquaredemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FourSquareActivity : AppCompatActivity() {

    private lateinit var mNavigationController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mNavigationController = Navigation.findNavController(this, R.id.navHostFragment)
        val configuration: AppBarConfiguration = AppBarConfiguration.Builder(mNavigationController.graph).build()
        NavigationUI.setupActionBarWithNavController(this, mNavigationController, configuration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavigationController.navigateUp()
    }
}