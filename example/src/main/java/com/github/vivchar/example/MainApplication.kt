package com.github.vivchar.example

import android.app.Application

/**
 * Created by Vivchar Vitaly on 08.10.17.
 */
class MainApplication : Application() {

	override fun onCreate() {
		super.onCreate()
		MainManager.init(this)
	}
}