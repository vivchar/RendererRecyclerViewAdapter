package com.github.vivchar.example.pages.simple.items;

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
public class SimpleViewModel implements ViewModel {

	private final String mText;

	public SimpleViewModel(final String text) {
		mText = text;
	}

	public String getText() {
		return mText;
	}
}