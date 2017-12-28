package com.github.vivchar.example.pages.simple.items;

import android.support.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */

public class ViewStateViewModel extends DefaultCompositeViewModel {

	private final int mID;

	public ViewStateViewModel(final int ID, @NonNull final List<? extends ViewModel> items) {
		super(items);
		mID = ID;
	}

	public int getID() {
		return mID;
	}
}