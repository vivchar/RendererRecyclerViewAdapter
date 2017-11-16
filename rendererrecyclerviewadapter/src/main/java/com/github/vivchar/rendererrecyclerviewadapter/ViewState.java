package com.github.vivchar.rendererrecyclerviewadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Vivchar Vitaly on 20.10.17.
 */
public interface ViewState <VH extends RecyclerView.ViewHolder> {
	void restore(@NonNull final ViewModel model, @NonNull VH holder);
}