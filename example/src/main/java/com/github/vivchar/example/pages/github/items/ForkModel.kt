package com.github.vivchar.example.pages.github.items

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
data class ForkModel(val name: String, val avatarUrl: String, val htmlUrl: String) : ViewModel {
	override fun toString() = javaClass.simpleName + "{" + name + ", " + avatarUrl + ", " + htmlUrl + "}"
}