package com.github.vivchar.example.pages.github.items.category;

import androidx.annotation.NonNull;

import com.github.vivchar.example.R;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewFinder;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public class CategoryViewRenderer extends ViewRenderer<CategoryModel, ViewFinder> {

    public CategoryViewRenderer(@NonNull final Listener listener) {
        super(R.layout.item_category, CategoryModel.class, (model, finder, payloads) -> finder
                .setText(R.id.title, model.getName())
                .setOnClickListener(R.id.viewAll, view -> listener.onCategoryClicked(model))
        );
    }

    public interface Listener {
        void onCategoryClicked(@NonNull CategoryModel model);
    }
}
