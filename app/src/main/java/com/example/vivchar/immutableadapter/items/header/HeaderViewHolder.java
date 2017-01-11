package com.example.vivchar.immutableadapter.items.header;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.vivchar.immutableadapter.R;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public
class HeaderViewHolder
		extends RecyclerView.ViewHolder
{

	public final TextView mTitle;

	public
	HeaderViewHolder(final View itemView) {
		super(itemView);
		mTitle = (TextView) itemView.findViewById(R.id.title);
	}
}
