package com.github.vivchar.example.pages.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vivchar.example.BaseScreenFragment;
import com.github.vivchar.example.R;
import com.github.vivchar.example.pages.simple.items.ViewStateViewModel;
import com.github.vivchar.example.pages.simple.items.ViewStateViewRenderer;
import com.github.vivchar.example.pages.simple.items.SimpleViewModel;
import com.github.vivchar.example.pages.simple.items.SimpleViewRenderer;
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */

public class ViewStateFragment extends BaseScreenFragment {

	private YourDataProvider mYourDataProvider = new YourDataProvider();

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		final RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();

		adapter.registerRenderer(
				new ViewStateViewRenderer(
						ViewStateViewModel.class,
						getContext()
				).registerRenderer(new SimpleViewRenderer(
						SimpleViewModel.class,
						getContext()
				))
		);

		adapter.setItems(mYourDataProvider.generateViewStateItems());

		final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

		return view;
	}
}