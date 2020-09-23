package com.github.vivchar.example.pages.simple;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.vivchar.example.BaseScreenFragment;
import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration;
import com.github.vivchar.example.widgets.MyAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class InputsFragment extends BaseScreenFragment {

	public static final String TAG = ViewRendererFragment.class.getSimpleName();

	private final YourDataProvider mYourDataProvider = new YourDataProvider();

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		final RendererRecyclerViewAdapter adapter = new MyAdapter();

		adapter.registerRenderer(new ViewRenderer<>(
				R.layout.item_input,
				InputViewModel.class,
				(model, finder, payloads) -> finder.setText(R.id.text, model.getValue()),
				getViewStateProvider()
		));

		adapter.setItems(mYourDataProvider.getInputsModels());

		final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

		return view;
	}

	@NonNull
	private ViewStateProvider<InputViewModel, ViewHolder<ViewFinder>> getViewStateProvider() {
		return new ViewStateProvider<InputViewModel, ViewHolder<ViewFinder>>() {
			@Override
			public ViewState createViewState() {
				return new ViewState<ViewHolder<ViewFinder>>() {
					private String savedValue; /* here will be saved a value for each item by createViewStateID() */

					@Override
					public void clear(@NonNull final ViewHolder<ViewFinder> holder) {
						holder.getViewFinder().setText(R.id.text, "");
					}

					@Override
					public void save(@NonNull final ViewHolder<ViewFinder> holder) {
						savedValue = holder.getViewFinder().<EditText>find(R.id.text).getText().toString();
					}

					@Override
					public void restore(@NonNull final ViewHolder<ViewFinder> holder) {
						holder.getViewFinder().setText(R.id.text, savedValue);
					}
				};
			}

			@Override
			public int createViewStateID(@NonNull final InputViewModel model) {
				return model.getID();
			}
		};
	}

	public static class InputViewModel implements ViewModel {

		private final int mID;
		@NonNull
		private final String mValue;

		public InputViewModel(final int ID, @NonNull final String value) {
			mID = ID;
			mValue = value;
		}

		private int getID() {
			return mID;
		}

		@NonNull
		public String getValue() {
			return mValue;
		}
	}
}
