package com.github.vivchar.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vivchar Vitaly on 09.10.17.
 */
public class GithubUser {

	@SerializedName("login")
	@Expose
	public String login;
	@SerializedName("id")
	@Expose
	public int id;
	@SerializedName("avatar_url")
	@Expose
	public String avatarUrl;
	@SerializedName("gravatar_id")
	@Expose
	public String gravatarId;
	@SerializedName("url")
	@Expose
	public String url;
	@SerializedName("html_url")
	@Expose
	public String htmlUrl;
	@SerializedName("followers_url")
	@Expose
	public String followersUrl;
	@SerializedName("following_url")
	@Expose
	public String followingUrl;
	@SerializedName("gists_url")
	@Expose
	public String gistsUrl;
	@SerializedName("starred_url")
	@Expose
	public String starredUrl;
	@SerializedName("subscriptions_url")
	@Expose
	public String subscriptionsUrl;
	@SerializedName("organizations_url")
	@Expose
	public String organizationsUrl;
	@SerializedName("repos_url")
	@Expose
	public String reposUrl;
	@SerializedName("events_url")
	@Expose
	public String eventsUrl;
	@SerializedName("received_events_url")
	@Expose
	public String receivedEventsUrl;
	@SerializedName("type")
	@Expose
	public String type;
	@SerializedName("site_admin")
	@Expose
	public boolean siteAdmin;

	public String getLogin() {
		return login;
	}

	public int getID() {
		return id;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getGravatarId() {
		return gravatarId;
	}

	public String getUrl() {
		return url;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public String getFollowersUrl() {
		return followersUrl;
	}

	public String getFollowingUrl() {
		return followingUrl;
	}

	public String getGistsUrl() {
		return gistsUrl;
	}

	public String getStarredUrl() {
		return starredUrl;
	}

	public String getSubscriptionsUrl() {
		return subscriptionsUrl;
	}

	public String getOrganizationsUrl() {
		return organizationsUrl;
	}

	public String getReposUrl() {
		return reposUrl;
	}

	public String getEventsUrl() {
		return eventsUrl;
	}

	public String getReceivedEventsUrl() {
		return receivedEventsUrl;
	}

	public String getType() {
		return type;
	}

	public boolean isSiteAdmin() {
		return siteAdmin;
	}

	@Override
	public String toString() {
		return GithubUser.class.getSimpleName() +
		       "{" + id +
		       ", " + login +
		       ", " + avatarUrl +
		       ", " + gravatarId +
		       ", " + url +
		       ", " + htmlUrl +
		       ", " + followersUrl +
		       ", " + followingUrl +
		       ", " + gistsUrl +
		       ", " + starredUrl +
		       ", " + subscriptionsUrl +
		       ", " + organizationsUrl +
		       ", " + reposUrl +
		       ", " + eventsUrl +
		       ", " + receivedEventsUrl +
		       ", " + type +
		       ", " + siteAdmin +
		       '}';
	}
}