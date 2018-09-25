package com.github.vivchar.example.pages.github.items.list;

import androidx.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/24/17.
 */

public class RecyclerViewModel extends DefaultCompositeViewModel {

	private final int mID;

	public RecyclerViewModel(final int ID, @NonNull final List<? extends ViewModel> items) {
		super(items);
		mID = ID;
	}

	public int getID() {
		return mID;
	}

	@Override
	public boolean equals(final Object o) {
		return o instanceof CompositeViewModel && mItems.equals(((CompositeViewModel) o).getItems());
	}

	@Override
	public int hashCode() {
		return mID;
	}

	@Override
	public String toString() {
//		return getClass().getSimpleName() + "{" + mID + ", " + mType + ", " + mItems.toString() + "}";
		return getClass().getSimpleName() + "{" + mItems.toString() + "}";
	}
}