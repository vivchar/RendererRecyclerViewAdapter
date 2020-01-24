package com.github.vivchar.rendererrecyclerviewadapter;

import android.view.View;

import androidx.annotation.NonNull;


/**
 * Created by Vivchar Vitaly on 18.01.18.
 */

public class ViewFinderFactory {

	private static Creator sViewFinderCreator = null;

	public static ViewFinder create(@NonNull final View view) {
		return sViewFinderCreator != null
				? sViewFinderCreator.create(view)
				: new ViewFinderImpl(view);
	}

	public static void setViewFinderCreator(@NonNull final Creator viewFinderCreator) {
		sViewFinderCreator = viewFinderCreator;
	}

	public interface Creator {
		@NonNull
		ViewFinderImpl create(@NonNull View view);
	}
}
