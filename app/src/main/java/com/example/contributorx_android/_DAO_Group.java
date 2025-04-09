package com.example.contributorx_android;

import java.util.ArrayList;
import java.util.List;

public class _DAO_Group {

    private static int lastId = 0;

    private static List<Group> groups = new ArrayList<>();

    public static List<Group> GetAllGroups() {
        return groups;
    }

    public static int AddGroup(Group group) {
        group.setId(++lastId);
        groups.add(group);
        return group.getId();
    }

    public static void UpdateGroup(Group group) {
        for (int index = 0; index < groups.size(); index++) {
            if (groups.get(index).getId() == group.getId()) {
                groups.set(index, group);
                return;
            }
        }
    }

    public static void DeleteGroup(int id) {
        for (int index = 0; index < groups.size(); index++) {
            if (groups.get(index).getId() == id) {
                groups.remove(index);
                return;
            }
        }
    }

    public static Group GetGroup(int id) {
        for (int index = 0; index < groups.size(); index++) {
            if (groups.get(index).getId() == id) {
                return groups.get(index);
            }
        }

        return null;
    }
}
