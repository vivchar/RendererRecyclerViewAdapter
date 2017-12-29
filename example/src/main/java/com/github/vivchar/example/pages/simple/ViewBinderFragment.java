package com.github.vivchar.example.pages.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewProvider;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
public class ViewBinderFragment extends ViewRendererFragment {

	private YourDataProvider mYourDataProvider = new YourDataProvider();

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		final RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();

		adapter.registerRenderer(new ViewBinder<>(R.layout.item_simple, RectViewModel.class, getContext(),
				(model, finder, payloads) -> finder
						.find(R.id.text, (ViewProvider<TextView>) textView -> textView.setText(model.getText()))
						.setOnClickListener(R.id.text, v -> {
							Toast.makeText(getContext(), "Text Clicked " + model.getText(), Toast.LENGTH_SHORT).show();
						})
		));
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		adapter.setItems(mYourDataProvider.getSquareItems());

		final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

		return view;
	}
}