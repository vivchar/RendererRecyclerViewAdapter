package com.github.vivchar.example.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Vivchar Vitaly on 10.07.19.
 */

public class AvatarView extends ImageView {

	public AvatarView(final Context context) {
		this(context, null);
	}

	public AvatarView(final Context context, final AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AvatarView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setUrl(@NonNull final String url) {
		final RequestOptions options = new RequestOptions();
		options.transform(new MultiTransformation(new BlurTransformation(25), new CircleCrop()));

		Glide.with(getContext())
				.asBitmap()
				.load(url)
				.apply(options)
				.into(this);
	}
}
