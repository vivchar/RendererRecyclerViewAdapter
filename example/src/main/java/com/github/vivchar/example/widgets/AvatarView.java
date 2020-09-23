package com.github.vivchar.example.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Vivchar Vitaly on 10.07.19.
 */

public class AvatarView extends AppCompatImageView {

	public AvatarView(final Context context) {
		this(context, null);
	}

	public AvatarView(final Context context, final AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AvatarView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	//Temporary workaround, you should use view's attr to circle view
	public void setUrl(@NonNull final String url, final boolean isCircle) {
		final RequestOptions options = new RequestOptions();
		if (isCircle) {
			options.transform(new MultiTransformation(new BlurTransformation(25), new CircleCrop()));
		} else {
			options.transform(new BlurTransformation(25));
		}

		Glide.with(getContext())
				.asBitmap()
				.load(url)
				.apply(options)
				.into(this);
	}

	public void setUrl(@NonNull final String url) {
		setUrl(url, false);
	}
}
