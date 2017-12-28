package com.github.vivchar.example.pages.simple.items;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.github.vivchar.example.R;
import com.github.vivchar.example.pages.simple.CompositeViewRendererFragment;
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
public class SimpleCompositeViewRenderer extends CompositeViewRenderer<DefaultCompositeViewModel, SimpleCompositeViewHolder> {

	public SimpleCompositeViewRenderer(@NonNull final Class<DefaultCompositeViewModel> type, @NonNull final Context context) {
		super(type, context);
	}

	@Override
	public void bindView(@NonNull final DefaultCompositeViewModel model, @NonNull final SimpleCompositeViewHolder holder) {
		holder.getAdapter().setItems(model.getItems());
		holder.getAdapter().notifyDataSetChanged();
	}

	@NonNull
	@Override
	protected List<? extends RecyclerView.ItemDecoration> createItemDecorations() {
		return Collections.singletonList(new BetweenSpacesItemDecoration(10, 10));
	}

	@NonNull
	@Override
	protected SimpleCompositeViewHolder createCompositeViewHolder(@Nullable final ViewGroup parent) {
		return new SimpleCompositeViewHolder(inflate(R.layout.item_simple_composite, parent));
	}
}