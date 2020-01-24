package com.github.vivchar.rendererrecyclerviewadapter.binder;

import androidx.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 18.01.18.
 */

public abstract class BinderAdapter <M> implements ViewRenderer.Binder<M, ViewFinder> {

	@Override
	public void bindView(@NonNull final M model, @NonNull final ViewFinder finder, @NonNull final List<Object> payloads) {
		bindView(model, finder);
	}

	public void bindView(@NonNull final M model, @NonNull final ViewFinder finder) {}
}
