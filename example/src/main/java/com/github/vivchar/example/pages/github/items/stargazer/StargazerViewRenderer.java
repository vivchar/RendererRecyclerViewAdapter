package com.github.vivchar.example.pages.github.items.stargazer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public class StargazerViewRenderer extends ViewRenderer<StargazerModel, StargazerViewHolder> {

	private static final String TAG = StargazerViewRenderer.class.getSimpleName();
	@NonNull
	private final Listener mListener;
	private final int mLayoutID;

	public StargazerViewRenderer(final int layoutID, @NonNull final Listener listener) {
		super(StargazerModel.class);
		mLayoutID = layoutID;
		mListener = listener;
	}

	@Override
	public void bindView(@NonNull final StargazerModel model, @NonNull final StargazerViewHolder holder) {
		Log.d(TAG, "bindView " + model.toString());
		bindInner(model, holder);
	}

	@Override
	public void rebindView(@NonNull final StargazerModel model,
	                       @NonNull final StargazerViewHolder holder,
	                       @NonNull final List<Object> payloads) {
		Log.d(TAG, "bindView " + model.toString());
		bindInner(model, holder);
	}

	private void bindInner(@NonNull final StargazerModel model, @NonNull final StargazerViewHolder holder) {
		holder.name.setText(model.getName());
		final String url = model.getAvatarUrl();
		Glide.with(getContext())
				.asBitmap()
				.load(url)
				.apply(bitmapTransform(new BlurTransformation(25)))
				.into(holder.avatar);
		holder.check.setVisibility(GONE);

		holder.itemView.setOnClickListener(view -> {
			final boolean willChecked = holder.check.getVisibility() == GONE;
			holder.check.setVisibility(willChecked ? VISIBLE : GONE);
			mListener.onStargazerItemClicked(model, willChecked);
		});

		/* vivchar: temporary workaround */
		holder.check.setOnClickListener(view -> {
			final boolean willChecked = holder.check.getVisibility() == GONE;
			holder.check.setVisibility(willChecked ? VISIBLE : GONE);
			mListener.onStargazerItemClicked(model, willChecked);
		});
	}

	@NonNull
	@Override
	public StargazerViewHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new StargazerViewHolder(inflate(mLayoutID, parent));
	}

	@Nullable
	@Override
	public ViewState createViewState(@NonNull final StargazerViewHolder holder) {
		return new StargazerViewState(holder);
	}

	@Override
	public int createViewStateID(@NonNull final StargazerModel model) {
		return model.getID();
	}

	public interface Listener {
		void onStargazerItemClicked(@NonNull StargazerModel model, final boolean isChecked);
	}
}