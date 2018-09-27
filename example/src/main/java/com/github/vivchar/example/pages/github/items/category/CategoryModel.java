package com.github.vivchar.example.pages.github.items.category;

import androidx.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public class CategoryModel implements ViewModel {

	@NonNull
	private final String mTitle;

	public CategoryModel(@NonNull final String title) {
		mTitle = title;
	}

	@NonNull
	public String getName() {
		return mTitle;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final CategoryModel that = (CategoryModel) o;

		return mTitle.equals(that.mTitle);
	}

	@Override
	public int hashCode() {
		return mTitle.hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + mTitle + "}";
	}
}
