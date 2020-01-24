package com.github.vivchar.rendererrecyclerviewadapter;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public class ViewRenderer<M extends ViewModel, VF extends ViewFinder> extends BaseViewRenderer<M, VF, ViewHolder<VF>> {

    public ViewRenderer(@LayoutRes final int layoutID,
                        @NonNull final Class<M> type,
                        @NonNull final ViewRenderer.Binder<M, VF> binder) {
        super(layoutID, type, binder);
    }

    public ViewRenderer(@LayoutRes final int layoutID,
                        @NonNull final Class<M> type,
                        @NonNull final ViewRenderer.Binder<M, VF> binder,
                        @Nullable final ViewStateProvider<M, ViewHolder<VF>> viewStateProvider) {
        super(layoutID, type, binder, viewStateProvider);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public ViewHolder<VF> createViewHolder(@NonNull final ViewGroup parent) {
        return (ViewHolder<VF>) new ViewHolder(inflate(mLayoutID, parent));
    }
}
