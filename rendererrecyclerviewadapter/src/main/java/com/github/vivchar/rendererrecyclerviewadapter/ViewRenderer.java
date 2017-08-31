package com.github.vivchar.rendererrecyclerviewadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public
abstract class ViewRenderer <M extends ItemModel, VH extends RecyclerView.ViewHolder>
{
	private final int mViewType;
	@NonNull
	private final Context mContext;

	public ViewRenderer(final int viewType, @NonNull final Context context) {
		mViewType = viewType;
		mContext = context;
	}

	@NonNull
	protected Context getContext() {
		return mContext;
	}

	public void bindView(final M item, final VH holder, final List payloads) {
	}

	public abstract void bindView(@NonNull M model, @NonNull VH holder);

	@NonNull
	public abstract VH createViewHolder(@Nullable ViewGroup parent);

	public int getType() {
		return mViewType;
	}

	@NonNull
	protected View inflate(@LayoutRes final int layout, @Nullable final ViewGroup parent, final boolean attachToRoot) {
		return LayoutInflater.from(getContext()).inflate(layout, parent, attachToRoot);
	}

	@NonNull
	protected View inflate(@LayoutRes final int layout, final @Nullable ViewGroup parent) {
		return inflate(layout, parent, false);
	}
}
