package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.LoadMoreViewModel;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 29.12.17.
 */

public class LoadMoreViewBinder extends ViewBinder<LoadMoreViewModel> {

	public LoadMoreViewBinder(final int layoutID,
	                          @NonNull final Class<LoadMoreViewModel> type,
	                          @NonNull final Context context,
	                          @NonNull final Binder<LoadMoreViewModel> binder) {
		super(layoutID, type, context, binder);
	}

	public LoadMoreViewBinder(final int layoutID,
	                          @NonNull final Class<LoadMoreViewModel> type,
	                          @NonNull final Context context) {
		this(layoutID, type, context, new Binder<LoadMoreViewModel>() {
			@Override
			public void bindView(@NonNull final LoadMoreViewModel model,
			                     @NonNull final ViewFinder finder,
			                     @NonNull final List<Object> payloads) {}
		});
	}

	public LoadMoreViewBinder(final int layoutID, @NonNull final Context context) {
		this(layoutID, LoadMoreViewModel.class, context);
	}
}