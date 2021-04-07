package com.github.vivchar.rendererrecyclerviewadapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vivchar Vitaly on 12/26/17.
 */
public class ViewHolder <VF extends ViewFinder> extends RecyclerView.ViewHolder {

	public static final int UNDEFINED = -1;

	private int mViewStateID = UNDEFINED;
	private Class<? extends ViewModel> mType;
	@Nullable
	private VF mViewFinder;

	public ViewHolder(final View itemView) {
		super(itemView);
	}

	@SuppressWarnings("unchecked")
	@NonNull
	public VF getViewFinder() {
		if (mViewFinder == null) {
			try {
				mViewFinder = (VF) ViewFinderFactory.create(itemView);
			} catch (ClassCastException e) { //TODO vivchar: by some reason it does not catch
				throw new WrongViewFinderException(e);
			}
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