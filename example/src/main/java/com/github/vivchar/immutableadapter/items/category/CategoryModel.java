package com.github.vivchar.immutableadapter.items.category;

import android.support.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public
class CategoryModel
		implements ItemModel
{

	public static final int TYPE = 0;
	@NonNull
	private final String mTitle;

	public
	CategoryModel(@NonNull final String title) {
		mTitle = title;
	}

	@Override
	public
	int getType() {
		return TYPE;
	}

	@NonNull
	public
	String getName() {
		return mTitle;
	}
}
