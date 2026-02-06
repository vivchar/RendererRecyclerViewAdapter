package com.github.vivchar.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.github.vivchar.example.databinding.MainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: MainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = MainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setSupportActionBar(binding.toolbar)

		val navHostFragment = supportFragmentManager
			.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment))
		setupActionBarWithNavController(navController, appBarConfiguration)
	}

	override fun onSupportNavigateUp(): Boolean {
		val navHostFragment = supportFragmentManager
			.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
	}
}
