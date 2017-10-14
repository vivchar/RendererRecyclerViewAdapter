package com.github.vivchar.example.pages.github.items.list;

import android.support.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeItemModel;
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/24/17.
 */

public class RecyclerViewModel
		extends DefaultCompositeItemModel
{
	public static final int TYPE = 654323;

	public RecyclerViewModel(@NonNull final List<ItemModel> items) {
		super(TYPE, items);
	}
}
