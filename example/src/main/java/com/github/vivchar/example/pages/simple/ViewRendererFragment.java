package com.github.vivchar.example.pages.simple;

import android.content.Context;
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
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */

public class ViewRendererFragment extends BaseScreenFragment {

	private YourDataProvider mYourDataProvider = new YourDataProvider();

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		final RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();

		adapter.registerRenderer(new RectViewRenderer(RectViewModel.class, getContext()));
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		adapter.setItems(mYourDataProvider.generateSquareItems());

		final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

		return view;
	}

	public static class RectViewRenderer extends ViewRenderer<RectViewModel, RectViewHolder> {

		public RectViewRenderer(@NonNull final Class<RectViewModel> type, @NonNull final Context context) {
			super(type, context);
		}

		@Override
		public void bindView(@NonNull final RectViewModel model, @NonNull final RectViewHolder holder) {
			holder.textView.setText(model.getText());
		}

		@NonNull
		@Override
		public RectViewHolder createViewHolder(@Nullable final ViewGroup parent) {
			return new RectViewHolder(inflate(R.layout.item_simple, parent));
		}
	}

	public static class RectViewHolder extends ViewHolder {

		public final TextView textView;

		public RectViewHolder(final View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.text);
		}
	}

	public static class RectViewModel implements ViewModel {

		private final String mText;

		public RectViewModel(final String text) {
			mText = text;
		}

		public String getText() {
			return mText;
		}
	}
}