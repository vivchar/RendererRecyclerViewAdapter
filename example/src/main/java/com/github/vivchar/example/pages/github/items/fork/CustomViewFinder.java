package com.github.vivchar.example.pages.github.items.fork;

import android.view.View;

import com.github.vivchar.example.widgets.AvatarView;
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinderImpl;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

/**
 * Created by Vivchar Vitaly on 10.07.19.
 */
public class CustomViewFinder extends ViewFinderImpl<CustomViewFinder> {

	public CustomViewFinder(final View itemView) {
		super(itemView);
	}

	@NonNull
	public CustomViewFinder setUrl(@IdRes final int ID, @NonNull final String url) {
		((AvatarView) find(ID)).setUrl(url);
		return this;
	}
}