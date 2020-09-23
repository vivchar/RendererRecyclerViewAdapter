package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 29.12.17.
 */
public class LoadMoreViewBinder extends ViewRenderer<LoadMoreViewModel, ViewFinder> {

    public LoadMoreViewBinder(final int layoutID,
                              @NonNull final Class<LoadMoreViewModel> type,
                              @NonNull final Binder<LoadMoreViewModel, ViewFinder> binder) {
        super(layoutID, type, binder);
    }

    public LoadMoreViewBinder(final int layoutID,
                              @NonNull final Class<LoadMoreViewModel> type) {
        this(layoutID, type, new Binder<LoadMoreViewModel, ViewFinder>() {
            @Override
            public void bindView(@NonNull final LoadMoreViewModel model,
                                 @NonNull final ViewFinder finder,
                                 @NonNull final List<Object> payloads) {
            }
        });
    }

    public LoadMoreViewBinder(final int layoutID) {
        this(layoutID, LoadMoreViewModel.class);
    }

}