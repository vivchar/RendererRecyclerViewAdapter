package com.github.vivchar.example.pages.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vivchar.example.BaseScreenFragment;
import com.github.vivchar.example.R;
import com.github.vivchar.example.pages.simple.items.SimpleViewModel;
import com.github.vivchar.example.pages.simple.items.SimpleViewRenderer;
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */

public class DiffUtilFragment extends BaseScreenFragment {

	private YourDataProvider mYourDataProvider = new YourDataProvider();

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		final RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();

		adapter.setDiffCallback(new DiffCallback());

		adapter.registerRenderer(new SimpleViewRenderer(SimpleViewModel.class, getContext()));

		adapter.setItems(mYourDataProvider.generateSimpleItems());

		final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

		return view;
	}

	private class DiffCallback extends DefaultDiffCallback<SimpleViewModel> {

		@Override
		public boolean areItemsTheSame(@NonNull final SimpleViewModel oldItem, @NonNull final SimpleViewModel newItem) {
			return super.areItemsTheSame(oldItem, newItem);
		}
	}
}
