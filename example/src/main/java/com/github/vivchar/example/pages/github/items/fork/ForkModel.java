package com.github.vivchar.example.pages.github.items.fork;

import android.support.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public class ForkModel
		implements ItemModel
{
	public static final int TYPE = 1654;

	@NonNull
	private final String mName;
	@NonNull
	private final String mAvatarUrl;
	@NonNull
	private final String mHtmlUrl;

	public ForkModel(@NonNull final String name, @NonNull final String avatarUrl, @NonNull final String htmlUrl) {
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
}
