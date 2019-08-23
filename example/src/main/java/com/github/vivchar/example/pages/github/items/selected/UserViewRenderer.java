package com.github.vivchar.example.pages.github.items.selected;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.github.vivchar.example.R;
import com.github.vivchar.example.pages.github.items.stargazer.StargazerModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public class UserViewRenderer extends ViewRenderer<StargazerModel, UserViewHolder> {

	public UserViewRenderer() {
		super(StargazerModel.class);
	}

	@Override
	public void bindView(@NonNull final StargazerModel model, @NonNull final UserViewHolder holder) {
		holder.name.setText(model.getName());

//		final RequestOptions options = new RequestOptions();
//		options.transform(new MultiTransformation(new BlurTransformation(25), new CircleCrop()));

//		Glide.with(getContext())
//				.asBitmap()
//				.load(model.getAvatarUrl())
//				.apply(options)
//				.into(holder.avatar);
	}

	@NonNull
	@Override
	public UserViewHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new UserViewHolder(inflate(R.layout.item_user_selected, parent));
	}
}
