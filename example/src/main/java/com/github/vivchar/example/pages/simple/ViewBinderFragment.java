package com.github.vivchar.example.pages.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
public class ViewBinderFragment extends ViewRendererFragment {

	public static final String TAG = ViewBinderFragment.class.getSimpleName();

	private YourDataProvider mYourDataProvider = new YourDataProvider();

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		final RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();

		adapter.registerRenderer(new ViewBinder<>(R.layout.item_simple, RectViewModel.class,
				(model, finder, payloads) -> finder
						.setText(R.id.text, model.getText())
						.setOnClickListener(R.id.text, v -> {
							Toast.makeText(getContext(), "Text Clicked " + model.getText(), Toast.LENGTH_SHORT).show();
						})
		));

//		adapter.registerRenderer(new ViewBinder<>(
//				R.layout.item_simple,
//				RectViewModel.class,
//				(model, finder, payloads) -> finder
//						.setText(R.id.text, model.getText())
//						.setChecked(R.id.checked_button, false) /* default state */
//						.setOnClickListener(R.id.text, v -> {
//							Toast.makeText(getContext(), "Text Clicked " + model.getText(), Toast.LENGTH_SHORT).show();
//							final SparseArray<ViewState> states = adapter.getViewStates();
//							for (int i = 0; i < states.size(); i++) {
//								final int key = states.keyAt(i);
//								final CheckedState value = (CheckedState)states.get(key);
//								Log.d(TAG, key + " " + value.isChecked());
//							}
//						})
//				, new CheckedStateProvider()
//		));
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		adapter.setItems(mYourDataProvider.getSquareItems());

		final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

		return view;
	}
//
//	public class CheckedState implements ViewState<ViewHolder> {
//
//		private final boolean mChecked;
//
//		public CheckedState(@NonNull final ViewHolder holder) {
//			final SwitchCompat switchButton = holder.getViewFinder().find(R.id.checked_button);
//			mChecked = switchButton.isChecked();
//		}
//
//		@Override
//		public void restore(@NonNull final ViewHolder holder) {
//			holder.getViewFinder().setChecked(R.id.checked_button, mChecked);
//		}
//
//		public boolean isChecked() {
//			return mChecked;
//		}
//	}
//
//	private class CheckedStateProvider implements ViewStateProvider<RectViewModel, ViewHolder> {
//
//		@Override
//		public ViewState createViewState(@NonNull final ViewHolder holder) {
//			return new CheckedState(holder);
//		}
//
//		@Override
//		public int createViewStateID(@NonNull final RectViewModel model) {
//			Log.d(TAG, "createViewStateID: " + model.getID());
//			return model.getID();
//		}
//	}
}