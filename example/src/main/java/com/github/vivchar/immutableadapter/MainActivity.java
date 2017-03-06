package com.github.vivchar.immutableadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.github.vivchar.immutableadapter.items.content.ContentModel;
import com.github.vivchar.immutableadapter.items.content.ContentViewRenderer;
import com.github.vivchar.immutableadapter.items.category.CategoryModel;
import com.github.vivchar.immutableadapter.items.category.CategoryViewRenderer;
import com.github.vivchar.immutableadapter.items.header.HeaderModel;
import com.github.vivchar.immutableadapter.items.header.HeaderViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

import java.util.ArrayList;

public
class MainActivity
		extends AppCompatActivity
{

	public static final int MAX_SPAN_COUNT = 3;
	private RendererRecyclerViewAdapter mRecyclerViewAdapter;
	private RecyclerView mRecyclerView;
	private GridLayoutManager mLayoutManager;

	@Override
	protected
	void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mRecyclerViewAdapter = new RendererRecyclerViewAdapter();
		mRecyclerViewAdapter.registerRenderer(new HeaderViewRenderer(HeaderModel.TYPE, this));
		mRecyclerViewAdapter.registerRenderer(new CategoryViewRenderer(CategoryModel.TYPE, this, mListener));
		mRecyclerViewAdapter.registerRenderer(new ContentViewRenderer(ContentModel.TYPE, this, mListener));

		mLayoutManager = new GridLayoutManager(this, MAX_SPAN_COUNT);
		mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
		{
			@Override
			public
			int getSpanSize(final int position) {
				switch (mRecyclerViewAdapter.getItemViewType(position)) {
					case ContentModel.TYPE:
						return 1;
					case CategoryModel.TYPE:
					default:
						return 3;
				}
			}
		});

		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mRecyclerViewAdapter);
		mRecyclerView.addItemDecoration(new EqualSpacesItemDecoration(20));

		mRecyclerViewAdapter.setItems(getItems());
		mRecyclerViewAdapter.notifyDataSetChanged();
	}

	@NonNull
	private
	ArrayList<ItemModel> getItems() {
		final ArrayList<ItemModel> items = new ArrayList<>();
			items.add(new HeaderModel("header"));
		for (int i = 0; i < 5; i++) {
			items.add(new CategoryModel("some category #" + (i + 1)));
			for (int j = 0; j < 12; j++) {
				items.add(new ContentModel("content " + (j + 1)));
			}
		}

		return items;
	}

	@NonNull
	private final ContentViewRenderer.Listener mListener = new ContentViewRenderer.Listener()
	{
		@Override
		public
		void onCategoryClicked(@NonNull final CategoryModel model) {
			Toast.makeText(MainActivity.this, model.getName(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public
		void onContentItemClicked(@NonNull final ContentModel model) {
			Toast.makeText(MainActivity.this, model.getName(), Toast.LENGTH_SHORT).show();
		}
	};
}
