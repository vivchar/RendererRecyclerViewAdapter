package com.github.vivchar.immutableadapter.items.small;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vivchar.immutableadapter.R;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public class SmallViewHolder
		extends RecyclerView.ViewHolder
{

	public final TextView mTextView;

	public SmallViewHolder(final View itemView) {
		super(itemView);
		mTextView = (TextView) itemView.findViewById(R.id.content);
	}
}
