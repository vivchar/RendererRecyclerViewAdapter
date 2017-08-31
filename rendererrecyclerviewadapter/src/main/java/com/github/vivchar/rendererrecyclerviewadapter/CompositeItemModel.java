package com.github.vivchar.rendererrecyclerviewadapter;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/25/17.
 */

public interface CompositeItemModel
		extends ItemModel
{
	@NonNull
	List<? extends ItemModel> getItems();
}
