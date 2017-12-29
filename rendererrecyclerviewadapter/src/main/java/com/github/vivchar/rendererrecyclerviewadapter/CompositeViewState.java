package com.github.vivchar.rendererrecyclerviewadapter;

import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Vivchar Vitaly on 20.10.17.
 *
 */
public class CompositeViewState <VH extends CompositeViewHolder> implements ViewState<VH> {

	@NonNull
	protected final Parcelable mLayoutManagerState;

	public <VH extends CompositeViewHolder> CompositeViewState(@NonNull final VH holder) {
		mLayoutManagerState = holder.getRecyclerView().getLayoutManager().onSaveInstanceState();
	}

	@Override
	public void restore(@NonNull final VH holder) {
		holder.getRecyclerView().getLayoutManager().onRestoreInstanceState(mLayoutManagerState);
	}
}