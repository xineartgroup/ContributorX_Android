package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Grouping {
    private int id = 0;
    private int contributorId = 0;
    private int groupId = 0;
    private Group group = null;

    public Grouping() {
    }

    public Grouping(int contributorId, int groupId) {
        this.contributorId = contributorId;
        this.groupId = groupId;
    }

    @JsonProperty("Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("ContributorId")
    public int getContributorId() {
        return contributorId;
    }

    public void setContributorId(int contributorId) {
        this.contributorId = contributorId;
    }

    @JsonProperty("GroupId")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @JsonProperty("Group")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
