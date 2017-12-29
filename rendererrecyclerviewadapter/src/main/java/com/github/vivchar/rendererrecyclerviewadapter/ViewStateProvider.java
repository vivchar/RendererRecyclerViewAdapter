package com.github.vivchar.rendererrecyclerviewadapter;

import android.support.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.binder.CompositeFinderHolder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.FinderHolder;

/**
 * Created by Vivchar Vitaly on 30.12.17.
 */
public interface ViewStateProvider <M, VH> {
	ViewState createViewState(@NonNull VH holder);
	int createViewStateID(@NonNull M model);
}