package com.github.vivchar.immutableadapter.items.small;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.github.vivchar.immutableadapter.R;
import com.github.vivchar.immutableadapter.items.category.CategoryViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public class SmallViewRenderer
		extends ViewRenderer<SmallModel, SmallViewHolder>
{
	@NonNull
	private final Listener mListener;

	public SmallViewRenderer(final int type, final Context context, @NonNull final Listener listener) {
		super(type, context);
		mListener = listener;
	}

	@Override
	public void bindView(final SmallModel item, final SmallViewHolder holder, final List payloads) {
		final Bundle o = (Bundle) payloads.get(0);
		for (String key : o.keySet()) {
			if (key.equals(SmallModel.KEY_NAME)) {
				/* use some animations to change text */
				holder.mTextView.setText(item.getName());
			}
		}
	}

	@Override
	public void bindView(@NonNull final SmallModel model, @NonNull final SmallViewHolder holder) {
		holder.mTextView.setText(model.getName());
		holder.itemView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view) {
				mListener.onSmallItemClicked(model);
			}
		});
	}

	@NonNull
	@Override
	public SmallViewHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new SmallViewHolder(inflate(R.layout.item_small, parent));
	}

	public interface Listener
			extends CategoryViewRenderer.Listener
	{
		void onSmallItemClicked(@NonNull SmallModel model);
	}
}
