package com.github.vivchar.example.pages.github.items.list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration;
import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.NestedAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewState;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewFinder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/24/17.
 */

public class RecyclerViewRenderer extends CompositeViewRenderer<RecyclerViewModel, ViewFinder> {

	public RecyclerViewRenderer() {
		super(R.layout.item_composite, R.id.recycler_view, RecyclerViewModel.class);
	}
//
//	@Override
//	public void rebindView(@NonNull final RecyclerViewModel model, @NonNull final RecyclerViewHolder holder, @NonNull final List<Object> payloads) {
//		Log.d(TAG, "bindView " + model.toString() + ", payload: " + payloads.toString());
//		holder.getAdapter().enableDiffUtil();
//		holder.getAdapter().setItems(model.getItems());
//	}
//
//	@Override
//	public void bindView(@NonNull final RecyclerViewModel model, @NonNull final RecyclerViewHolder holder) {
//		Log.d(TAG, "bindView " + model.toString());
//		holder.getAdapter().disableDiffUtil();
//		holder.getAdapter().setItems(model.getItems());
//		holder.getAdapter().notifyDataSetChanged();
//	}

	@Nullable
	@Override
	public ViewState createViewState(@NonNull final CompositeViewHolder<ViewFinder> holder) {
		return new CompositeViewState<>(holder);
	}

	@Override
	public int createViewStateID(@NonNull final RecyclerViewModel model) {
		return model.getID();
	}

	@NonNull
	@Override
	protected RendererRecyclerViewAdapter createAdapter() {
		final NestedAdapter nestedAdapter = new NestedAdapter();
		nestedAdapter.setDiffCallback(new DefaultDiffCallback<RecyclerViewModel>());
		return nestedAdapter;
	}

	@NonNull
	@Override
	protected List<? extends RecyclerView.ItemDecoration> createItemDecorations() {
		return Collections.singletonList(new BetweenSpacesItemDecoration(0, 10));
	}
}