package com.github.vivchar.rendererrecyclerviewadapter;

import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/25/17.
 */
public class CompositeViewRenderer<M extends CompositeViewModel, VF extends ViewFinder>
        extends BaseViewRenderer<M, VF, CompositeViewHolder<VF>> {

    @IdRes
    protected final int mRecyclerViewID;
    @NonNull
    protected final List<RecyclerView.ItemDecoration> mDecorations = new ArrayList<>();
    @Nullable
    protected CompositeViewStateProvider<M, CompositeViewHolder<VF>> mViewStateProvider = null;
    @NonNull
    protected final ArrayList<BaseViewRenderer> mRenderers = new ArrayList<>();
    @Nullable
    protected RecyclerView.RecycledViewPool mRecycledViewPool;

    public CompositeViewRenderer(final int layoutID,
                                 @IdRes final int recyclerViewID,
                                 @NonNull final Class<M> type,
                                 @NonNull final ViewRenderer.Binder<M, VF> binder,
                                 @NonNull final List<? extends RecyclerView.ItemDecoration> decorations,
                                 @Nullable final CompositeViewStateProvider<M, CompositeViewHolder<VF>> viewStateProvider) {
        super(layoutID, type, binder);
        mRecyclerViewID = recyclerViewID;
        mViewStateProvider = viewStateProvider;
        mDecorations.addAll(decorations);
    }

    public CompositeViewRenderer(final int layoutID,
                                 @IdRes final int recyclerViewID,
                                 @NonNull final Class<M> type,
                                 @NonNull final ViewRenderer.Binder<M, VF> binder) {
        super(layoutID, type, binder);
        mRecyclerViewID = recyclerViewID;
    }

    public CompositeViewRenderer(final int layoutID,
                                 @IdRes final int recyclerViewID,
                                 @NonNull final Class<M> type,
                                 @NonNull final ViewRenderer.Binder<M, VF> binder,
                                 @NonNull final ViewRenderer... renderers) {
        super(layoutID, type, binder);
        mRecyclerViewID = recyclerViewID;
//        mRenderers.addAll(new ArrayList<ViewRenderer>(renderers));
    }

    public CompositeViewRenderer(final int layoutID,
                                 @IdRes final int recyclerViewID,
                                 @NonNull final Class<M> type) {
        this(layoutID, recyclerViewID, type, new ViewRenderer.Binder<M, VF>() {
            @Override
            public void bindView(@NonNull final M model, @NonNull final VF finder, @NonNull final List<Object> payloads) {
            }
        });
    }

    public CompositeViewRenderer(final int layoutID,
                                 @IdRes final int recyclerViewID,
                                 @NonNull final Class<M> type,
                                 @NonNull final List<? extends RecyclerView.ItemDecoration> decorations) {
        this(layoutID, recyclerViewID, type);
        mDecorations.addAll(decorations);
    }

    public CompositeViewRenderer(final int layoutID,
                                 @IdRes final int recyclerViewID,
                                 @NonNull final Class<M> type,
                                 @Nullable final CompositeViewStateProvider<M, CompositeViewHolder<VF>> viewStateProvider) {
        this(layoutID, recyclerViewID, type);
        mViewStateProvider = viewStateProvider;
    }

    public CompositeViewRenderer(final int layoutID,
                                 @IdRes final int recyclerViewID,
                                 @NonNull final Class<M> type,
                                 @NonNull final List<? extends RecyclerView.ItemDecoration> decorations,
                                 @Nullable final CompositeViewStateProvider<M, CompositeViewHolder<VF>> viewStateProvider) {
        this(layoutID, recyclerViewID, type);
        mViewStateProvider = viewStateProvider;
        mDecorations.addAll(decorations);
    }

    @Override
    public void rebindView(@NonNull final M model, @NonNull final CompositeViewHolder<VF> holder, @NonNull final List<Object> payloads) {
        super.rebindView(model, holder, payloads);
        bindAdapterItems(model, holder.getAdapter());
    }

    @Override
    protected void bindView(@NonNull final M model, @NonNull final CompositeViewHolder<VF> holder) {
        super.bindView(model, holder);
        bindAdapterItems(model, holder.getAdapter());
    }

    /**
     * You can use the DiffUtil, just extend this class and override this method
     * @return true if we should enable DiffUtil
     */
    protected boolean isDiffUtilEnabled() {
        return false;
    }

    /**
     * @param model - item models which RecyclerView should contains
     * @param adapter - adapter of RecyclerView
     */
    protected void bindAdapterItems(@NonNull final M model, @NonNull final RendererRecyclerViewAdapter adapter) {
        if (isDiffUtilEnabled()) {
            adapter.enableDiffUtil(true);
            adapter.setItems(model.getItems());
        } else {
            adapter.setItems(model.getItems());
            adapter.notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public CompositeViewHolder<VF> createViewHolder(@NonNull final ViewGroup parent) {
        final RendererRecyclerViewAdapter adapter = createAdapter();
        for (final BaseViewRenderer renderer : mRenderers) {
            adapter.registerRenderer(renderer);
        }

        final CompositeViewHolder<VF> viewHolder = createCompositeViewHolder(parent);
        viewHolder.setAdapter(adapter);

        if (viewHolder.getRecyclerView() != null) {
            viewHolder.getRecyclerView().setLayoutManager(createLayoutManager());
            viewHolder.getRecyclerView().setAdapter(adapter);
            viewHolder.getRecyclerView().setRecycledViewPool(mRecycledViewPool);
            for (final RecyclerView.ItemDecoration itemDecoration : createItemDecorations()) {
                viewHolder.getRecyclerView().addItemDecoration(itemDecoration);
            }
        }

        return viewHolder;
    }

    @NonNull
    public CompositeViewRenderer registerRenderer(@NonNull final BaseViewRenderer renderer) {
        mRenderers.add(renderer);
        return this;
    }

    /**
     * Use {@link CompositeViewState} if you want to save scroll state of Nested RecyclerView
     */
    @Nullable
    @Override
    public ViewState createViewState(@NonNull final CompositeViewHolder<VF> holder) {
        return super.createViewState(holder);
    }

    @NonNull
    protected CompositeViewHolder<VF> createCompositeViewHolder(@NonNull final ViewGroup parent) {
        return new CompositeViewHolder<>(mRecyclerViewID, inflate(mLayoutID, parent));
    }

    @NonNull
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }

    @NonNull
    protected RendererRecyclerViewAdapter createAdapter() {
        return new RendererRecyclerViewAdapter();
    }

    @NonNull
    protected List<? extends RecyclerView.ItemDecoration> createItemDecorations() {
        return mDecorations;
    }

    /**
     * Recycled view pools allow multiple RecyclerViews to share a common pool of scrap views.
     * This can be useful if you have multiple RecyclerViews with adapters that use the same
     * view types, for example if you have several data sets with the same kinds of item views
     * displayed by a {@link ViewPager ViewPager}.
     *
     * @param pool Pool to set. If this parameter is null a new pool will be created and used.
     */
    @NonNull
    public CompositeViewRenderer<M, VF> setRecycledViewPool(@Nullable final RecyclerView.RecycledViewPool pool) {
        mRecycledViewPool = pool;
        return this;
    }
}
