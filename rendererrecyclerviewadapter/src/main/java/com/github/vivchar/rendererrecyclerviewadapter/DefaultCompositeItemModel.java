package com.github.vivchar.rendererrecyclerviewadapter;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 9/14/17.
 */

public class DefaultCompositeItemModel
		implements CompositeItemModel
{

	protected final int mType;
	@NonNull
	protected final List<? extends ItemModel> mItems;

	public DefaultCompositeItemModel(final int type, @NonNull final List<? extends ItemModel> items) {
		mType = type;
		mItems = items;
	}

	@Override
	public int getType() {
		return mType;
	}

	@NonNull
	@Override
	public List<? extends ItemModel> getItems() {
		return mItems;
	}
}