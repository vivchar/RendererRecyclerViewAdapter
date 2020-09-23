package com.github.vivchar.rendererrecyclerviewadapter;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by Vivchar Vitaly on 29.12.17.
 * <p>
 * Library: https://github.com/vivchar/ViewFinder
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
	ViewFinder setOnViewClickListener(@IdRes int ID, View.OnClickListener listener);
	@NonNull
	ViewFinder setOnClickListener(@IdRes int ID, OnClickListener listener);
	@NonNull
	ViewFinder setOnViewTouchListener(@IdRes int ID, View.OnTouchListener listener);
	@NonNull
	ViewFinder setOnTouchListener(@IdRes int ID, OnTouchListener listener);
	@NonNull
	ViewFinder setOnViewLongClickListener(@IdRes int ID, View.OnLongClickListener listener);
	@NonNull
	ViewFinder setOnLongClickListener(@IdRes int ID, OnLongClickListener listener);
	@NonNull
	ViewFinder setOnViewClickListener(@NonNull View.OnClickListener listener);
	@NonNull
	ViewFinder setOnClickListener(@NonNull OnClickListener listener);
	@NonNull
	ViewFinder setOnViewCheckedChangeListener(@IdRes int ID, CompoundButton.OnCheckedChangeListener listener);
	@NonNull
	ViewFinder setOnCheckedChangeListener(@IdRes int ID, OnCheckedChangeListener listener);
	@NonNull
	ViewFinder setClickable(@IdRes int ID, boolean clickable);
	@NonNull
	ViewFinder setLongClickable(@IdRes int ID, boolean clickable);
	@NonNull
	ViewFinder setText(@IdRes int ID, CharSequence text);
	@NonNull
	ViewFinder setText(@IdRes int ID, @StringRes int textID);
	@NonNull
	ViewFinder setText(@IdRes int ID, CharSequence text, TextView.BufferType type);
	@NonNull
	ViewFinder setTextSize(@IdRes int ID, float size);
	@NonNull
	ViewFinder setTypeface(@IdRes int ID, Typeface typeface, int style);
	@NonNull
	ViewFinder setTypeface(@IdRes int ID, Typeface typeface);
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
	ViewFinder setAlpha(@IdRes int ID, float alpha);
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
	ViewFinder setEnabled(@IdRes int ID, boolean enable);
	@NonNull
	ViewFinder setEnabled(@IdRes int ID);
	@NonNull
	ViewFinder setDisabled(@IdRes int ID);
	@NonNull
	ViewFinder setTag(@IdRes int ID, Object tag);
	@NonNull
	ViewFinder setTag(@IdRes int ID, int key, Object tag);
	@NonNull
	ViewFinder setChecked(@IdRes int ID, boolean checked);
	@NonNull
	ViewFinder setSelected(@IdRes int ID, boolean selected);
	@NonNull
	ViewFinder setPressed(@IdRes int ID, boolean pressed);
	@NonNull
	ViewFinder setProgress(@IdRes int ID, int progress);
	@NonNull @RequiresApi(api = Build.VERSION_CODES.N)
	ViewFinder setProgress(@IdRes int ID, int progress, boolean animate);
	@NonNull
	ViewFinder setMaxProgress(@IdRes int ID, int max);
	@NonNull @RequiresApi(api = Build.VERSION_CODES.O)
	ViewFinder setMinProgress(@IdRes int ID, int min);
	@NonNull
	ViewFinder setProgress(@IdRes int ID, int progress, int max);
	@NonNull @RequiresApi(api = Build.VERSION_CODES.O)
	ViewFinder setProgress(@IdRes int ID, int progress, int min, int max);
	@NonNull
	ViewFinder setRating(@IdRes int ID, float rating);
	@NonNull
	ViewFinder setMaxRating(@IdRes int ID, int max);
	@NonNull @RequiresApi(api = Build.VERSION_CODES.O)
	ViewFinder setMinRating(@IdRes int ID, int min);
	@NonNull
	ViewFinder setRating(@IdRes int ID, float rating, int max);
	@NonNull @RequiresApi(api = Build.VERSION_CODES.O)
	ViewFinder setRating(@IdRes int ID, float rating, int min, int max);
	@NonNull
	ViewFinder setAdapter(@IdRes int ID, Adapter adapter);
	@NonNull
	ViewFinder setOnItemClickListener(@IdRes int ID, AdapterView.OnItemClickListener listener);
	@NonNull
	ViewFinder setOnItemLongClickListener(@IdRes int ID, AdapterView.OnItemLongClickListener listener);
	@NonNull
	ViewFinder setOnItemSelectedListener(@IdRes int ID, AdapterView.OnItemSelectedListener listener);
}