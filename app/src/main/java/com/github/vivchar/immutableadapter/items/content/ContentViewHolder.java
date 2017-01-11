package com.github.vivchar.immutableadapter.items.content;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vivchar.immutableadapter.R;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public
class ContentViewHolder
		extends RecyclerView.ViewHolder
{

	public final TextView mTextView;

	public
	ContentViewHolder(final View itemView) {
		super(itemView);
		mTextView = (TextView) itemView.findViewById(R.id.content);
	}
}
