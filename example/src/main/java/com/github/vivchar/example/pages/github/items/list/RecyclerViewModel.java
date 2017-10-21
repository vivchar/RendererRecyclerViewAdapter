package com.github.vivchar.example.pages.github.items.list;

import android.support.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.CompositeItemModel;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeItemModel;
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/24/17.
 */

public class RecyclerViewModel extends DefaultCompositeItemModel {

	public static final int TYPE = 654323;
	private int mID;

	public RecyclerViewModel(final int ID, @NonNull final List<ItemModel> items) {
		super(TYPE, items);
		mID = ID;
	}

	public int getID() {
		return mID;
	}

	@Override
	public boolean equals(final Object o) {
		return o instanceof CompositeItemModel && mItems.equals(((CompositeItemModel) o).getItems());
	}

	@Override
	public int hashCode() {
		return mID;
	}
}