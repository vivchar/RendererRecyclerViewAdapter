package com.github.vivchar.example.pages.github.items;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration;
import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.NestedAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewState;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/24/17.
 */

public class RecyclerViewRenderer extends CompositeViewRenderer<RecyclerViewModel, ViewFinder> {

	public RecyclerViewRenderer() {
		super(R.layout.item_composite, R.id.recycler_view, RecyclerViewModel.class);
	}

	@Override
	protected void bindAdapterItems(@NonNull final RendererRecyclerViewAdapter adapter, @NonNull final List<? extends ViewModel> models) {
		adapter.setItems(models);
	}

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