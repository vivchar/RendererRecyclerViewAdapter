package com.github.vivchar.example;

/**
 * Created by Vivchar Vitaly on 12/29/17.
 */

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

import static com.github.vivchar.example.MenuItemID.*;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
		MAIN,
		DONE,
		DIFF_UTIL,
		PAYLOAD,
		VIEW_STATE,
		LOAD_MORE,
		VIEW_BINDER,
		COMPOSITE_VIEW_RENDERER,
		VIEW_RENDERER,
})
public @interface MenuItemID {
	int MAIN = R.id.main;
	int DONE = R.id.done;
	int DIFF_UTIL = R.id.diff_util;
	int PAYLOAD = R.id.payload;
	int VIEW_STATE = R.id.view_state;
	int LOAD_MORE = R.id.load_more;
	int VIEW_BINDER = R.id.view_binder;
	int COMPOSITE_VIEW_RENDERER = R.id.composite_view_renderer;
	int VIEW_RENDERER = R.id.view_renderer;

	List<Integer> ALL = Arrays.asList(
			MAIN,
			DONE,
			DIFF_UTIL,
			PAYLOAD,
			VIEW_STATE,
			LOAD_MORE,
			VIEW_BINDER,
			COMPOSITE_VIEW_RENDERER,
			VIEW_RENDERER
	);
}