package com.example.mac.trendingrepos;

public class RepoInfo {
    private String name;
    private String owner;
    private String stars;
    private String description;

    public RepoInfo(String name, String owner, String description, String stars) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getStars() {
        return stars;
    }

    public String getDescription() {
        return description;
    }
}
