package com.github.vivchar.rendererrecyclerviewadapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by Vivchar Vitaly on 1/9/17.
 */
@SuppressWarnings("unchecked")
public class RendererRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

	protected static final String ITEM_VIEW_STATES_KEY = "renderer_adapter_item_view_states_key";
	protected static final String RECYCLER_VIEW_STATE_KEY = "renderer_adapter_recycler_view_state_key";

	@NonNull
	protected final ArrayList<ViewModel> mItems = new ArrayList<>();
	@NonNull
	protected final ArrayList<ViewRenderer> mRenderers = new ArrayList<>();
	@NonNull
	protected final ArrayList<Type> mTypes = new ArrayList<>();
	@NonNull
	protected final HashMap<Integer, ViewState> mViewStates = new HashMap<>();
	@NonNull
	protected final ArrayList<ViewHolder> mBoundViewHolders = new ArrayList<>();

	@Nullable
	protected RecyclerView mRecyclerView = null;
	@Nullable
	protected ListUpdateCallback mUpdateCallback = null;
	@NonNull
	protected DiffCallback mDiffCallback = new DefaultDiffCallback();
	@NonNull
	protected LoadMoreViewModel mLoadMoreModel = new LoadMoreViewModel();

	protected boolean mDiffUtilEnabled = false;
	protected boolean mLoadMoreVisible = false;
	protected int mLoadMorePosition;
	@Nullable
	private Bundle mSavedInstanceState;

	public RendererRecyclerViewAdapter() {}

	@Deprecated
	public RendererRecyclerViewAdapter(@NonNull final Context context) {}

	@Override
	public ViewHolder onCreateViewHolder(final ViewGroup parent, final int typeIndex) {
		final ViewRenderer renderer = mRenderers.get(typeIndex);
		return renderer.performCreateViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position, @Nullable final List payloads) {
		super.onBindViewHolder(holder, position, payloads);
		final ViewModel item = getItem(position);
		final ViewRenderer renderer = getRenderer(item);

		if (payloads == null || payloads.isEmpty()) {
			/* Full bind */
			renderer.performBindView(item, holder);
			restoreViewState(holder);
		} else {
			/* Partial bind */
			renderer.performRebindView(item, holder, payloads);
		}

		mBoundViewHolders.remove(holder);
		mBoundViewHolders.add(holder);
	}

	public void registerRenderer(@NonNull final ViewRenderer renderer) {
		final Type type = renderer.getType();

		if (!mTypes.contains(type)) {
			mTypes.add(type);
			mRenderers.add(renderer);
		} else {
			throw new RuntimeException("ViewRenderer already registered for this type: " + type);
		}
	}

	@NonNull
	protected ViewRenderer getRenderer(final int position) {
		final int typeIndex = getTypeIndex(position);
		return mRenderers.get(typeIndex);
	}

	@NonNull
	protected ViewRenderer getRenderer(@NonNull final ViewModel model) {
		final int typeIndex = getTypeIndex(model);
		return mRenderers.get(typeIndex);
	}

	@NonNull
	protected ViewRenderer getRenderer(@NonNull final Type type) {
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
	public void onViewRecycled(final ViewHolder holder) {
		super.onViewRecycled(holder);

		final ViewRenderer renderer = getRenderer(holder.getType());
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
		return (T) mItems.get(position);
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	public void enableDiffUtil() {
		mDiffUtilEnabled = true;
	}

	public void disableDiffUtil() {
		mDiffUtilEnabled = false;
	}

	public void setDiffCallback(@NonNull final DiffCallback diffCallback) {
		mDiffCallback = diffCallback;
		enableDiffUtil();
	}

	public void setUpdateCallback(@NonNull final ListUpdateCallback updateCallback) {
		mUpdateCallback = updateCallback;
	}

	public void setItems(@NonNull final List<? extends ViewModel> items) {
		if (mDiffUtilEnabled) {
			mDiffCallback.setItems(mItems, items);

			final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(mDiffCallback);

			mItems.clear();
			mItems.addAll(items);

			diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
				@Override
				public void onInserted(final int position, final int count) {
					if (mUpdateCallback != null) {
						mUpdateCallback.onInserted(position, count);
					}
					notifyItemRangeInserted(position, count);
				}

				@Override
				public void onRemoved(final int position, final int count) {
					if (mUpdateCallback != null) {
						mUpdateCallback.onRemoved(position, count);
					}
					notifyItemRangeRemoved(position, count);
				}

				@Override
				public void onMoved(final int fromPosition, final int toPosition) {
					if (mUpdateCallback != null) {
						mUpdateCallback.onMoved(fromPosition, toPosition);
					}
					notifyItemMoved(fromPosition, toPosition);
				}

				@Override
				public void onChanged(final int position, final int count, final Object payload) {
					if (mUpdateCallback != null) {
						mUpdateCallback.onChanged(position, count, payload);
					}
					notifyItemRangeChanged(position, count, payload);
				}
			});
		} else {
			mItems.clear();
			mItems.addAll(items);
		}

		mLoadMoreVisible = false;
	}

	/**
	 * Show a Load More Indicator.
	 * The Load More Indicator will be hidden automatically when you call the {@link #setItems(List)} method.
	 * Or you can manually hide it using the {@link #hideLoadMore()} method
	 * <p>
	 * FYI: If you want to add a Load More Indicator to other position, then you should override this method
	 */
	public void showLoadMore() {
		final Handler handler = new Handler();
		final Runnable r = new Runnable() {
			public void run() {
				mLoadMoreVisible = true;
				mItems.add(mLoadMoreModel);
				mLoadMorePosition = getItemCount() - 1;
				notifyItemInserted(mLoadMorePosition);
			}
		};
		handler.post(r);
	}

	public void hideLoadMore() {
		if (mLoadMoreVisible && mLoadMorePosition < getItemCount()) {
			final Handler handler = new Handler();
			final Runnable r = new Runnable() {
				public void run() {
					mItems.remove(mLoadMorePosition);
					notifyItemRemoved(mLoadMorePosition);
					mLoadMoreVisible = false;
				}
			};
			handler.post(r);
		}
	}

	/**
	 * If you want to show a some custom data in a Load More Indicator
	 * then you should set your custom {@link LoadMoreViewModel} and createViewState your custom {@link LoadMoreViewRenderer}
	 * <p>
	 * Use the {@link #registerRenderer(ViewRenderer)} to set your custom {@link LoadMoreViewRenderer}
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

	/**
	 * Use {@link #getStates()}
	 */
	@Deprecated
	@NonNull
	public SparseArray<ViewState> getViewStates() {
		final SparseArray<ViewState> list = new SparseArray<>();

		final Iterator<Map.Entry<Integer, ViewState>> iterator = getStates().entrySet().iterator();
		while (iterator.hasNext()) {
			final Map.Entry<Integer, ViewState> next = iterator.next();
			list.put(next.getKey(), next.getValue());
		}

		return list;
	}

	/**
	 * Use {@link #setStates(HashMap)}
	 */
	@Deprecated
	public void setViewStates(@NonNull final SparseArray<ViewState> states) {
		mViewStates.clear();
		for (int i = 0; i < states.size(); i++) {
			final int key = states.keyAt(i);
			final ViewState value = states.get(key);
			mViewStates.put(key, value);
		}
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
		final ViewRenderer viewRenderer = getRenderer(holder.getType());
		final ViewState viewState = viewRenderer.createViewState(holder);
		if (viewState != null) {
			if (holder.isSupportViewState()) {
				mViewStates.put(holder.getViewStateID(), viewState);
			} else {
				throw new RuntimeException("You defined the " + viewState.getClass().getSimpleName() + " but didn't specify the ID."
				                           + " Please override onCreateViewStateID(model) method in your ViewRenderer.");
			}
		}
	}

	protected void restoreViewState(@NonNull final ViewHolder holder) {
		if (holder.isSupportViewState()) {
			final ViewState viewState = mViewStates.get(holder.getViewStateID());
			if (viewState != null) {
				viewState.restore(holder);
			} else if (hasChildren(holder)) {
				getChildAdapter((CompositeViewHolder) holder).clearViewStates();
			}
		}
	}

	protected void saveRecyclerViewState(@NonNull final Bundle outState) {
		if (mRecyclerView != null) {
			final Parcelable recyclerViewState = mRecyclerView.getLayoutManager().onSaveInstanceState();
			outState.putParcelable(RECYCLER_VIEW_STATE_KEY, recyclerViewState);
		}
	}

	protected void restoreRecyclerViewState(@Nullable final Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			final Parcelable parcelable = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE_KEY);
			if (parcelable != null && mRecyclerView != null) {
				mRecyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
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

	@Override
	public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		mRecyclerView = recyclerView;
		restoreRecyclerViewState(mSavedInstanceState);
	}

	@Override
	public void onDetachedFromRecyclerView(final RecyclerView recyclerView) {
		super.onDetachedFromRecyclerView(recyclerView);
		mRecyclerView = null;
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
}