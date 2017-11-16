package com.github.vivchar.rendererrecyclerviewadapter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by Vivchar Vitaly on 1/9/17.
 */
@SuppressWarnings("unchecked")
public class RendererRecyclerViewAdapter extends RecyclerView.Adapter {

	@NonNull
	protected final ArrayList<ViewModel> mItems = new ArrayList<>();
	@NonNull
	protected final ArrayList<ViewRenderer> mRenderers = new ArrayList<>();
	@NonNull
	protected final ArrayList<Type> mTypes = new ArrayList<>();
	@NonNull
	protected SparseArray<ViewState> mViewStates = new SparseArray<>();
	@NonNull
	protected final ArrayList<RecyclerView.ViewHolder> mBoundViewHolders = new ArrayList<>();

	@NonNull
	protected DiffCallback mDiffCallback = new DefaultDiffCallback();
	@NonNull
	protected LoadMoreViewModel mLoadMoreModel = new LoadMoreViewModel();

	protected boolean mDiffUtilEnabled = false;
	protected boolean mLoadMoreVisible = false;
	protected int mLoadMorePosition;

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int typeIndex) {
		final ViewRenderer renderer = mRenderers.get(typeIndex);
		return renderer.createViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position, @Nullable final List payloads) {
		super.onBindViewHolder(holder, position, payloads);
		final ViewModel item = getItem(position);
		final ViewRenderer renderer = getRenderer(item);

		if (payloads == null || payloads.isEmpty()) {
			/* Full bind */
			renderer.bindView(item, holder);
			restoreViewState(position, holder);
		} else {
			/* Partial bind */
			renderer.rebindView(item, holder, payloads);
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
		final Type type = model.getClass();
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
	public void onViewRecycled(final RecyclerView.ViewHolder holder) {
		super.onViewRecycled(holder);
		final int position = holder.getAdapterPosition();
		if (position != NO_POSITION) {
			if (hasChildren(holder)) {
				onChildrenViewsRecycled(getChildAdapter((CompositeViewHolder) holder));
			}
			saveViewState(position, holder);
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

	public void setItems(@NonNull final List<? extends ViewModel> items) {
		if (mDiffUtilEnabled) {
			mDiffCallback.setItems(mItems, items);

			final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(mDiffCallback);

			mItems.clear();
			mItems.addAll(items);

			diffResult.dispatchUpdatesTo(this);
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
	 * then you should set your custom {@link LoadMoreViewModel} and create your custom {@link LoadMoreViewRenderer}
	 * <p>
	 * Use the {@link #registerRenderer(ViewRenderer)} to set your custom {@link LoadMoreViewRenderer}
	 *
	 * @param model - custom {@link LoadMoreViewModel}
	 */
	public void setLoadMoreModel(@NonNull final LoadMoreViewModel model) {
		mLoadMoreModel = model;
	}

	@NonNull
	public ArrayList<RecyclerView.ViewHolder> getBoundViewHolders() {
		return new ArrayList<>(mBoundViewHolders);
	}

	@NonNull
	public SparseArray<ViewState> getViewStates() {
		return mViewStates;
	}

	public void setViewStates(@NonNull final SparseArray<ViewState> states) {
		mViewStates = states;
	}

	public void clearViewStates() {
		mViewStates.clear();
	}

	protected void saveViewState(final int position, @NonNull final RecyclerView.ViewHolder holder) {
		final ViewModel model = getItem(position);
		final ViewRenderer viewRenderer = getRenderer(model);
		mViewStates.put(position, viewRenderer.createViewState(model, holder));
	}

	protected void restoreViewState(final int position, @NonNull final RecyclerView.ViewHolder holder) {
		final ViewModel model = getItem(position);
		final ViewState viewState = mViewStates.get(position);
		if (viewState != null) {
			viewState.restore(model, holder);
		} else if (hasChildren(holder)) {
			getChildAdapter((CompositeViewHolder) holder).clearViewStates();
		}
	}

	protected void onChildrenViewsRecycled(@NonNull final RendererRecyclerViewAdapter adapter) {
		final ArrayList<RecyclerView.ViewHolder> boundViewHolders = adapter.getBoundViewHolders();
		for (final RecyclerView.ViewHolder viewHolder : boundViewHolders) {
			adapter.onViewRecycled(viewHolder);
		}
	}

	protected boolean hasChildren(@NonNull final RecyclerView.ViewHolder holder) {
		return holder instanceof CompositeViewHolder;
	}

	@NonNull
	protected RendererRecyclerViewAdapter getChildAdapter(@NonNull final CompositeViewHolder holder) {
		return holder.adapter;
	}
}