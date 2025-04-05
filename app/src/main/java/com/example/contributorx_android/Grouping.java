package com.example.contributorx_android;

public class Grouping {
    private int id = 0;
    private int contributorId = 0;
    private int groupId = 0;

    public Grouping() {
    }

    public Grouping(int contributorId, int groupId) {
        this.contributorId = contributorId;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContributorId() {
        return contributorId;
    }

    public void setContributorId(int contributorId) {
        this.contributorId = contributorId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
