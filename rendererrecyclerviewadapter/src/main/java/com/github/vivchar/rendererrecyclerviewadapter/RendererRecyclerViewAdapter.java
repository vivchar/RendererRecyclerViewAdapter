package com.github.vivchar.rendererrecyclerviewadapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

/**
 * Created by Vivchar Vitaly on 1/9/17.
 */
@SuppressWarnings("unchecked")
public class RendererRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

	protected static final String ITEM_VIEW_STATES_KEY = "renderer_adapter_item_view_states_key";
	protected static final String RECYCLER_VIEW_STATE_KEY = "renderer_adapter_recycler_view_state_key";

	@NonNull
	protected DiffCallback<? extends ViewModel> mDiffCallback = new DefaultDiffCallback();
	@NonNull
	private AsyncListDiffer<ViewModel> mDiffer = createDiffer(mDiffCallback, false);
	@NonNull
	private final Collection<OnLatchListener> mOnLatchListeners = new LinkedList<>();
	@NonNull
	private final MainThreadExecutor mMainThreadExecutor = new MainThreadExecutor();
	@NonNull
	protected final ArrayList<ViewModel> mItems = new ArrayList<>();
	@NonNull
	protected final ArrayList<BaseViewRenderer> mRenderers = new ArrayList<>();
	@NonNull
	protected final ArrayList<Type> mTypes = new ArrayList<>();
	@NonNull
	protected final HashMap<Integer, ViewState> mViewStates = new HashMap<>();
	@NonNull
	protected final ArrayList<ViewHolder> mBoundViewHolders = new ArrayList<>();

	@Nullable
	protected WeakReference<RecyclerView> mRecyclerView = null;
	@Nullable
	protected ListUpdateCallback mUpdateCallback = null;
	@NonNull
	protected LoadMoreViewModel mLoadMoreModel = new LoadMoreViewModel();
	@Nullable
	protected RecyclerView.RecycledViewPool mNestedRecycledViewPool = null;

	protected boolean mDiffUtilEnabled = false;
	protected boolean mLoadMoreVisible = false;
	protected int mLoadMorePosition;
	@Nullable
	private Bundle mSavedInstanceState;

	private boolean mSubmitting = false;

	public RendererRecyclerViewAdapter() {}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int typeIndex) {
		final BaseViewRenderer renderer = mRenderers.get(typeIndex);
		if (isCompositeRenderer(renderer) && mNestedRecycledViewPool != null) {
			((CompositeViewRenderer) renderer).setRecycledViewPool(mNestedRecycledViewPool);
		}
		return renderer.performCreateViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {}

	@SuppressWarnings("ConstantConditions")
	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull final List payloads) {
		super.onBindViewHolder(holder, position, payloads);
		final ViewModel item = getItem(position);
		final BaseViewRenderer renderer = getRenderer(item);

		/* check null to support old versions */
		if (payloads == null || payloads.isEmpty()) {
			/* Full bind */
			clearViewStateifNeed(holder); /* we should clear previous view state before bindView */
			renderer.performBindView(item, holder);
			restoreViewState(holder); /* we should restore view state after bindView */
		} else {
			/* Partial bind */
			renderer.performRebindView(item, holder, payloads);
		}

		mBoundViewHolders.remove(holder);
		mBoundViewHolders.add(holder);
	}

	public void registerRenderer(@NonNull final BaseViewRenderer renderer) {
		final Type type = renderer.getType();

		if (!mTypes.contains(type)) {
			mTypes.add(type);
			mRenderers.add(renderer);
		} else {
			throw new RuntimeException("ViewRenderer already registered for this type: " + type);
		}
	}

	@NonNull
	protected BaseViewRenderer getRenderer(final int position) {
		final int typeIndex = getTypeIndex(position);
		return mRenderers.get(typeIndex);
	}

	@NonNull
	protected BaseViewRenderer getRenderer(@NonNull final ViewModel model) {
		final int typeIndex = getTypeIndex(model);
		return mRenderers.get(typeIndex);
	}

	@NonNull
	protected BaseViewRenderer getRenderer(@NonNull final Type type) {
		final int typeIndex = getTypeIndex(type);
		return mRenderers.get(typeIndex);
	}

	/**
	 * The ItemViewType is the term of the RecyclerView, We never use this term.
	 */
	@Override
	public int getItemViewType(final int position) {
		return getTypeIndex(position);
	}

	protected int getTypeIndex(final int position) {
		final ViewModel model = getItem(position);
		return getTypeIndex(model);
	}

	protected int getTypeIndex(@NonNull final ViewModel model) {
		return getTypeIndex(model.getClass());
	}

	protected int getTypeIndex(@NonNull final Type type) {
		final int typeIndex = mTypes.indexOf(type);

		if (typeIndex == -1) {
			throw new RuntimeException("ViewRenderer not registered for this type: " + type);
		}
		return typeIndex;
	}

	@NonNull
	public Type getType(final int position) {
		final int typeIndex = getTypeIndex(position);
		return mTypes.get(typeIndex);
	}

	@Override
	public void onViewRecycled(@NonNull final ViewHolder holder) {
		super.onViewRecycled(holder);

		final BaseViewRenderer renderer = getRenderer(holder.getType());
		renderer.viewRecycled(holder);

		final int position = holder.getAdapterPosition();
		if (position != NO_POSITION) {
			if (hasChildren(holder)) {
				onChildrenViewsRecycled(getChildAdapter((CompositeViewHolder) holder));
			}
			saveViewState(holder);
		}

		mBoundViewHolders.remove(holder);
	}

	@NonNull
	public <T extends ViewModel> T getItem(final int position) {
		return (T) getReadOnlyItems().get(position);
	}

	@Override
	public int getItemCount() {
		return getReadOnlyItems().size();
	}

	public void enableDiffUtil() {
		enableDiffUtil(false);
	}

	public void enableDiffUtil(final boolean async) {
		mDiffUtilEnabled = true;
		mDiffer = createDiffer(mDiffCallback, async);
	}

	public void disableDiffUtil() {
		mDiffUtilEnabled = false;
	}

	public void setDiffCallback(@NonNull final DiffCallback<? extends ViewModel> diffCallback, final boolean async) {
		mDiffCallback = diffCallback;
		enableDiffUtil(async);
	}

	public void setDiffCallback(@NonNull final DiffCallback<? extends ViewModel> diffCallback) {
		setDiffCallback(diffCallback, false);
	}

	public void setUpdateCallback(@NonNull final ListUpdateCallback updateCallback) {
		mUpdateCallback = updateCallback;
	}

	/**
	 * A helper method to perform actions after items latched.
	 * Use it if some operations are supposed to be performed after the items latched, like scrolling.
	 *
	 * @param items           The data to insert in adapter
	 * @param onLatchListener The function to be called after the items latched
	 */
	public void setItems(@NonNull final List<? extends ViewModel> items, @NonNull final OnLatchListener onLatchListener) {
		mOnLatchListeners.add(onLatchListener);
		setItems(items);
	}

	public void setItems(@NonNull final List<? extends ViewModel> items) {
		if (mDiffUtilEnabled) {
			mSubmitting = true;
			mDiffer.submitList(new ArrayList<>(items));
		} else {
			mItems.clear();
			mItems.addAll(items);
			dispatchLatched();
		}

		mLoadMoreVisible = false;
	}

	@NonNull
	protected ListUpdateCallback getListUpdateCallback() {
		return new ListUpdateCallback() {
			@Override
			public void onInserted(final int position, final int count) {
				dispatchLatched();
				if (mUpdateCallback != null) {
					mUpdateCallback.onInserted(position, count);
				}
				notifyItemRangeInserted(position, count);
			}

			@Override
			public void onRemoved(final int position, final int count) {
				dispatchLatched();
				if (mUpdateCallback != null) {
					mUpdateCallback.onRemoved(position, count);
				}
				notifyItemRangeRemoved(position, count);
			}

			@Override
			public void onMoved(final int fromPosition, final int toPosition) {
				dispatchLatched();
				if (mUpdateCallback != null) {
					mUpdateCallback.onMoved(fromPosition, toPosition);
				}
				notifyItemMoved(fromPosition, toPosition);
			}

			@Override
			public void onChanged(final int position, final int count, final Object payload) {
				dispatchLatched();
				if (mUpdateCallback != null) {
					mUpdateCallback.onChanged(position, count, payload);
				}
				notifyItemRangeChanged(position, count, payload);
			}
		};
	}

	/**
	 * Show a Load More Indicator.
	 * The Load More Indicator will be hidden automatically when you call the {@link #setItems(List)} method.
	 * Or you can manually hide it using the {@link #hideLoadMore()} method
	 * <p>
	 * FYI: If you want to add a Load More Indicator to other position, then you should override this method
	 */
	public void showLoadMore() {
		if (mDiffUtilEnabled) {
			final OnLatchListener listener = new OnLatchListener() {
				@Override
				public void onLatch() {
					if (!mLoadMoreVisible) {
						final List<ViewModel> items = new ArrayList<ViewModel>(getItemCount() + 1) {{
							addAll(getReadOnlyItems());
							add(mLoadMoreModel);
						}};
						mLoadMorePosition = getItemCount() - 1;
						setItems(items);
						mLoadMoreVisible = true;
					}
				}
			};
			if (mSubmitting) {
				mOnLatchListeners.add(listener);
			} else {
				listener.onLatch();
			}
		} else {
			mMainThreadExecutor.execute(new Runnable() {
				public void run() {
					if (!mLoadMoreVisible) {
						mLoadMoreVisible = true;
						mItems.add(mLoadMoreModel);
						mLoadMorePosition = getItemCount() - 1;
						notifyItemInserted(mLoadMorePosition);
					}
				}
			});
		}
	}

	public void hideLoadMore() {
		if (mLoadMoreVisible && mLoadMorePosition < getItemCount()) {
			if (mDiffUtilEnabled) {
				final List<ViewModel> items = new ArrayList<ViewModel>(getItemCount()) {{
					addAll(getReadOnlyItems());
					remove(mLoadMorePosition);
				}};
				setItems(items, new OnLatchListener() {
					@Override
					public void onLatch() {
						notifyItemRemoved(mLoadMorePosition);
						mLoadMoreVisible = false;
					}
				});
			} else {
				mMainThreadExecutor.execute(new Runnable() {
					public void run() {
						mItems.remove(mLoadMorePosition);
						notifyItemRemoved(mLoadMorePosition);
						mLoadMoreVisible = false;
					}
				});
			}
		}
	}

	/**
	 * Recycled view pools allow multiple RecyclerViews to share a common pool of scrap views.
	 * This can be useful if you have multiple RecyclerViews with adapters that use the same
	 * view types, for example if you have several data sets with the same kinds of item views
	 * displayed by a {@link ViewPager ViewPager}.
	 *
	 * @param pool Pool to set. If this parameter is null a new pool will be created and used.
	 */
	public void setNestedRecycledViewPool(@Nullable final RecyclerView.RecycledViewPool pool) {
		mNestedRecycledViewPool = pool;
	}

	/**
	 * If you want to show a some custom data in a Load More Indicator
	 * then you should set your custom {@link LoadMoreViewModel} and createViewState your custom {@link LoadMoreViewBinder}
	 * <p>
	 * Use the {@link #registerRenderer(BaseViewRenderer)} to set your custom {@link LoadMoreViewBinder}
	 *
	 * @param model - custom {@link LoadMoreViewModel}
	 */
	public void setLoadMoreModel(@NonNull final LoadMoreViewModel model) {
		mLoadMoreModel = model;
	}

	@NonNull
	public ArrayList<ViewHolder> getBoundViewHolders() {
		return new ArrayList<>(mBoundViewHolders);
	}

	@NonNull
	public HashMap<Integer, ViewState> getStates() {
		saveBoundViewState();
		return new HashMap<>(mViewStates);
	}

	public void setStates(@NonNull final HashMap<Integer, ViewState> states) {
		mViewStates.clear();
		mViewStates.putAll(states);
	}

	public void clearViewStates() {
		mViewStates.clear();
	}

	protected void saveBoundViewState() {
		for (final ViewHolder holder : mBoundViewHolders) {
			saveViewState(holder);
		}
	}

	protected void saveViewState(@NonNull final ViewHolder holder) {
		final BaseViewRenderer viewRenderer = getRenderer(holder.getType());
		final ViewState viewState = viewRenderer.createViewState();
		if (viewState != null) {
			if (holder.isSupportViewState()) {
				viewState.save(holder);
				mViewStates.put(holder.getViewStateID(), viewState);
			} else {
				throw new RuntimeException("You defined the " + viewState.getClass().getSimpleName() + " but didn't specify the ID."
				                           + " Please override onCreateViewStateID(model) method in your ViewRenderer.");
			}
		}
	}

	/**
	 * This method called after bindView and if current model of this holder hasn't saved viewState yet.
	 * <p>
	 * So we should reset state to default, because it may be a reused view with a foreign state.
	 * https://github.com/vivchar/RendererRecyclerViewAdapter/issues/15
	 *
	 * @param holder - holder with should be reset to default values
	 */
	protected void clearViewState(@NonNull final ViewHolder holder) {
		if (holder.isSupportViewState()) { /* has a foreign state, need to clear */
			final ViewState viewState = getRenderer(holder.getType()).createViewState();
			if (viewState != null) {
				viewState.clear(holder);
			}
		}
	}

