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

import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * Created by Vivchar Vitaly on 29.12.17.
 */
@SuppressWarnings("unchecked")
public class ViewFinderImpl<T extends ViewFinder> implements ViewFinder {

    @NonNull
    private final SparseArray<View> mCachedViews = new SparseArray<>();
    @NonNull
    private final View mItemView;

    public ViewFinderImpl(@NonNull final View itemView) {
        mItemView = itemView;
    }

    @NonNull
    @Override
    public <V extends View> T find(final int ID, @NonNull final ViewProvider<V> viewProvider) {
        viewProvider.provide((V) findViewById(ID));
        return (T) this;
    }

    @NonNull
    @Override
    public <V extends View> V find(final int ID) {
        return (V) findViewById(ID);
    }

    @NonNull
    @Override
    public <V extends View> T getRootView(@NonNull final ViewProvider<V> viewProvider) {
        viewProvider.provide((V) mItemView);
        return (T) this;
    }

    @NonNull
    @Override
    public <V extends View> V getRootView() {
        return (V) mItemView;
    }

    @NonNull
    @Override
    public T setOnViewClickListener(final int ID, final View.OnClickListener listener) {
        findViewById(ID).setOnClickListener(listener);
        return (T) this;
    }

    @NonNull
    @Override
    public T setOnClickListener(final int ID, final OnClickListener listener) {
        return setOnViewClickListener(ID, new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                listener.onClick();
            }
        });
    }

    @NonNull
    @Override
    public T setOnViewTouchListener(final int ID, final View.OnTouchListener listener) {
        find(ID).setOnTouchListener(listener);
        return (T) this;
    }

    @NonNull
    @Override
    public T setOnTouchListener(final int ID, final OnTouchListener listener) {
        return setOnViewTouchListener(ID, new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                return listener.onTouch(motionEvent) && view.performClick();
            }
        });
    }

    @NonNull
    @Override
    public T setOnViewLongClickListener(final int ID, final View.OnLongClickListener listener) {
        find(ID).setOnLongClickListener(listener);
        return (T) this;
    }

    @NonNull
    @Override
    public ViewFinder setOnLongClickListener(final int ID, final OnLongClickListener listener) {
        return setOnViewLongClickListener(ID, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                return listener.onLongClick();
            }
        });
    }

    @NonNull
    @Override
    public T setOnViewClickListener(@NonNull final View.OnClickListener listener) {
        getRootView().setOnClickListener(listener);
        return (T) this;
    }

    @NonNull
    @Override
    public ViewFinder setOnClickListener(@NonNull final OnClickListener listener) {
        return setOnViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                listener.onClick();
            }
        });
    }

    @NonNull
    @Override
    public T setOnViewCheckedChangeListener(final int ID, final CompoundButton.OnCheckedChangeListener listener) {
        ((CompoundButton) find(ID)).setOnCheckedChangeListener(listener);
        return (T) this;
    }

    @NonNull
    @Override
    public ViewFinder setOnCheckedChangeListener(final int ID, final OnCheckedChangeListener listener) {
        return setOnViewCheckedChangeListener(ID, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                listener.onCheckedChanged(b);
            }
        });
    }

    @NonNull
    @Override
    public T setClickable(final int ID, final boolean clickable) {
        find(ID).setClickable(clickable);
        return (T) this;
    }

    @NonNull
    @Override
    public T setLongClickable(final int ID, final boolean clickable) {
        find(ID).setLongClickable(clickable);
        return (T) this;
    }

    @NonNull
    @Override
    public T setText(final int ID, final CharSequence text) {
        ((TextView) find(ID)).setText(text);
        return (T) this;
    }

    @NonNull
    @Override
    public T setText(final int ID, @StringRes final int textID) {
        ((TextView) find(ID)).setText(textID);
        return (T) this;
    }

    @NonNull
    @Override
    public T setText(final int ID, final CharSequence text, final TextView.BufferType type) {
        ((TextView) find(ID)).setText(text, type);
        return (T) this;
    }

    @NonNull
    @Override
    public T setTextSize(final int ID, final float size) {
        ((TextView) find(ID)).setTextSize(size);
        return (T) this;
    }

    @NonNull
    @Override
    public T setTypeface(final int ID, final Typeface typeface, final int style) {
        ((TextView) find(ID)).setTypeface(typeface, style);
        return (T) this;
    }

    @NonNull
    @Override
    public T setTypeface(final int ID, final Typeface typeface) {
        ((TextView) find(ID)).setTypeface(typeface);
        return (T) this;
    }

    @NonNull
    @Override
    public T setError(final int ID, final CharSequence error) {
        ((TextView) find(ID)).setError(error);
        return (T) this;
    }

    @NonNull
    @Override
    public T setError(final int ID, final CharSequence error, final Drawable icon) {
        ((TextView) find(ID)).setError(error, icon);
        return (T) this;
    }

    @NonNull
    @Override
    public T setTextColor(final int ID, final int color) {
        ((TextView) find(ID)).setTextColor(color);
        return (T) this;
    }

    @NonNull
    @Override
    public T setTextColor(final int ID, final ColorStateList colors) {
        ((TextView) find(ID)).setTextColor(colors);
        return (T) this;
    }

    @NonNull
    @Override
    public T setBackgroundColor(final int ID, @ColorInt final int color) {
        find(ID).setBackgroundColor(color);
        return (T) this;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public T setForeground(final int ID, final Drawable background) {
        find(ID).setForeground(background);
        return (T) this;
    }

    @NonNull
    @Override
    public T setBackground(final int ID, final Drawable background) {
        find(ID).setBackgroundDrawable(background);
        return (T) this;
    }

    @NonNull
    @Override
    public T setBackgroundDrawable(final int ID, final Drawable background) {
        return setBackground(ID, background);
    }

    @NonNull
    @Override
    public T setBackgroundResource(final int ID, @DrawableRes final int background) {
        find(ID).setBackgroundResource(background);
        return (T) this;
    }

    @NonNull
    @Override
    public T setImageDrawable(final int ID, @Nullable final Drawable drawable) {
        ((ImageView) find(ID)).setImageDrawable(drawable);
        return (T) this;
    }

    @NonNull
    @Override
    public T setImageResource(final int ID, final int resId) {
        ((ImageView) find(ID)).setImageResource(resId);
        return (T) this;
    }

    @NonNull
    @Override
    public T setImageURI(final int ID, @Nullable final Uri uri) {
        ((ImageView) find(ID)).setImageURI(uri);
        return (T) this;
    }

    @NonNull
    @Override
    public T setImageBitmap(final int ID, final Bitmap bm) {
        ((ImageView) find(ID)).setImageBitmap(bm);
        return (T) this;
    }

    @NonNull
    @Override
    public T setColorFilter(final int ID, final ColorFilter cf) {
        ((ImageView) find(ID)).setColorFilter(cf);
        return (T) this;
    }

    @NonNull
    @Override
    public T setColorFilter(final int ID, final int color, final PorterDuff.Mode mode) {
        ((ImageView) find(ID)).setColorFilter(color, mode);
        return (T) this;
    }

    @NonNull
    @Override
    public T clearColorFilter(final int ID) {
        ((ImageView) find(ID)).clearColorFilter();
        return (T) this;
    }

    @NonNull
    @Override
    public T setVisible(final int ID, final boolean visible) {
        find(ID).setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return (T) this;
    }

    @NonNull
    @Override
    public T setInvisible(final int ID, final boolean invisible) {
        find(ID).setVisibility(invisible ? View.INVISIBLE : View.VISIBLE);
        return (T) this;
    }

    @NonNull
    @Override
    public T setGone(final int ID, final boolean gone) {
        find(ID).setVisibility(gone ? View.GONE : View.VISIBLE);
        return (T) this;
    }

    @NonNull
    @Override
    public T setVisibility(final int ID, final int visibility) {
        find(ID).setVisibility(visibility);
        return (T) this;
    }

    @NonNull
    @Override
    public T setAlpha(final int ID, final float alpha) {
        find(ID).setAlpha(alpha);
        return (T) this;
    }

    @NonNull
    @Override
    public T addView(final int ID, final View child) {
        ((ViewGroup) find(ID)).addView(child);
        return (T) this;
    }

    @NonNull
    @Override
    public T addView(final int ID, final View child, final int index) {
        ((ViewGroup) find(ID)).addView(child, index);
        return (T) this;
    }

    @NonNull
    @Override
    public T removeView(final int ID, final View view) {
        ((ViewGroup) find(ID)).removeView(view);
        return (T) this;
    }

    @NonNull
    @Override
    public T removeViewAt(final int ID, final int index) {
        ((ViewGroup) find(ID)).removeViewAt(index);
        return (T) this;
    }

    @NonNull
    @Override
    public T removeAllViews(final int ID) {
        ((ViewGroup) find(ID)).removeAllViews();
        return (T) this;
    }

    @NonNull
    @Override
    public T setEnabled(final int ID, final boolean enable) {
        find(ID).setEnabled(enable);
        return (T) this;
    }

    @NonNull
    @Override
    public T setEnabled(final int ID) {
        find(ID).setEnabled(true);
        return (T) this;
    }

    @NonNull
    @Override
    public T setDisabled(final int ID) {
        find(ID).setEnabled(false);
        return (T) this;
    }

    @NonNull
    @Override
    public T setTag(final int ID, final Object tag) {
        find(ID).setTag(tag);
        return (T) this;
    }

    @NonNull
    @Override
    public T setTag(final int ID, final int key, final Object tag) {
        find(ID).setTag(key, tag);
        return (T) this;
    }

    @NonNull
    @Override
    public T setChecked(final int ID, final boolean checked) {
        ((Checkable) find(ID)).setChecked(checked);
        return (T) this;
    }

    @NonNull
    @Override
    public T setSelected(final int ID, final boolean selected) {
        find(ID).setSelected(selected);
        return (T) this;
    }

    @NonNull
    @Override
    public T setPressed(final int ID, final boolean pressed) {
        find(ID).setPressed(pressed);
        return (T) this;
    }

    @NonNull
    @Override
    public T setProgress(final int ID, final int progress) {
        ((ProgressBar) find(ID)).setProgress(progress);
        return (T) this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public T setProgress(final int ID, final int progress, final boolean animate) {
        ((ProgressBar) find(ID)).setProgress(progress, animate);
        return (T) this;
    }

    @NonNull
    @Override
    public T setMaxProgress(final int ID, final int max) {
        ((ProgressBar) find(ID)).setMax(max);
        return (T) this;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public T setMinProgress(final int ID, final int min) {
        ((ProgressBar) find(ID)).setMin(min);
        return (T) this;
    }

    @NonNull
    @Override
    public T setProgress(final int ID, final int progress, final int max) {
        final ProgressBar progressBar = find(ID);
        progressBar.setMax(max);
        progressBar.setProgress(progress);
        return (T) this;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public T setProgress(final int ID, final int progress, final int min, final int max) {
        final ProgressBar progressBar = find(ID);
        progressBar.setProgress(progress);
        progressBar.setMin(min);
        progressBar.setMax(max);
        return (T) this;
    }

    @NonNull
    @Override
    public T setRating(final int ID, final float rating) {
        ((RatingBar) find(ID)).setRating(rating);
        return (T) this;
    }

    @NonNull
    @Override
    public T setMaxRating(final int ID, final int max) {
        ((RatingBar) find(ID)).setMax(max);
        return (T) this;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public T setMinRating(final int ID, final int min) {
        ((RatingBar) find(ID)).setMin(min);
        return (T) this;
    }

    @NonNull
    @Override
    public T setRating(final int ID, final float rating, final int max) {
        final RatingBar ratingBar = find(ID);
        ratingBar.setRating(rating);
        ratingBar.setMax(max);
        return (T) this;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public T setRating(final int ID, final float rating, final int min, final int max) {
        final RatingBar ratingBar = find(ID);
        ratingBar.setRating(rating);
        ratingBar.setMin(min);
        ratingBar.setMax(max);
        return (T) this;
    }

    @NonNull
    @Override
    public T setAdapter(final int ID, final Adapter adapter) {
        ((AdapterView) find(ID)).setAdapter(adapter);
        return (T) this;
    }

    @NonNull
    @Override
    public T setOnItemClickListener(final int ID, final AdapterView.OnItemClickListener listener) {
        ((AdapterView) find(ID)).setOnItemClickListener(listener);
        return (T) this;
    }

    @NonNull
    @Override
    public T setOnItemLongClickListener(final int ID, final AdapterView.OnItemLongClickListener listener) {
        ((AdapterView) find(ID)).setOnItemLongClickListener(listener);
        return (T) this;
    }

    @NonNull
    @Override
    public T setOnItemSelectedListener(final int ID, final AdapterView.OnItemSelectedListener listener) {
        ((AdapterView) find(ID)).setOnItemSelectedListener(listener);
        return (T) this;
    }

    @NonNull
    private View findViewById(@IdRes final int ID) {
        final View cachedView = mCachedViews.get(ID);
        if (cachedView != null) {
            return cachedView;
        }
        final View view = mItemView.findViewById(ID);
        mCachedViews.put(ID, view);
        return view;
    }
}