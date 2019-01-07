package com.example.mac.trendingrepos;

public class RepoInfo {
    String name;
    String owner;
    int stars;
    String description;

    public RepoInfo(String name, String owner, String description, int stars) {
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

    public int getStars() {
        return stars;
    }

    public String getDescription() {
        return description;
    }
}
