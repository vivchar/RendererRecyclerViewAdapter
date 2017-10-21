package com.github.vivchar.example.pages.github.items.stargazer;

import android.support.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;

/**
 * Created by Vivchar Vitaly on 10.10.17.
 */

public class StargazerModel implements ItemModel {

	public static final int TYPE = 634278;
	@NonNull
	private final String mName;
	@NonNull
	private final String mAvatarUrl;
	@NonNull
	private final String mHtmlUrl;

	public StargazerModel(@NonNull final String name, @NonNull final String avatarUrl, @NonNull final String htmlUrl) {
		mName = name;
		mAvatarUrl = avatarUrl;
		mHtmlUrl = htmlUrl;
	}

	@Override
	public int getType() {
		return TYPE;
	}

	@NonNull
	public String getName() {
		return mName;
	}

	@NonNull
	public String getAvatarUrl() {
		return mAvatarUrl;
	}

	@NonNull
	public String getHtmlUrl() {
		return mHtmlUrl;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final StargazerModel that = (StargazerModel) o;

		if (!mName.equals(that.mName)) {
			return false;
		}
		if (!mAvatarUrl.equals(that.mAvatarUrl)) {
			return false;
		}
		return mHtmlUrl.equals(that.mHtmlUrl);
	}

	@Override
	public int hashCode() {
		int result = mName.hashCode();
		result = 31 * result + mAvatarUrl.hashCode();
		result = 31 * result + mHtmlUrl.hashCode();
		return result;
	}
}
