/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.testing._auth.githubtesting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GithubResponseDto Class.
 * <p>
 * </p>
 *
 * @author
 */

public class GithubResponseDto {

    private String name;
    @JsonProperty("full_name")

    private String fullName;

    private String description;

    @JsonProperty("html_url")
    private String htmlUrl;

    private boolean fork;

    @JsonProperty("stargazers_count")
    private int stargazersCount;

    private String language;
    // Add more fields as needed, matching the JSON response from GitHub

    // Constructor (optional, but good practice for immutability)
    public GithubResponseDto() {
    }

    public GithubResponseDto(String name, String fullName, String description, String htmlUrl, boolean fork, int stargazersCount, String language) {
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.htmlUrl = htmlUrl;
        this.fork = fork;
        this.stargazersCount = stargazersCount;
        this.language = language;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "GitHubRepository{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", fork=" + fork +
                ", stargazersCount=" + stargazersCount +
                ", language='" + language + '\'' +
                '}';
    }


}
