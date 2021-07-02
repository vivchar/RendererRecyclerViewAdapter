package com.github.vivchar.example.widgets

import android.view.View
import androidx.annotation.IdRes
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinderImpl

/**
 * Created by Vivchar Vitaly on 10.07.19.
 */
class CustomViewFinder(itemView: View) : ViewFinderImpl<CustomViewFinder>(itemView) {

	fun setUrl(@IdRes viewID: Int, url: String) = this.apply { (find<View>(viewID) as AvatarView).setUrl(url) }
	fun setUrlCircled(@IdRes ID: Int, url: String) = this.apply { (find<View>(ID) as AvatarView).setUrl(url, true) }
}