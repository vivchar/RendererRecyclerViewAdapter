package com.github.vivchar.immutableadapter.items.content;

import android.support.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public
class ContentModel implements ItemModel
{

	public static final int TYPE = 1;
	@NonNull
	private final String mName;

	public
	ContentModel(@NonNull final String name) {
		mName = name;
	}

	@Override
	public
	int getType() {
		return TYPE;
	}

	@NonNull
	public
	String getName() {
		return mName;
	}
}
