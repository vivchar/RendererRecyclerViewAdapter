package com.github.vivchar.example.pages.simple;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vivchar.example.BaseScreenFragment;
import com.github.vivchar.example.R;
import com.github.vivchar.example.widgets.BetweenSpacesItemDecoration;
import com.github.vivchar.example.widgets.MyAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder;

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */
@Deprecated
public class ViewRendererFragment extends BaseScreenFragment {

    private YourDataProvider mYourDataProvider = new YourDataProvider();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_list, container, false);

        final RendererRecyclerViewAdapter adapter = new MyAdapter();

        adapter.registerRenderer(new RectViewRenderer(RectViewModel.class));
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

        adapter.setItems(mYourDataProvider.getSquareItems());

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

        return view;
    }

    public static class RectViewRenderer extends ViewRenderer<RectViewModel, ViewFinder> {

        public RectViewRenderer(@NonNull final Class<RectViewModel> type) {
            super(R.layout.item_simple, type, (model, finder, payloads) -> finder
                    .setText(R.id.text, model.getText())
            );
        }
    }

    public static class RectViewModel implements ViewModel {

        private int mID;
        private final String mText;

        public RectViewModel(final int ID, final String text) {
            mID = ID;
            mText = text;
        }

        public int getID() {
            return mID;
        }

        public String getText() {
            return mText;
        }
    }
}