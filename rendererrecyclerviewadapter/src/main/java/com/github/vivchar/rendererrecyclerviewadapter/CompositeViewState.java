package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Vivchar Vitaly on 20.10.17.
 */
public class CompositeViewState<VH extends CompositeViewHolder> implements ViewState<VH>, Serializable {

    @NonNull
    protected HashMap<Integer, ViewState> mViewStates;
    protected int mPosition;
    protected int mTopOffset;
    protected int mLeftOffset;


    @Override
    public void clear(@NonNull final VH holder) {
        final RecyclerView.LayoutManager layoutManager = holder.getRecyclerView().getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            @NonNull final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
        }
    }

    @Override
    public void save(@NonNull final VH holder) {
        mViewStates = holder.getAdapter().getStates();
        final RecyclerView.LayoutManager layoutManager = holder.getRecyclerView().getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            /* To save rid of Parcelable, https://stackoverflow.com/a/35287828/4894238 */
            @NonNull final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            mPosition = linearLayoutManager.findFirstVisibleItemPosition();
            final View item = holder.getRecyclerView().getChildAt(0);
            mTopOffset = (item == null) ? 0 : (item.getTop() - holder.getRecyclerView().getPaddingTop());
            mLeftOffset = (item == null) ? 0 : (item.getLeft() - holder.getRecyclerView().getPaddingLeft());
//			Log.d("###", "save mPosition: " + mPosition + " mTopOffset: " + mTopOffset + " mLeftOffset " + mLeftOffset);
        } else {
            mPosition = 0;
            mTopOffset = 0;
        }
    }

    @Override
    public void restore(@NonNull final VH holder) {
        holder.getAdapter().setStates(mViewStates);

        final RecyclerView.LayoutManager layoutManager = holder.getRecyclerView().getLayoutManager();
        if (mPosition != -1 && layoutManager instanceof LinearLayoutManager) {
            @NonNull final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
//			Log.d("###", "restore mPosition: " + mPosition + " mTopOffset: " + mTopOffset + " mLeftOffset " + mLeftOffset);
            if (linearLayoutManager.canScrollHorizontally()) {
                linearLayoutManager.scrollToPositionWithOffset(mPosition, mLeftOffset);
            } else {
                linearLayoutManager.scrollToPositionWithOffset(mPosition, mTopOffset);
            }
        }
    }
}