package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

/**
 * Created by Vivchar Vitaly on 29.12.17.
 *  * <p>
 * More detail you can get there: https://github.com/vivchar/ViewFinder
 */

public interface ViewFinder {

	@NonNull
	<V extends View> ViewFinder find(@IdRes int ID, @NonNull ViewProvider<V> viewProvider);
	@NonNull
	<V extends View> V find(@IdRes int ID);
	@NonNull
	<V extends View> ViewFinder getRootView(@NonNull ViewProvider<V> viewProvider);
	@NonNull
	<V extends View> V getRootView();
	@NonNull
	ViewFinder setOnClickListener(@IdRes int ID, @NonNull View.OnClickListener onClickListener);
	@NonNull
	ViewFinder setText(@IdRes int ID, CharSequence text);
	@NonNull
	ViewFinder setText(@IdRes int ID, @StringRes int textID);
}