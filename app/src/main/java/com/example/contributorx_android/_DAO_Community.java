package com.example.contributorx_android;

import java.util.ArrayList;
import java.util.List;

public class _DAO_Community {

    private static int lastId = 0;

    private static List<Community> communities = new ArrayList<>();

    public static List<Community> GetAllCommunitys() {
        return communities;
    }

    public static int AddCommunity(Community community) {
        community.setId(++lastId);
        communities.add(community);
        return community.getId();
    }

    public static void UpdateCommunity(Community community) {
        for (int index = 0; index < communities.size(); index++) {
            if (communities.get(index).getId() == community.getId()) {
                communities.set(index, community);
                return;
            }
        }
    }

    public static void DeleteCommunity(int id) {
        for (int index = 0; index < communities.size(); index++) {
            if (communities.get(index).getId() == id) {
                communities.remove(index);
                return;
            }
        }
    }

    public static Community GetCommunity(int id) {
        for (int index = 0; index < communities.size(); index++) {
            if (communities.get(index).getId() == id) {
                return communities.get(index);
            }
        }

        return null;
    }
}
