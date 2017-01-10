package com.example.vivchar.immutableadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
