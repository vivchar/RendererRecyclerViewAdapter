package com.example.vivchar.immutableadapter;

import android.support.annotation.NonNull;

import com.example.vivchar.rendererrecyclerviewadapter.ItemModel;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public
class HeaderModel implements ItemModel
{

	public static final int TYPE = 0;
	@NonNull
	private final String mTitle;

	public
	HeaderModel(@NonNull final String title) {
		mTitle = title;
	}

	@Override
	public
	int getType() {
		return TYPE;
	}

	@NonNull
	public
	String getTitle() {
		return mTitle;
	}
}
