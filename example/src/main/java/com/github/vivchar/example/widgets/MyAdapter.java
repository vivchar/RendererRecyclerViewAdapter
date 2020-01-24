package com.github.vivchar.example.widgets;

import com.github.vivchar.example.pages.github.items.fork.CustomViewFinder;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

public class MyAdapter extends RendererRecyclerViewAdapter {
    public MyAdapter() {
        super();
        registerViewFinder(CustomViewFinder::new);
    }
}
