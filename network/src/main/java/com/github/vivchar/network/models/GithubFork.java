package com.github.vivchar.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vivchar Vitaly on 12.10.17.
 */

public class GithubFork {

	@SerializedName("id")
	@Expose
	public int id;
	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("full_name")
	@Expose
	public String fullName;
	@SerializedName("owner")
	@Expose
	public GithubUser owner;
	@SerializedName("private")
	@Expose
	public boolean _private;
	@SerializedName("html_url")
	@Expose
	public String htmlUrl;
	@SerializedName("description")
	@Expose
	public String description;
	@SerializedName("fork")
	@Expose
	public boolean fork;
	@SerializedName("url")
	@Expose
	public String url;
	@SerializedName("forks_url")
	@Expose
	public String forksUrl;
	@SerializedName("keys_url")
	@Expose
	public String keysUrl;
	@SerializedName("collaborators_url")
	@Expose
	public String collaboratorsUrl;
	@SerializedName("teams_url")
	@Expose
	public String teamsUrl;
	@SerializedName("hooks_url")
	@Expose
	public String hooksUrl;
	@SerializedName("issue_events_url")
	@Expose
	public String issueEventsUrl;
	@SerializedName("events_url")
	@Expose
	public String eventsUrl;
	@SerializedName("assignees_url")
	@Expose
	public String assigneesUrl;
	@SerializedName("branches_url")
	@Expose
	public String branchesUrl;
	@SerializedName("tags_url")
	@Expose
	public String tagsUrl;
	@SerializedName("blobs_url")
	@Expose
	public String blobsUrl;
	@SerializedName("git_tags_url")
	@Expose
	public String gitTagsUrl;
	@SerializedName("git_refs_url")
	@Expose
	public String gitRefsUrl;
	@SerializedName("trees_url")
	@Expose
	public String treesUrl;
	@SerializedName("statuses_url")
	@Expose
	public String statusesUrl;
	@SerializedName("languages_url")
	@Expose
	public String languagesUrl;
	@SerializedName("stargazers_url")
	@Expose
	public String stargazersUrl;
	@SerializedName("contributors_url")
	@Expose
	public String contributorsUrl;
	@SerializedName("subscribers_url")
	@Expose
	public String subscribersUrl;
	@SerializedName("subscription_url")
	@Expose
	public String subscriptionUrl;
	@SerializedName("commits_url")
	@Expose
	public String commitsUrl;
	@SerializedName("git_commits_url")
	@Expose
	public String gitCommitsUrl;
	@SerializedName("comments_url")
	@Expose
	public String commentsUrl;
	@SerializedName("issue_comment_url")
	@Expose
	public String issueCommentUrl;
	@SerializedName("contents_url")
	@Expose
	public String contentsUrl;
	@SerializedName("compare_url")
	@Expose
	public String compareUrl;
	@SerializedName("merges_url")
	@Expose
	public String mergesUrl;
	@SerializedName("archive_url")
	@Expose
	public String archiveUrl;
	@SerializedName("downloads_url")
	@Expose
	public String downloadsUrl;
	@SerializedName("issues_url")
	@Expose
	public String issuesUrl;
	@SerializedName("pulls_url")
	@Expose
	public String pullsUrl;
	@SerializedName("milestones_url")
	@Expose
	public String milestonesUrl;
	@SerializedName("notifications_url")
	@Expose
	public String notificationsUrl;
	@SerializedName("labels_url")
	@Expose
	public String labelsUrl;
	@SerializedName("releases_url")
	@Expose
	public String releasesUrl;
	@SerializedName("deployments_url")
	@Expose
	public String deploymentsUrl;
	@SerializedName("created_at")
	@Expose
	public String createdAt;
	@SerializedName("updated_at")
	@Expose
	public String updatedAt;
	@SerializedName("pushed_at")
	@Expose
	public String pushedAt;
	@SerializedName("git_url")
	@Expose
	public String gitUrl;
	@SerializedName("ssh_url")
	@Expose
	public String sshUrl;
	@SerializedName("clone_url")
	@Expose
	public String cloneUrl;
	@SerializedName("svn_url")
	@Expose
	public String svnUrl;
	@SerializedName("homepage")
	@Expose
	public String homepage;
	@SerializedName("size")
	@Expose
	public int size;
	@SerializedName("stargazers_count")
	@Expose
	public int stargazersCount;
	@SerializedName("watchers_count")
	@Expose
	public int watchersCount;
	@SerializedName("language")
	@Expose
	public String language;
	@SerializedName("has_issues")
	@Expose
	public boolean hasIssues;
	@SerializedName("has_projects")
	@Expose
	public boolean hasProjects;
	@SerializedName("has_downloads")
	@Expose
	public boolean hasDownloads;
	@SerializedName("has_wiki")
	@Expose
	public boolean hasWiki;
	@SerializedName("has_pages")
	@Expose
	public boolean hasPages;
	@SerializedName("forks_count")
	@Expose
	public int forksCount;
	@SerializedName("mirror_url")
	@Expose
	public Object mirrorUrl;
	@SerializedName("open_issues_count")
	@Expose
	public int openIssuesCount;
	@SerializedName("forks")
	@Expose
	public int forks;
	@SerializedName("open_issues")
	@Expose
	public int openIssues;
	@SerializedName("watchers")
	@Expose
	public int watchers;
	@SerializedName("default_branch")
	@Expose
	public String defaultBranch;

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public GithubUser getOwner() {
		return owner;
	}

	public String getOwnerLogin() {
		return owner.login;
	}

	public String getOwnerAvatarUrl() {
		return owner.avatarUrl;
	}

	public String getOwnerHtmlUrl() {
		return owner.htmlUrl;
	}

	public boolean is_private() {
		return _private;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public String getDescription() {
		return description;
	}

	public boolean isFork() {
		return fork;
	}

	public String getUrl() {
		return url;
	}

	public String getForksUrl() {
		return forksUrl;
	}

	public String getKeysUrl() {
		return keysUrl;
	}

	public String getCollaboratorsUrl() {
		return collaboratorsUrl;
	}

	public String getTeamsUrl() {
		return teamsUrl;
	}

	public String getHooksUrl() {
		return hooksUrl;
	}

	public String getIssueEventsUrl() {
		return issueEventsUrl;
	}

	public String getEventsUrl() {
		return eventsUrl;
	}

	public String getAssigneesUrl() {
		return assigneesUrl;
	}

	public String getBranchesUrl() {
		return branchesUrl;
	}

	public String getTagsUrl() {
		return tagsUrl;
	}

	public String getBlobsUrl() {
		return blobsUrl;
	}

	public String getGitTagsUrl() {
		return gitTagsUrl;
	}

	public String getGitRefsUrl() {
		return gitRefsUrl;
	}

	public String getTreesUrl() {
		return treesUrl;
	}
}
