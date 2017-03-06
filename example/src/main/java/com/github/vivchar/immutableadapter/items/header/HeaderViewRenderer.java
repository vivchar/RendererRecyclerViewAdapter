package com.github.vivchar.immutableadapter.items.header;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.github.vivchar.immutableadapter.R;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

/**
 * Created by Vivchar Vitaly on 3/6/17.
 */
public
class HeaderViewRenderer
		extends ViewRenderer<HeaderModel, HeaderViewHolder>
{
	public
	HeaderViewRenderer(final int type, final Context context) {
		super(type, context);
	}

	@Override
	public
	void bindView(@NonNull final HeaderModel model, @NonNull final HeaderViewHolder holder) {
		holder.mTextView.setText(model.getName());
	}

	@NonNull
	@Override
	public
	HeaderViewHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new HeaderViewHolder(inflate(R.layout.item_header, parent));
	}
}
