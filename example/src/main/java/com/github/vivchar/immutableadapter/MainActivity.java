package com.github.vivchar.immutableadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.github.vivchar.immutableadapter.items.BaseItemModel;
import com.github.vivchar.immutableadapter.items.category.CategoryModel;
import com.github.vivchar.immutableadapter.items.category.CategoryViewRenderer;
import com.github.vivchar.immutableadapter.items.content.ContentModel;
import com.github.vivchar.immutableadapter.items.content.ContentViewRenderer;
import com.github.vivchar.immutableadapter.items.header.HeaderModel;
import com.github.vivchar.immutableadapter.items.header.HeaderViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public
class MainActivity
		extends AppCompatActivity
{

	public static final int MAX_SPAN_COUNT = 3;
	private RendererRecyclerViewAdapter mRecyclerViewAdapter;
	private RecyclerView mRecyclerView;
	private GridLayoutManager mLayoutManager;
	private SwipeRefreshLayout mSwipeToRefresh;

	@Override
	protected
	void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mSwipeToRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
		mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
		{
			@Override
			public
			void onRefresh() {
				updateItems();
			}
		});

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

		updateItems();
	}

	private
	void updateItems() {
//		mRecyclerViewAdapter.setItems(getItems());
//		mRecyclerViewAdapter.notifyDataSetChanged();

		mRecyclerViewAdapter.setItems(getItems(), mDiffCallback);
		mSwipeToRefresh.setRefreshing(false);
	}

	@NonNull
	private
	ArrayList<BaseItemModel> getItems() {
		final ArrayList<BaseItemModel> items = new ArrayList<>();
		final int headerID = 1;
		items.add(new HeaderModel(headerID, "header"));

		final int categoryCount = random(3, 9);
		for (int i = 0; i < categoryCount; i++) {
			final int categoryID = i * 10;
			items.add(new CategoryModel(categoryID, "some category #" + (i + 1)));

			final ArrayList<BaseItemModel> content = new ArrayList<>();
			final int contentCount = random(1, 9);
			for (int j = 0; j < contentCount; j++) {
				final int contentID = i * 10 + j;
				content.add(new ContentModel(contentID, "content " + (j + 1)));
			}
			Collections.shuffle(content);
			items.addAll(content);
		}

		return items;
	}

	private
	int random(final int min, final int max) {
		final Random random = new Random();
		return random.nextInt(max - min + 1) + min;
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

	@NonNull
	private final RendererRecyclerViewAdapter.DiffCallback<BaseItemModel> mDiffCallback = new RendererRecyclerViewAdapter
			.DiffCallback<BaseItemModel>()
	{

		private final ArrayList<BaseItemModel> mOldItems = new ArrayList<>();
		private final ArrayList<BaseItemModel> mNewItems = new ArrayList<>();

		@Override
		public
		void setItems(@NonNull final List<BaseItemModel> oldItems, @NonNull final List<BaseItemModel> newItems) {
			mOldItems.clear();
			mOldItems.addAll(oldItems);

			mNewItems.clear();
			mNewItems.addAll(newItems);
		}

		@Override
		public
		int getOldListSize() {
			return mOldItems.size();
		}

		@Override
		public
		int getNewListSize() {
			return mNewItems.size();
		}

		@Override
		public
		boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
			final BaseItemModel oldItem = mOldItems.get(oldItemPosition);
			final BaseItemModel newItem = mNewItems.get(newItemPosition);
			return oldItem.getID() == newItem.getID();
		}

		@Override
		public
		boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
			final ItemModel oldItem = mOldItems.get(oldItemPosition);
			final ItemModel newItem = mNewItems.get(newItemPosition);
			return oldItem.equals(newItem);
		}
	};

}
