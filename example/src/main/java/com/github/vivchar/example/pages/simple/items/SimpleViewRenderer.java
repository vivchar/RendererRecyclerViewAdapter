package com.github.vivchar.example.pages.simple.items;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.github.vivchar.example.R;
import com.github.vivchar.example.pages.simple.ViewRendererFragment;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
public class SimpleViewRenderer extends ViewRenderer<SimpleViewModel, SimpleViewHolder> {

	public SimpleViewRenderer(@NonNull final Class<SimpleViewModel> type, @NonNull final Context context) {
		super(type, context);
	}

	@Override
	public void bindView(@NonNull final SimpleViewModel model, @NonNull final SimpleViewHolder holder) {
		holder.textView.setText(model.getText());
	}

	@NonNull
	@Override
	public SimpleViewHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new SimpleViewHolder(inflate(R.layout.item_simple, parent));
	}
}