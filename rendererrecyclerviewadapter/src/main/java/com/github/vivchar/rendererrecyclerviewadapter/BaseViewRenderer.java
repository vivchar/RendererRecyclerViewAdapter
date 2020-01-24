package com.github.vivchar.rendererrecyclerviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseViewRenderer<M extends ViewModel, VF extends ViewFinder, VH extends ViewHolder<VF>> implements ViewStateProvider<M, VH> {

    protected Context mContext;
    @NonNull
    protected final Type mType;
    @LayoutRes
    protected final int mLayoutID;
    @NonNull
    protected final ViewRenderer.Binder<M, VF> mBinder;
    @Nullable
    protected ViewStateProvider<M, VH> mViewStateProvider = null;


    public BaseViewRenderer(@LayoutRes final int layoutID,
                            @NonNull final Class<M> type,
                            @NonNull final ViewRenderer.Binder<M, VF> binder) {
        mType = type;
        mLayoutID = layoutID;
        mBinder = binder;
    }

    public BaseViewRenderer(@LayoutRes final int layoutID,
                            @NonNull final Class<M> type,
                            @NonNull final ViewRenderer.Binder<M, VF> binder,
                            @Nullable final ViewStateProvider<M, VH> viewStateProvider) {
        mType = type;
        mLayoutID = layoutID;
        mBinder = binder;
        mViewStateProvider = viewStateProvider;
    }

    public void setContext(@NonNull final Context context) {
        mContext = context;
    }

    /**
     * @return Context or null if called before {@link #createViewHolder(ViewGroup)} method
     */
    protected Context getContext() {
        return mContext;
    }

    @NonNull
    protected ViewHolder performCreateViewHolder(@NonNull final ViewGroup parent) {
        setContext(parent.getContext());
        return createViewHolder(parent);
    }

    /**
     * This method will be called for a partial bind if you override the {@link com.github.vivchar.rendererrecyclerviewadapter
     * .RendererRecyclerViewAdapter.DiffCallback#getChangePayload(ViewModel,
     * ViewModel)} method
     *
     * @param model    your a ViewModel
     * @param holder   your a ViewHolder
     * @param payloads your payload
     */
    protected void performRebindView(@NonNull final M model, @NonNull final VH holder, @NonNull final List<Object> payloads) {
        bindViewInner(model, holder, payloads);
    }

    /**
     * This method will be called for a full bind.
     *
     * @param model  your a ViewModel
     * @param holder your a ViewHolder
     */
    protected void performBindView(@NonNull final M model, @NonNull final VH holder) {
        holder.setType(model.getClass());
        holder.setViewStateID(createViewStateID(model));
        bindViewInner(model, holder, new ArrayList<>());
    }

    public void bindViewInner(@NonNull final M model, @NonNull final VH holder, @NonNull final List<Object> payloads) {
        mBinder.bindView(model, holder.getViewFinder(), payloads);
    }

    @NonNull
    public abstract VH createViewHolder(@NonNull final ViewGroup parent);

    /**
     * Called when a view created by your adapter has been recycled.
     */
    public void viewRecycled(@NonNull final ViewHolder<VF> holder) {
    }

    @NonNull
    public Type getType() {
        return mType;
    }

    @Nullable
    public ViewState createViewState(@NonNull final VH holder) {
        return mViewStateProvider != null ? mViewStateProvider.createViewState(holder) : null;
    }

    @Override
    public int createViewStateID(@NonNull final M model) {
        return mViewStateProvider != null ? mViewStateProvider.createViewStateID(model) : ViewHolder.UNDEFINED;
    }

    @NonNull
    protected View inflate(@LayoutRes final int layoutID, @NonNull final ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(layoutID, parent, false);
    }

    public interface Binder<M, VF extends ViewFinder> {

        void bindView(@NonNull M model, @NonNull VF finder, @NonNull final List<Object> payloads);
    }
}

