package com.github.vivchar.network;

import com.github.vivchar.network.models.GithubForkRaw;
import com.github.vivchar.network.models.GithubUserRaw;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vivchar Vitaly on 09.10.17.
 */
interface GithubAPI {

	@GET("repos/vivchar/RendererRecyclerViewAdapter/stargazers")
	Call<List<GithubUserRaw>> getStargazers(@Query("page") int page);

	@GET("repos/vivchar/RendererRecyclerViewAdapter/forks")
	Call<List<GithubForkRaw>> getForks();
}
