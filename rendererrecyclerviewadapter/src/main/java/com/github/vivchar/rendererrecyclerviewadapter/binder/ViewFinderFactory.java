package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Vivchar Vitaly on 18.01.18.
 */

public class ViewFinderFactory {

	public static ViewFinder create(@NonNull final View itemView) {
		//TODO
		return new ViewFinderImpl(itemView);
	}
}
