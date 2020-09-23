package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;

/**
 * Created by Vivchar Vitaly on 30.12.17.
 */
public interface ViewStateProvider<M extends ViewModel, VH extends ViewHolder> {
    ViewState createViewState();
    int createViewStateID(@NonNull M model);
}