package com.github.vivchar.rendererrecyclerviewadapter.binder;

import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider;

/**
 * Created by Vivchar Vitaly on 30.12.17.
 */

public interface CompositeViewStateProvider <M extends CompositeViewModel, VH extends CompositeViewHolder>
		extends ViewStateProvider<M, VH> {}