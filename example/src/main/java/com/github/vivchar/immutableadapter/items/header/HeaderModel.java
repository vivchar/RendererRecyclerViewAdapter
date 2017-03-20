package com.github.vivchar.immutableadapter.items.header;

import android.support.annotation.NonNull;

import com.github.vivchar.immutableadapter.items.BaseItemModel;

/**
 * Created by Vivchar Vitaly on 3/6/17.
 */
public
class HeaderModel
		implements BaseItemModel
{

	public static final int TYPE = 2;
	private final int mID;
	@NonNull
	private final String mName;

	public
	HeaderModel(final int ID, @NonNull final String name) {
		mID = ID;
		mName = name;
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
		return mName;
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

		final HeaderModel that = (HeaderModel) o;

		if (mID != that.mID) {
			return false;
		}
		return mName.equals(that.mName);

	}

	@Override
	public
	int hashCode() {
		int result = mID;
		result = 31 * result + mName.hashCode();
		return result;
	}
}
