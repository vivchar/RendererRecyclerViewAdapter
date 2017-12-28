package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.support.annotation.NonNull;

/**
 * More detail you can get there: https://github.com/vivchar/ViewFinder
 */
public interface ViewProvider <T> {

	void provide(@NonNull T view);
}