package com.github.vivchar.rendererrecyclerviewadapter;

/**
 * Created by Vivchar Vitaly on 11.07.19.
 */

public class WrongViewFinderException extends RuntimeException {

    public WrongViewFinderException(String message) {
        super("Looks like you are trying to use a custom ViewFinder, " +
                "but forgot to implement or register it, " +
                "please use RendererRecyclerViewAdapter.registerViewFinder() to support your custom ViewFinder. \n " + message);
    }
}
