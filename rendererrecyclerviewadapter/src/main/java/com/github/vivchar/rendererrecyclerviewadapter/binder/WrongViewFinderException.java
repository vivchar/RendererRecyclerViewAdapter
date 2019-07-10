package com.github.vivchar.rendererrecyclerviewadapter.binder;

/**
 * Created by Vivchar Vitaly on 11.07.19.
 */

class WrongViewFinderException extends RuntimeException {

	public WrongViewFinderException() {
		super("Looks like you are trying to use a custom ViewFinder, " +
		      "but forgot to implement or register it, " +
		      "please use RendererRecyclerViewAdapter.registerViewFinder() to support your custom ViewFinder.");
	}
}
