package com.github.vivchar.rendererrecyclerviewadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewFinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewFinderFactory;

/**
 * Created by Vivchar Vitaly on 12/26/17.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

	public static final int UNDEFINED = -1;

	private int mViewStateID = UNDEFINED;
	private Class<? extends ViewModel> mType;
	@Nullable
	private ViewFinder mViewFinder;

	public ViewHolder(final View itemView) {
		super(itemView);
	}

	@NonNull
	public ViewFinder getViewFinder() {
		if (mViewFinder == null) {
			mViewFinder = ViewFinderFactory.create(itemView);
		}
		return mViewFinder;
	}

	/**
	 * It helps to support ViewState, we will save a ViewState by this ID.
	 */
	void setViewStateID(final int ID) {
		mViewStateID = ID;
	}

	int getViewStateID() {
		return mViewStateID;
	}

	void setType(@NonNull final Class<? extends ViewModel> type) {
		mType = type;
	}

	/**
	 * @return - type of bound ViewModel
	 */
	Class<? extends ViewModel> getType() {
		return mType;
	}

	boolean isSupportViewState() {
		return mType != null && mViewStateID != UNDEFINED;
	}
}