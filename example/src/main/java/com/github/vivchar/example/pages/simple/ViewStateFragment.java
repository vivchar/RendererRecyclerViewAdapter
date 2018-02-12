package com.github.vivchar.example.pages.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vivchar.example.BaseScreenFragment;
import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewState;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;
import com.github.vivchar.rendererrecyclerviewadapter.binder.CompositeViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.CompositeViewStateProvider;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewProvider;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */

public class ViewStateFragment extends BaseScreenFragment {

	private final YourDataProvider mYourDataProvider = new YourDataProvider();

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		final RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();

		adapter.registerRenderer(
				new CompositeViewBinder<>(
						R.layout.item_simple_composite,
						R.id.recycler_view,
						StateViewModel.class,
						Collections.singletonList(new BetweenSpacesItemDecoration(10, 10)),
						new CompositeViewStateProvider<StateViewModel, CompositeViewHolder>() {
							@Override
							public ViewState createViewState(@NonNull final CompositeViewHolder holder) {
								return new CompositeViewState(holder);
							}

							@Override
							public int createViewStateID(@NonNull final StateViewModel model) {
								return model.getID();
							}
						}
				).registerRenderer(getAnyViewRenderer())
		);
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		adapter.setItems(mYourDataProvider.getStateItems());

		final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

		return view;
	}

	private ViewRenderer getAnyViewRenderer() {
		return new ViewBinder<>(R.layout.item_simple_square, DiffUtilFragment.DiffViewModel.class,
				(model, finder, payloads) -> finder.setText(R.id.text, model.getText())
		);
	}

	public static class StateViewModel extends DefaultCompositeViewModel {

		private final int mID;

		public StateViewModel(final int ID, @NonNull final List<? extends ViewModel> items) {
			super(items);
			mID = ID;
		}

		private int getID() {
			return mID;
		}
	}
}