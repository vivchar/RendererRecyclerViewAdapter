package com.github.vivchar.example;

import android.app.Application;

import com.github.vivchar.network.MainManager;


/**
 * Created by vivchar on 08.10.17.
 */

public class MainApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		MainManager.init(this);
	}
}
