package com.example.vivchar.rendererrecyclerviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public
abstract
class ViewRenderer <M extends ItemModel, VH extends RecyclerView.ViewHolder>
{
	private final int mViewType;
	@NonNull
	private final Context mContext;

	public
	ViewRenderer(final int viewType, @NonNull final Context context) {
		mViewType = viewType;
		mContext = context;
	}

	public
	boolean isSupportType(final int viewType) {
		return mViewType == viewType;
	}

	@NonNull
	public
	Context getContext() {
		return mContext;
	}

	public abstract
	void bindView(@NonNull M model, @NonNull VH holder);

	@NonNull
	public abstract
	VH createViewHolder(@Nullable ViewGroup parent);
}
