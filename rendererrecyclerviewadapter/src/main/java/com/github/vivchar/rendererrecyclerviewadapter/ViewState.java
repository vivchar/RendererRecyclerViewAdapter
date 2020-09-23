package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vivchar Vitaly on 20.10.17.
 * <p>
 * Use ViewState for non "Data Model" cases, ex: when you need to save a scroll position,
 * position of an animation or something similar
 * <p>
 * If you can use ViewModel(Data Model), then you shouldn't use ViewState
 */
public interface ViewState<VH extends RecyclerView.ViewHolder> {
    void clear(@NonNull VH holder);
    void save(@NonNull VH holder);
    void restore(@NonNull VH holder);
}