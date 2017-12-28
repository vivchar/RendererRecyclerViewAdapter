package com.github.vivchar.example.pages.simple;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vivchar.example.BaseScreenFragment;
import com.github.vivchar.example.R;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */

public class ViewRendererFragment extends BaseScreenFragment {

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {
		final View inflate = inflater.inflate(R.layout.fragment_github, container, false);

		final RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();
		adapter.registerRenderer(createSimpleViewRenderer());

		final RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		adapter.setItems(generateSimpleItems());

		return inflate;
	}

	protected List<? extends ViewModel> generateSimpleItems() {
		final ArrayList<ViewModel> list = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			list.add(new SimpleViewModel(SimpleViewModel.class.getSimpleName() + " #" + i));
		}
		return list;
	}

	protected ViewRenderer createSimpleViewRenderer() {
		return new SimpleViewRenderer(SimpleViewModel.class, getContext());
	}

	protected class SimpleViewRenderer extends ViewRenderer<SimpleViewModel, SimpleViewHolder> {

		public SimpleViewRenderer(@NonNull final Class<SimpleViewModel> type, @NonNull final Context context) {
			super(type, context);
		}

		@Override
		public void bindView(@NonNull final SimpleViewModel model, @NonNull final SimpleViewHolder holder) {
			holder.textView.setText(model.getText());
		}

		@NonNull
		@Override
		public SimpleViewHolder createViewHolder(@Nullable final ViewGroup parent) {
			return new SimpleViewHolder(inflate(R.layout.item_simple, parent));
		}
	}

	protected class SimpleViewModel implements ViewModel {

		private String mText;

		private SimpleViewModel(final String text) {
			mText = text;
		}

		public String getText() {
			return mText;
		}
	}

	protected class SimpleViewHolder extends ViewHolder {

		private final TextView textView;

		public SimpleViewHolder(final View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.text);
		}
	}
}