package com.github.vivchar.immutableadapter.items.header;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vivchar.immutableadapter.R;

/**
 * Created by Vivchar Vitaly on 3/6/17.
 */
public
class HeaderViewHolder
		extends RecyclerView.ViewHolder
{

	public final TextView mTextView;

	public
	HeaderViewHolder(final View itemView) {
		super(itemView);
		mTextView = (TextView) itemView.findViewById(R.id.content);
	}
}
