package com.github.vivchar.example.pages.simple;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vivchar.example.BaseScreenFragment;
import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.EndlessScrollListener;
import com.github.vivchar.example.widgets.ItemOffsetDecoration;
import com.github.vivchar.rendererrecyclerviewadapter.binder.LoadMoreViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;

import java.lang.reflect.Type;

/**
 * Created by Vivchar Vitaly on 12/29/17.
 */

public class LoadMoreFragment extends BaseScreenFragment {

	public static final int COLUMNS_COUNT = 4;
	private final YourDataProvider mYourDataProvider = new YourDataProvider();
	private RendererRecyclerViewAdapter mAdapter;
	private RecyclerView mRecyclerView;
	private GridLayoutManager mLayoutManager;

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		mAdapter = new RendererRecyclerViewAdapter();
		mAdapter.enableDiffUtil();


//		mAdapter.setLoadMoreModel(new YourLoadMoreModel()); /* you can change the LoadMoreModel if needed */
		mAdapter.registerRenderer(new LoadMoreViewBinder(R.layout.item_load_more));
		mAdapter.registerRenderer(new ViewBinder<>(R.layout.item_simple_square, SimpleViewModel.class,
				(model, finder, payloads) -> finder.setText(R.id.text, model.getText())
		));
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		mAdapter.setItems(mYourDataProvider.getLoadMoreItems());

		mLayoutManager = new GridLayoutManager(getContext(), COLUMNS_COUNT);
		mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(final int position) {
				final Type type = mAdapter.getType(position);
				if (type.equals(SimpleViewModel.class)) {
					return 1;
				}
				return COLUMNS_COUNT;
			}
		});

		mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.addItemDecoration(new ItemOffsetDecoration(10));
		mRecyclerView.addOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(final int page, final int totalItemsCount) {
				Log.d("#####", "onLoadMore " + page);
				mAdapter.showLoadMore();
//				mAdapter.hideLoadMore(); /* if you need force hide progress or call setItems() */
				mYourDataProvider.getLoadMoreItems(list -> getActivity().runOnUiThread(() -> mAdapter.setItems(list)));
			}
		});

		return view;
	}

	public static class SimpleViewModel implements ViewModel {

		private final String mText;

		public SimpleViewModel(final String text) {
			mText = text;
		}

		public String getText() {
			return mText;
		}
	}
}
