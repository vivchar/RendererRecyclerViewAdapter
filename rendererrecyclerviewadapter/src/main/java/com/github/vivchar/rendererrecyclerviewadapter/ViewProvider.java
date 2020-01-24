package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;

/**
 * More detail you can get there: https://github.com/vivchar/ViewFinder
 */
public interface ViewProvider<V> {
    void provide(@NonNull V view);
}