package com.github.vivchar.rendererrecyclerviewadapter;

import androidx.annotation.NonNull;

/**
 * Created by Vivchar Vitaly on 11.07.19.
 */

public class WrongViewFinderException extends RuntimeException {

    public WrongViewFinderException(@NonNull final ClassCastException e) {
        super("Looks like you are trying to use a custom ViewFinder, " +
                "but forgot to implement or register it. As a result you've got the following error:" +
                " \"" + e.getMessage() + "\". " +
                "Please use RendererRecyclerViewAdapter.registerViewFinder() to support your custom ViewFinder.");
    }
}
