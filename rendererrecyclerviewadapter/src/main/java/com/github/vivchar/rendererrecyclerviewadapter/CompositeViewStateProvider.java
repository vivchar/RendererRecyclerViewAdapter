package com.github.vivchar.rendererrecyclerviewadapter;

/**
 * Created by Vivchar Vitaly on 30.12.17.
 */

public interface CompositeViewStateProvider <M extends CompositeViewModel, VH extends CompositeViewHolder>
		extends ViewStateProvider<M, VH> {}