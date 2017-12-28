package com.github.vivchar.example.pages.simple.items;

import android.view.View;
import android.widget.TextView;

import com.github.vivchar.example.R;
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */
public class SimpleViewHolder extends ViewHolder {

	public final TextView textView;

	public SimpleViewHolder(final View itemView) {
		super(itemView);
		textView = (TextView) itemView.findViewById(R.id.text);
	}
}