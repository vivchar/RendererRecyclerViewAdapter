package com.github.vivchar.example.pages.github.items.category;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vivchar.example.R;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public
class CategoryViewHolder
		extends RecyclerView.ViewHolder
{

	public final TextView mName;
	public final View mViewAll;

	public
	CategoryViewHolder(final View itemView) {
		super(itemView);
		mName = (TextView) itemView.findViewById(R.id.title);
		mViewAll = itemView.findViewById(R.id.viewAll);
	}
}
