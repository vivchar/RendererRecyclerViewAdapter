package com.github.vivchar.example.widgets;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vivchar Vitaly on 05.11.17.
 * <p>
 * https://stackoverflow.com/a/29893272/4894238
 */
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
	/* The total number of items in the dataset after the last load */
	private int previousTotalItemCount = 0;
	private boolean loading = true;
	private final int visibleThreshold = 5;
	private int firstVisibleItem, visibleItemCount, totalItemCount;
	private final int startingPageIndex = 0;
	private int currentPage = 0;

	@Override
	public void onScrolled(final RecyclerView mRecyclerView, final int dx, final int dy) {
		super.onScrolled(mRecyclerView, dx, dy);
		final LinearLayoutManager mLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();

		visibleItemCount = mRecyclerView.getChildCount();
		totalItemCount = mLayoutManager.getItemCount();
		firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
		onScroll(firstVisibleItem, visibleItemCount, totalItemCount);
	}

	private void onScroll(final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
		/*
		If the total item count is zero and the previous isn't, assume the
		list is invalidated and should be reset back to initial state
		*/
		if (totalItemCount < previousTotalItemCount) {
			this.currentPage = this.startingPageIndex;
			this.previousTotalItemCount = totalItemCount;
			if (totalItemCount == 0) {
				this.loading = true;
			}
		}
		/*
		If it’s still loading, we check to see if the dataset count has
		changed, if so we conclude it has finished loading and update the current page
		number and total item count.
		*/
		if (loading && (totalItemCount > previousTotalItemCount)) {
			loading = false;
			previousTotalItemCount = totalItemCount;
			currentPage++;
		}

		/*
		If it isn’t currently loading, we check to see if we have breached
		the visibleThreshold and need to reload more data.
		If we do need to reload some more data, we execute onLoadMore to fetch the data.
		*/
		if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
			this.previousTotalItemCount++; /* we will add the LoadMoreItem */
			onLoadMore(currentPage + 1, totalItemCount);
			loading = true;
		}
	}

	/* Defines the process for actually loading more data based on page */
	public abstract void onLoadMore(int page, int totalItemsCount);
}