protected void clearViewStateifNeed(@NonNull final ViewHolder holder) {
	if (holder.isSupportViewState()) {
		final ViewState viewState = mViewStates.get(holder.getViewStateID());
		final boolean hasSavedViewState = viewState != null;

		if (!hasSavedViewState) {
			clearViewState(holder);
			if (hasChildren(holder)) {
				getChildAdapter((CompositeViewHolder) holder).clearViewStates();
			}
		}
	}
}

protected void restoreViewState(@NonNull final ViewHolder holder) {
	if (holder.isSupportViewState()) {
		final ViewState viewState = mViewStates.get(holder.getViewStateID());
		final boolean hasSavedViewState = viewState != null;

		if (hasSavedViewState) {
			viewState.restore(holder);
		}
	}
}

	protected void saveRecyclerViewState(@NonNull final Bundle outState) {
		if (mRecyclerView != null && mRecyclerView .get() != null) {
			final Parcelable recyclerViewState = mRecyclerView.get().getLayoutManager().onSaveInstanceState();
			outState.putParcelable(RECYCLER_VIEW_STATE_KEY, recyclerViewState);
		}
	}

	protected void restoreRecyclerViewState(@Nullable final Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			final Parcelable parcelable = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE_KEY);
			if (parcelable != null && mRecyclerView != null && mRecyclerView .get() != null) {
				mRecyclerView.get().getLayoutManager().onRestoreInstanceState(parcelable);
				mSavedInstanceState = null;
			} else {
				mSavedInstanceState = savedInstanceState;
			}
		}
	}

	protected void onChildrenViewsRecycled(@NonNull final RendererRecyclerViewAdapter adapter) {
		final ArrayList<ViewHolder> boundViewHolders = adapter.getBoundViewHolders();
		for (final ViewHolder viewHolder : boundViewHolders) {
			adapter.onViewRecycled(viewHolder);
		}
	}

	protected boolean hasChildren(@NonNull final ViewHolder holder) {
		return holder instanceof CompositeViewHolder;
	}

	protected boolean isCompositeRenderer(@NonNull final BaseViewRenderer renderer) {
		return renderer instanceof CompositeViewRenderer;
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		mRecyclerView = new WeakReference<>(recyclerView);
		restoreRecyclerViewState(mSavedInstanceState);
	}

	@Override
	public void onDetachedFromRecyclerView(@NonNull final RecyclerView recyclerView) {
		super.onDetachedFromRecyclerView(recyclerView);
		if (mRecyclerView != null) {
			mRecyclerView.clear();
			mRecyclerView = null;
		}
	}

	@NonNull
	protected RendererRecyclerViewAdapter getChildAdapter(@NonNull final CompositeViewHolder holder) {
		return holder.getAdapter();
	}

	public void onSaveInstanceState(@NonNull final Bundle outState) {
		saveBoundViewState();
		outState.putSerializable(ITEM_VIEW_STATES_KEY, getStates());

		saveRecyclerViewState(outState);
	}

	public void onRestoreInstanceState(@Nullable final Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			final Serializable serializable = savedInstanceState.getSerializable(ITEM_VIEW_STATES_KEY);
			if (serializable != null && serializable instanceof HashMap) {
				setStates((HashMap) serializable);
			}
		}
		restoreRecyclerViewState(savedInstanceState);
	}

	@NonNull
	protected List<ViewModel> getReadOnlyItems() {
		return mDiffUtilEnabled
				? mDiffer.getCurrentList()
				: Collections.unmodifiableList(mItems);
	}

	private void dispatchLatched() {
		mSubmitting = false;
		if (mOnLatchListeners.isEmpty()) {
			return;
		}
		final Iterator<OnLatchListener> iterator = mOnLatchListeners.iterator();
		while (iterator.hasNext()) {
			iterator.next().onLatch();
			iterator.remove();
		}
	}

	@NonNull
	private AsyncListDiffer<ViewModel> createDiffer(@NonNull final DiffCallback<? extends ViewModel> diffCallback, final boolean async) {
		return (AsyncListDiffer<ViewModel>) new AsyncListDiffer<>(getListUpdateCallback(), getConfig(diffCallback, async));
	}

	@NonNull
	private <V extends ViewModel> AsyncDifferConfig<V> getConfig(@NonNull final DiffCallback<V> diffUtil, final boolean async) {
		final AsyncDifferConfig.Builder<V> builder = new AsyncDifferConfig.Builder<>(new DiffUtil.ItemCallback<V>() {
			@Override
			public boolean areItemsTheSame(@NonNull final V oldItem, @NonNull final V newItem) {
				return diffUtil.areItemsTheSame(oldItem, newItem);
			}

			@Override
			public boolean areContentsTheSame(@NonNull final V oldItem, @NonNull final V newItem) {
				return diffUtil.areContentsTheSame(oldItem, newItem);
			}

			@Nullable
			@Override
			public Object getChangePayload(@NonNull final V oldItem, @NonNull final V newItem) {
				return diffUtil.getChangePayload(oldItem, newItem);
			}
		});
		if (!async) {
			builder.setBackgroundThreadExecutor(mMainThreadExecutor);
		}
		return builder.build();
	}

	/**
	 * Use this method to support your custom ViewFinder, so you will have ability easily work with your custom views
	 *
	 * @param creator
	 */
	public void registerViewFinder(@NonNull final ViewFinderFactory.Creator creator) {
		ViewFinderFactory.setViewFinderCreator(creator);
	}
}