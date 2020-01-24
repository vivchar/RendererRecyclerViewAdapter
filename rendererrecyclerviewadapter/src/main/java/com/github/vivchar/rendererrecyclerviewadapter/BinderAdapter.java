package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 18.01.18.
 * <p>
 * use it when you don't need in the payloads param
 */

public abstract class BinderAdapter<M> implements ViewRenderer.Binder<M, ViewFinder> {

    @Override
    public void bindView(@NonNull final M model, @NonNull final ViewFinder finder, @NonNull final List<Object> payloads) {
        bindView(model, finder);
    }

    public void bindView(@NonNull final M model, @NonNull final ViewFinder finder) {
    }
}
