package com.github.vivchar.rendererrecyclerviewadapter.binder;

import androidx.annotation.NonNull;

import android.view.View;


/**
 * Created by Vivchar Vitaly on 18.01.18.
 */

public class ViewFinderFactory {

	private static Creator sViewFinderCreator = null;

	public static ViewFinder create(@NonNull final View view) {
		if (sViewFinderCreator != null) {
			sViewFinderCreator.create(view);
		}
		return new ViewFinderImpl(view);
	}

	public static void setViewFinderCreator(@NonNull final Creator viewFinderCreator) {
		sViewFinderCreator = viewFinderCreator;
	}

	public interface Creator {
		@NonNull
		ViewFinderImpl create(@NonNull View view);
	}
}
