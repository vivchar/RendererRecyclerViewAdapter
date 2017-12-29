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
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewProvider;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */
public class CompositeViewRendererFragment extends BaseScreenFragment {

	private YourDataProvider mYourDataProvider = new YourDataProvider();

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		final RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();

		adapter.registerRenderer(
				new SimpleCompositeViewRenderer(DefaultCompositeViewModel.class, getContext())
						.registerRenderer(getAnyViewRenderer())
//						.registerRenderer(...)
//						.registerRenderer(...)
		);
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		adapter.setItems(mYourDataProvider.getCompositeSimpleItems());

		final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

		return view;
	}

	private ViewRenderer getAnyViewRenderer() {
		return new ViewBinder<>(
				R.layout.item_simple_square,
				DiffUtilFragment.DiffViewModel.class,
				getContext(),
				(model, finder, payloads) -> finder.find(R.id.text, (ViewProvider<TextView>) textView -> textView.setText(model.getText()))
		);
	}

	public class SimpleCompositeViewRenderer extends CompositeViewRenderer<DefaultCompositeViewModel, SimpleCompositeViewHolder> {

		public SimpleCompositeViewRenderer(@NonNull final Class<DefaultCompositeViewModel> type, @NonNull final Context context) {
			super(type, context);
		}

		@Override
		public void bindView(@NonNull final DefaultCompositeViewModel model, @NonNull final SimpleCompositeViewHolder holder) {
			holder.getAdapter().setItems(model.getItems());
			holder.getAdapter().notifyDataSetChanged();
		}

		@NonNull
		@Override
		protected List<? extends RecyclerView.ItemDecoration> createItemDecorations() {
			return Collections.singletonList(new BetweenSpacesItemDecoration(10, 10));
		}

		@NonNull
		@Override
		protected SimpleCompositeViewHolder createCompositeViewHolder(@Nullable final ViewGroup parent) {
			return new SimpleCompositeViewHolder(inflate(R.layout.item_simple_composite, parent));
		}
	}

	public class SimpleCompositeViewHolder extends CompositeViewHolder {

		public SimpleCompositeViewHolder(final View itemView) {
			super(itemView);
			recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
		}
	}
}