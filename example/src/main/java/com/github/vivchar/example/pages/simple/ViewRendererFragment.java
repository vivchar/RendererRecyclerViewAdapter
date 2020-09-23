package com.github.vivchar.example.pages.simple;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.vivchar.example.BaseScreenFragment;
import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration;
import com.github.vivchar.example.widgets.MyAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
public class ViewRendererFragment extends BaseScreenFragment {

	public static final String TAG = ViewRendererFragment.class.getSimpleName();

	private YourDataProvider mYourDataProvider = new YourDataProvider();

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		final RendererRecyclerViewAdapter adapter = new MyAdapter();

		adapter.registerRenderer(new ViewRenderer<>(R.layout.item_simple, RectViewModel.class,
				(model, finder, payloads) -> finder
						.setText(R.id.text, model.getText())
						.setOnClickListener(R.id.text, () -> {
							Toast.makeText(getContext(), "Text Clicked " + model.getText(), Toast.LENGTH_SHORT).show();
						})
		));

		adapter.setItems(mYourDataProvider.getSquareItems());

		final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

		return view;
	}

	public static class RectViewModel implements ViewModel {

		private int mID;
		private final String mText;

		public RectViewModel(final int ID, final String text) {
			mID = ID;
			mText = text;
		}

		public int getID() {
			return mID;
		}

		public String getText() {
			return mText;
		}
	}
}