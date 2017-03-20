package com.github.vivchar.immutableadapter.items.category;

import android.support.annotation.NonNull;

import com.github.vivchar.immutableadapter.items.BaseItemModel;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public
class CategoryModel
		implements BaseItemModel
{

	public static final int TYPE = 0;
	private final int mID;
	@NonNull
	private final String mTitle;

	public
	CategoryModel(final int ID, @NonNull final String title) {
		mID = ID;
		mTitle = title;
	}

	@Override
	public
	int getID() {
		return mID;
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

	@Override
	public
	boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final CategoryModel that = (CategoryModel) o;

		if (mID != that.mID) {
			return false;
		}
		return mTitle.equals(that.mTitle);

	}

	@Override
	public
	int hashCode() {
		int result = mID;
		result = 31 * result + mTitle.hashCode();
		return result;
	}
}
