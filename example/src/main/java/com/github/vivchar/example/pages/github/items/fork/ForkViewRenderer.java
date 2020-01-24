package com.github.vivchar.example.pages.github.items.fork;

import androidx.annotation.NonNull;

import com.github.vivchar.example.R;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public class ForkViewRenderer extends ViewRenderer<ForkModel, CustomViewFinder> {

    public ForkViewRenderer(@NonNull final Listener listener) {
        super(R.layout.item_fork, ForkModel.class, (model, finder, payloads) -> finder
                .setOnClickListener(view -> listener.onForkItemClicked(model))
                .setText(R.id.fork_name, model.getName())
                .setUrl(R.id.fork_avatar, model.getAvatarUrl())
        );
    }

    public interface Listener {
        void onForkItemClicked(@NonNull ForkModel model);
    }
}
