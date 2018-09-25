package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Vivchar Vitaly on 8/25/17.
 */

public interface CompositeViewModel extends ViewModel {
	@NonNull
	List<? extends ViewModel> getItems();
}