package com.github.vivchar.example.widgets

import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter

open class MyAdapter : RendererRecyclerViewAdapter() {

	init {
		/* an issue after kotlin converting: https://stackoverflow.com/a/50222496/4894238 */
		registerViewFinder { CustomViewFinder(it) }
	}
}