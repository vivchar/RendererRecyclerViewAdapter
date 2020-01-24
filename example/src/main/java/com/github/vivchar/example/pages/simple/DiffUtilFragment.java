package com.github.vivchar.example.pages.simple;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vivchar.example.BaseScreenFragment;
import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.ItemOffsetDecoration;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */

public class DiffUtilFragment extends BaseScreenFragment {

	private YourDataProvider mYourDataProvider = new YourDataProvider();
	private RendererRecyclerViewAdapter mAdapter;
	private RecyclerView mRecyclerView;

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		mAdapter = new RendererRecyclerViewAdapter();

		mAdapter.setDiffCallback(new DiffCallback());
//		adapter.enableDiffUtil(); /* Or just call it to enable DiffUtil with DefaultDiffCallback */

		mAdapter.registerRenderer(new ViewRenderer<>(R.layout.item_simple_square, DiffViewModel.class,
				(model, finder, payloads) -> finder
						.setText(R.id.text, model.getText())
						.setOnClickListener(R.id.text, v -> {
							reloadItems(model);
						})
		));
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		mAdapter.setItems(mYourDataProvider.getDiffItems());

		mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
		mRecyclerView.addItemDecoration(new ItemOffsetDecoration(10));

		return view;
	}

	private void reloadItems(@NonNull final DiffViewModel model) {
		mAdapter.setItems(mYourDataProvider.getUpdatedDiffItems(model));
	}

	private class DiffCallback extends DefaultDiffCallback<DiffViewModel> {

		@Override
		public boolean areItemsTheSame(@NonNull final DiffViewModel oldItem, @NonNull final DiffViewModel newItem) {
			return oldItem.getID() == newItem.getID();
		}
	}

	public static class DiffViewModel implements ViewModel {

		private final int mID;
		private final String mText;

		public DiffViewModel(final int ID, final String text) {
			mID = ID;
			mText = text;
		}

		public String getText() {
			return mText;
		}

		public int getID() {
			return mID;
		}

		@Override
		public boolean equals(final Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			final DiffViewModel that = (DiffViewModel) o;

			if (mID != that.mID) {
				return false;
			}
			return mText != null ? mText.equals(that.mText) : that.mText == null;
		}

		@Override
		public int hashCode() {
			int result = mID;
			result = 31 * result + (mText != null ? mText.hashCode() : 0);
			return result;
		}
	}
}