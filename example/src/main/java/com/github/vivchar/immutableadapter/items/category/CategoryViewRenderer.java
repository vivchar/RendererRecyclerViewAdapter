package com.github.vivchar.immutableadapter.items.category;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.github.vivchar.immutableadapter.R;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public
class CategoryViewRenderer
		extends ViewRenderer<CategoryModel, CategoryViewHolder>
{
	@NonNull
	private final Listener mListener;

	public
	CategoryViewRenderer(final int type, final Context context, @NonNull final Listener listener) {
		super(type, context);
		mListener = listener;
	}

	@Override
	public
	void bindView(@NonNull final CategoryModel model, @NonNull final CategoryViewHolder holder) {
		holder.mName.setText(model.getName());
		holder.mViewAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public
			void onClick(final View v) {
				mListener.onCategoryClicked(model);
			}
		});
	}

	@NonNull
	@Override
	public
	CategoryViewHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new CategoryViewHolder(inflate(R.layout.item_category, parent));
	}

	public
	interface Listener {
		void onCategoryClicked(@NonNull CategoryModel model);
	}
}
