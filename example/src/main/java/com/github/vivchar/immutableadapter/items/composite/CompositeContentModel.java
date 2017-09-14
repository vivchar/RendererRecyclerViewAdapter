package com.github.vivchar.immutableadapter.items.composite;

import android.support.annotation.NonNull;

import com.github.vivchar.immutableadapter.items.BaseItemModel;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeItemModel;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/24/17.
 */

public class CompositeContentModel
		extends DefaultCompositeItemModel
		implements BaseItemModel
{
	public static final int TYPE = 12431;

	private int mID;

	public CompositeContentModel(final int ID, @NonNull final List<BaseItemModel> items) {
		super(TYPE, items);
		mID = ID;
	}

	@Override
	public int getID() {
		return mID;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final CompositeContentModel that = (CompositeContentModel) o;

		if (mID != that.mID) {
			return false;
		}
		return mItems.equals(that.mItems);

	}

	@Override
	public int hashCode() {
		int result = mID;
		result = 31 * result + mItems.hashCode();
		return result;
	}
}
