package com.github.vivchar.example.pages.github.items.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.vivchar.example.R;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;


/**
 * Created by Vivchar Vitaly on 8/24/17.
 */

public class RecyclerViewHolder extends CompositeViewHolder {

	public RecyclerViewHolder(@NonNull final View view) {
		super(view);
		recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
	}
}