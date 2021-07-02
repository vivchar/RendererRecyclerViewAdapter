package com.github.vivchar.example.pages.github.items

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel

/**
 * Created by Vivchar Vitaly on 10.10.17.
 */
data class StargazerModel(val id: Int, val name: String, val avatarUrl: String, val htmlUrl: String) : ViewModel {
	override fun toString() = name
}