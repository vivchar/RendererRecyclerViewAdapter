package com.github.vivchar.rendererrecyclerviewadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vivchar Vitaly on 12/26/17.
 * <p>
 * Use {@link com.github.vivchar.rendererrecyclerviewadapter.binder.FinderHolder}
 */
@Deprecated
public class ViewHolder extends RecyclerView.ViewHolder {

	public static final int UNDEFINED = -1;

	private int mViewStateID = UNDEFINED;
	private Class<? extends ViewModel> mType;

	public ViewHolder(final View itemView) {
		super(itemView);
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
