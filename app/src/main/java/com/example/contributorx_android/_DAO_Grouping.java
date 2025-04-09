package com.example.contributorx_android;

import java.util.ArrayList;
import java.util.List;

public class _DAO_Grouping {

    private static int lastId = 0;

    private static List<Grouping> groupings = new ArrayList<>();

    public static List<Grouping> GetAllGrouping() {
        return groupings;
    }

    public static int AddGrouping(Grouping grouping) {
        grouping.setId(++lastId);
        groupings.add(grouping);
        return grouping.getId();
    }

    public static void DeleteGrouping(int id) {
        for (int index = 0; index < groupings.size(); index++) {
            if (groupings.get(index).getId() == id) {
                groupings.remove(index);
                return;
            }
        }
    }

    public static Grouping GetGrouping(int id) {
        for (int index = 0; index < groupings.size(); index++) {
            if (groupings.get(index).getId() == id) {
                return groupings.get(index);
            }
        }
        return null;
    }

    public static void UpdateGrouping(Grouping grouping) {
        for (int index = 0; index < groupings.size(); index++) {
            if (groupings.get(index).getId() == grouping.getId()) {
                groupings.set(index, grouping);
                return;
            }
        }
    }

    public static List<Grouping> GetGroupingsForContributor(int contributorId) {
        List<Grouping> result = new ArrayList<>();

        for (int i = 0; i < groupings.size(); i++) {
            if (groupings.get(i).getContributorId() == contributorId) {
                result.add(groupings.get(i));
            }
        }

        return result;
    }
}
