package com.github.vivchar.example.pages.github.items.selected;

import com.github.vivchar.example.R;
import com.github.vivchar.example.pages.github.items.fork.CustomViewFinder;
import com.github.vivchar.example.pages.github.items.stargazer.StargazerModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

/**
 * Created by Vivchar Vitaly on 1/10/17.
 */
public class UserViewRenderer extends ViewRenderer<StargazerModel, CustomViewFinder> {

    public UserViewRenderer() {
        super(R.layout.item_user_selected, StargazerModel.class, (model, finder, payloads) -> finder
                .setText(R.id.name, model.getName())
                .setUrl(R.id.avatar, model.getAvatarUrl())
        );
    }
}
