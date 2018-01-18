package com.github.vivchar.rendererrecyclerviewadapter.binder;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Vivchar Vitaly on 29.12.17.
 *  * <p>
 * More detail you can get there: https://github.com/vivchar/ViewFinder
 */

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface ViewFinder {

	@NonNull
	<V extends View> ViewFinder find(@IdRes int ID, @NonNull ViewProvider<V> viewProvider);
	@NonNull
	<V extends View> V find(@IdRes int ID);
	@NonNull
	<V extends View> ViewFinder getRootView(@NonNull ViewProvider<V> viewProvider);
	@NonNull
	<V extends View> V getRootView();
	@NonNull
	ViewFinder setOnClickListener(@IdRes int ID, @NonNull View.OnClickListener onClickListener);
	@NonNull
	ViewFinder setOnLongClickListener(@IdRes int ID, @NonNull View.OnLongClickListener onClickListener);
	@NonNull
	ViewFinder setOnClickListener(@NonNull View.OnClickListener onClickListener);
	@NonNull
	ViewFinder setText(@IdRes int ID, CharSequence text);
	@NonNull
	ViewFinder setText(@IdRes int ID, @StringRes int textID);
	@NonNull
	ViewFinder setText(@IdRes int ID, CharSequence text, TextView.BufferType type);
	@NonNull
	ViewFinder setTextSize(@IdRes int ID, float size);
	@NonNull
	ViewFinder setTypeface(int ID, Typeface typeface, int style);
	@NonNull
	ViewFinder setTypeface(int ID, Typeface typeface);
	@NonNull
	ViewFinder setError(@IdRes int ID, CharSequence error);
	@NonNull
	ViewFinder setError(@IdRes int ID, CharSequence error, Drawable icon);
	@NonNull
	ViewFinder setTextColor(@IdRes int ID, @ColorInt int color);
	@NonNull
	ViewFinder setTextColor(@IdRes int ID, ColorStateList colors);
	@NonNull
	ViewFinder setBackgroundColor(@IdRes int ID, @ColorInt int color);
	@NonNull
	ViewFinder setForeground(@IdRes int ID, Drawable background);
	@NonNull
	ViewFinder setBackground(@IdRes int ID, Drawable background);
	@NonNull
	ViewFinder setBackgroundDrawable(@IdRes int ID, Drawable background);
	@NonNull
	ViewFinder setBackgroundResource(@IdRes int ID, @DrawableRes int background);
	@NonNull
	ViewFinder setImageDrawable(@IdRes int ID, @Nullable Drawable drawable);
	@NonNull
	ViewFinder setImageResource(@IdRes int ID, @DrawableRes int resId);
	@NonNull
	ViewFinder setImageURI(@IdRes int ID, @Nullable Uri uri);
	@NonNull
	ViewFinder setImageBitmap(@IdRes int ID, Bitmap bm);
	@NonNull
	ViewFinder setColorFilter(@IdRes int ID, ColorFilter cf);
	@NonNull
	ViewFinder setColorFilter(@IdRes int ID, int color, PorterDuff.Mode mode);
	@NonNull
	ViewFinder clearColorFilter(@IdRes int ID);
	@NonNull
	ViewFinder setVisible(@IdRes int ID, boolean visible);
	@NonNull
	ViewFinder setInvisible(@IdRes int ID, boolean invisible);
	@NonNull
	ViewFinder setGone(@IdRes int ID, boolean gone);
	@NonNull
	ViewFinder setVisibility(@IdRes int ID, int visibility);
	@NonNull
	ViewFinder setAlpha(@IdRes int ID, int alpha);
	@NonNull
	ViewFinder addView(@IdRes int ID, View child);
	@NonNull
	ViewFinder addView(@IdRes int ID, View child, int index);
	@NonNull
	ViewFinder removeView(@IdRes int ID, View view);
	@NonNull
	ViewFinder removeViewAt(@IdRes int ID, int index);
	@NonNull
	ViewFinder removeAllViews(@IdRes int ID);
	@NonNull
	ViewFinder setEnabled(int ID, boolean enable);
	@NonNull
	ViewFinder setEnabled(int ID);
	@NonNull
	ViewFinder setDisabled(int ID);
	@NonNull
	ViewFinder setTag(int ID, Object tag);
	@NonNull
	ViewFinder setChecked(int ID, boolean checked);
	@NonNull
	ViewFinder setSelected(int ID, boolean selected);
	@NonNull
	ViewFinder setPressed(int ID, boolean pressed);
}