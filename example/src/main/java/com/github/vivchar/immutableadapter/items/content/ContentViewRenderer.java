package com.github.vivchar.immutableadapter.items.content;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.github.vivchar.immutableadapter.R;
import com.github.vivchar.immutableadapter.items.category.CategoryViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public
class ContentViewRenderer
		extends ViewRenderer<ContentModel, ContentViewHolder>
{
	@NonNull
	private final Listener mListener;

	public
	ContentViewRenderer(final int type, final Context context, @NonNull final Listener listener) {
		super(type, context);
		mListener = listener;
	}

	@Override
	public
	void bindView(@NonNull final ContentModel model, @NonNull final ContentViewHolder holder) {
		holder.mTextView.setText(model.getName());
		holder.itemView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public
			void onClick(final View view) {
				mListener.onContentItemClicked(model);
			}
		});
	}

	@NonNull
	@Override
	public
	ContentViewHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new ContentViewHolder(inflate(R.layout.item_content, parent));
	}

	public
	interface Listener
			extends CategoryViewRenderer.Listener
	{
		void onContentItemClicked(@NonNull ContentModel model);
	}
}
