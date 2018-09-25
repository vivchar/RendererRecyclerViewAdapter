package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vivchar Vitaly on 20.10.17.
 */
public interface ViewState <VH extends RecyclerView.ViewHolder> {
	void restore(@NonNull VH holder);
}