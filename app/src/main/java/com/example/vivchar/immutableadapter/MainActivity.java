package com.example.vivchar.immutableadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.vivchar.immutableadapter.items.content.ContentModel;
import com.example.vivchar.immutableadapter.items.content.ContentViewRenderer;
import com.example.vivchar.immutableadapter.items.header.HeaderModel;
import com.example.vivchar.immutableadapter.items.header.HeaderViewRenderer;
import com.example.vivchar.rendererrecyclerviewadapter.ItemModel;
import com.example.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

import java.util.ArrayList;

public
class MainActivity
		extends AppCompatActivity
{

	private RendererRecyclerViewAdapter mRecyclerViewAdapter;
	private RecyclerView mRecyclerView;

	@Override
	protected
	void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mRecyclerViewAdapter = new RendererRecyclerViewAdapter();
		mRecyclerViewAdapter.registerRenderer(new HeaderViewRenderer(HeaderModel.TYPE, this));
		mRecyclerViewAdapter.registerRenderer(new ContentViewRenderer(ContentModel.TYPE, this, mListener));

		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(mRecyclerViewAdapter);

		mRecyclerViewAdapter.setItems(getItems());
		mRecyclerViewAdapter.notifyDataSetChanged();
	}

	@NonNull
	private
	ArrayList<ItemModel> getItems() {
		final ArrayList<ItemModel> items = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			items.add(new HeaderModel("title " + (i + 1)));
			for (int j = 0; j < 10; j++) {
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
		void onContentItemClicked(@NonNull final ContentModel model) {
			Toast.makeText(MainActivity.this, model.getName(), Toast.LENGTH_SHORT).show();
		}
	};
}
