package com.github.vivchar.example.pages.github.items;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.CustomViewFinder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ViewState;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public class StargazerViewRenderer extends ViewRenderer<StargazerModel, CustomViewFinder> {

    public StargazerViewRenderer(final int layoutID, @NonNull final Listener listener) {
        super(layoutID, StargazerModel.class, (model, finder, payloads) -> finder
                .setUrl(R.id.avatar, model.getAvatarUrl())
                .setOnClickListener(() -> {
                    final boolean willChecked = finder.find(R.id.check).getVisibility() == GONE;
                    finder.find(R.id.check).setVisibility(willChecked ? VISIBLE : GONE);
                    listener.onStargazerItemClicked(model, willChecked);
                })
                .setOnClickListener(R.id.check, () -> {
                    final boolean willChecked = finder.find(R.id.check).getVisibility() == GONE;
                    finder.find(R.id.check).setVisibility(willChecked ? VISIBLE : GONE);
                    listener.onStargazerItemClicked(model, willChecked);
                }));
    }

    @Nullable
    @Override
    public ViewState createViewState() {
        return new StargazerViewState();
    }

    @Override
    public int createViewStateID(@NonNull final StargazerModel model) {
        return model.getID();
    }

    public interface Listener {
        void onStargazerItemClicked(@NonNull StargazerModel model, final boolean isChecked);
    }
}