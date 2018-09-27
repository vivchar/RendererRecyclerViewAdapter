package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 9/14/17.
 */

public class DefaultCompositeViewModel implements CompositeViewModel {

	@NonNull
	protected final List<? extends ViewModel> mItems;

	public DefaultCompositeViewModel(@NonNull final List<? extends ViewModel> items) {
		mItems = items;
	}

	@NonNull
	@Override
	public List<? extends ViewModel> getItems() {
		return mItems;
	}
}