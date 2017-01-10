package com.example.vivchar.immutableadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vivchar.rendererrecyclerviewadapter.ViewRenderer;

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
		holder.itemView.setOnClickListener(new View.OnClickListener() {
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
		return new ContentViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_content, parent, false));
	}

	public
	interface Listener {
		void onContentItemClicked(@NonNull ContentModel model);
	}
}
