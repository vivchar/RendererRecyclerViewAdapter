package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Universal ViewRenderer without ViewHolder
 * <p>
 * More detail you can get there: https://github.com/vivchar/ViewFinder
 */
public class ViewBinder <M extends ViewModel> extends ViewRenderer<M, ViewFinder> {

	@LayoutRes
	private final int mLayoutID;
	@NonNull
	private final Binder mBinder;

	public ViewBinder(@LayoutRes final int layoutID,
	                  @NonNull final Class<M> type,
	                  @NonNull final Context context,
	                  @NonNull final Binder<M> binder) {
		super(type, context);
		mLayoutID = layoutID;
		mBinder = binder;
	}

	@Override
	public void bindView(@NonNull final M model, @NonNull final ViewFinder finder) {
		mBinder.bindView(model, finder, new ArrayList<>());
	}

	@Override
	public void rebindView(@NonNull final M model, @NonNull final ViewFinder finder, @NonNull final List<Object> payloads) {
		mBinder.bindView(model, finder, payloads);
	}

	@NonNull
	@Override
	public ViewFinder createViewHolder(@Nullable final ViewGroup parent) {
		return new ViewFinder(inflate(mLayoutID, parent));
	}

	public interface Binder <M> {

		void bindView(@NonNull M model, @NonNull ViewFinder finder, @NonNull final List<Object> payloads);
	}
}