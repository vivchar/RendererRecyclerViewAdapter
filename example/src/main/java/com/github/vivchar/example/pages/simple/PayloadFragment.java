package com.github.vivchar.example.pages.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vivchar.example.BaseScreenFragment;
import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.ItemOffsetDecoration;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewProvider;

/**
 * Created by Vivchar Vitaly on 12/29/17.
 */

public class PayloadFragment extends BaseScreenFragment {

	public static final Integer TEXT_CHANGED = 1;
	public static final Integer DESCRIPTION_CHANGED = 2;

	private YourDataProvider mYourDataProvider = new YourDataProvider();
	private RendererRecyclerViewAdapter mAdapter;
	private RecyclerView mRecyclerView;

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		mAdapter = new RendererRecyclerViewAdapter(getContext());

		mAdapter.setDiffCallback(new PayloadDiffCallback());

		mAdapter.registerRenderer(new ViewBinder<>(
				R.layout.item_payload_square, PayloadViewModel.class,
				(model, finder, payloads) -> {
					finder.setOnClickListener(R.id.text, v -> changeItem(model));
					final TextView textView = finder.find(R.id.text);

					if (payloads.isEmpty()) {
						/* full bind */
						textView.setText(model.getText());
						finder.setText(R.id.desciption, model.getDescription());
					} else {
						/* partially bind */
						final Object payload = payloads.get(0);
						if (payload.equals(TEXT_CHANGED)) {
							textView.setRotation(0);
							textView.animate().rotation(360).start();
							textView.setText(model.getText());
						} else if (payload.equals(DESCRIPTION_CHANGED)) {
							finder.setText(R.id.desciption, model.getDescription());
						}
					}
				}
		));
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		mAdapter.setItems(mYourDataProvider.getPayloadItems());

		mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
		mRecyclerView.addItemDecoration(new ItemOffsetDecoration(10));

		return view;
	}

	private void changeItem(@NonNull final PayloadViewModel model) {
		mAdapter.setItems(mYourDataProvider.getChangedPayloadItems(model));
	}

	private class PayloadDiffCallback extends DefaultDiffCallback<PayloadViewModel> {

		@Override
		public boolean areItemsTheSame(@NonNull final PayloadViewModel oldItem, @NonNull final PayloadViewModel newItem) {
			return oldItem.getID() == newItem.getID();
		}

		@Nullable
		@Override
		public Object getChangePayload(@NonNull final PayloadViewModel oldItem, @NonNull final PayloadViewModel newItem) {
			if (!oldItem.getText().equals(newItem.getText())) {
				return TEXT_CHANGED;
			}
			if (!oldItem.getDescription().equals(newItem.getDescription())) {
				return DESCRIPTION_CHANGED;
			}
			return super.getChangePayload(oldItem, newItem);
		}
	}

	public static class PayloadViewModel implements ViewModel {

		private final int mID;
		private final String mText;
		private final String mDescription;

		public PayloadViewModel(final int ID, final String text, final String description) {
			mID = ID;
			mText = text;
			mDescription = description;
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

			final PayloadViewModel that = (PayloadViewModel) o;

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

		public String getDescription() {
			return mDescription;
		}
	}
}
