package com.github.vivchar.example

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
	var router: UIRouter? = null
	var menuController: OptionsMenuController? = null
	private var presenter: MainPresenter? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		router = UIRouter(this)
		menuController = OptionsMenuController(this)
		setContentView(R.layout.main)
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)
		val firstInit = savedInstanceState == null
		presenter = MainPresenter(menuController!!, router!!, firstInit)
	}

	override fun onStart() {
		super.onStart()
		presenter?.viewShown()
	}

	override fun onStop() {
		super.onStop()
		presenter?.viewHidden()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuController?.onCreateOptionsMenu(menu, menuInflater)
		return super.onCreateOptionsMenu(menu)
	}

	override fun onPrepareOptionsMenu(menu: Menu): Boolean {
		menuController?.onPrepareOptionsMenu(menu, menuInflater)
		return super.onPrepareOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		menuController?.onOptionsItemSelected(item)
		return super.onOptionsItemSelected(item)
	}
}