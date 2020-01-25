package com.github.vivchar.example;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import com.github.vivchar.example.pages.github.GithubFragment;
import com.github.vivchar.example.pages.simple.CompositeViewRendererFragment;
import com.github.vivchar.example.pages.simple.DiffUtilFragment;
import com.github.vivchar.example.pages.simple.LoadMoreFragment;
import com.github.vivchar.example.pages.simple.PayloadFragment;
import com.github.vivchar.example.pages.simple.ViewRendererFragment;
import com.github.vivchar.example.pages.simple.ViewStateFragment;

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */

public class UIRouter {

	@NonNull
	private final Activity mContext;
	@NonNull
	private final FragmentManager mFragmentManager;

	public UIRouter(@NonNull final AppCompatActivity activity) {
		mContext = activity;
		mFragmentManager = activity.getSupportFragmentManager();
	}

	@NonNull
	public Activity getContext() {
		return mContext;
	}

	@NonNull
	public FragmentManager getFragmentManager() {
		return mFragmentManager;
	}

	public void openGithubPage() {
		showFragment(new GithubFragment());
	}

	private void showFragment(@NonNull final BaseScreenFragment fragment) {
		try {
			getFragmentManager().beginTransaction()
					.replace(R.id.screen_container, fragment, fragment.getClass().getName())
					.commitAllowingStateLoss();
		} catch (IllegalStateException ignored) {
		}
	}

	public void openViewRendererPage() {
		showFragment(new ViewRendererFragment());
	}

	public void openCompositeViewRendererPage() {
		showFragment(new CompositeViewRendererFragment());
	}

	public void openViewStatePage() {
		showFragment(new ViewStateFragment());
	}

	public void openDiffUtilPage() {
		showFragment(new DiffUtilFragment());
	}

	public void openPayloadPage() {
		showFragment(new PayloadFragment());
	}

	public void openLoadMorePage() {
		showFragment(new LoadMoreFragment());
	}
}
