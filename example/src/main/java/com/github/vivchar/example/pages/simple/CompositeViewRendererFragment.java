package com.github.vivchar.example.pages.simple;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vivchar.example.R;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */

public class CompositeViewRendererFragment extends ViewRendererFragment {

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {
		final View inflate = inflater.inflate(R.layout.fragment_github, container, false);

		final RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();
		adapter.registerRenderer(
				createCompositeViewRenderer().registerRenderer(createSimpleViewRenderer())
		);

		final RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		adapter.setItems(generateCompositeSimpleItems());

		return inflate;
	}

	private List<? extends ViewModel> generateCompositeSimpleItems() {
		final ArrayList<ViewModel> items = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			items.add(new DefaultCompositeViewModel(generateSimpleItems()));
		}
		return items;
	}

	private CompositeViewRenderer createCompositeViewRenderer() {
		return new SimpleCompositeViewRenderer(DefaultCompositeViewModel.class, getContext());
	}

	private class SimpleCompositeViewRenderer extends CompositeViewRenderer<DefaultCompositeViewModel, SimpleCompositeViewHolder> {

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
		protected SimpleCompositeViewHolder createCompositeViewHolder(@Nullable final ViewGroup parent) {
			return new SimpleCompositeViewHolder(inflate(R.layout.item_recycler_view, parent));
		}
	}

	private class SimpleCompositeViewHolder extends CompositeViewHolder {

		public SimpleCompositeViewHolder(final View itemView) {
			super(itemView);
			recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
		}
	}
}
