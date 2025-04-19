package com.example.contributorx_android;

import java.util.ArrayList;
import java.util.List;

public class _DAO_Contribution {

    private static int lastId = 0;

    private static List<Contribution> contributions = new ArrayList<>();

    public static List<Contribution> GetAllContributions() {

        return contributions;

    }

    public static List<Contribution> GetAllContributionsInCommunity(int communityId) {
        List<Contribution> list = new ArrayList<>();
        for (Contribution contribution : contributions) {
            Group group = _DAO_Group.GetGroup(contribution.getGroupId());
            if (group != null) {
                if (group.getCommunityId() == communityId) {
                    list.add(contribution);
                }
            }
        }
        return list;
    }

    public static int AddContribution(Contribution contribution) {
        contribution.setId(++lastId);
        contributions.add(contribution);
        return contribution.getId();
    }

    public static void UpdateContribution(Contribution contribution) {
        for (int index = 0; index < contributions.size(); index++) {
            if (contributions.get(index).getId() == contribution.getId()) {
                contributions.set(index, contribution);
                return;
            }
        }
    }

    public static void DeleteContribution(int id) {
        for (int index = 0; index < contributions.size(); index++) {
            if (contributions.get(index).getId() == id) {
                contributions.remove(index);
                return;
            }
        }
    }

    public static Contribution GetContribution(int id) {
        for (int index = 0; index < contributions.size(); index++) {
            if (contributions.get(index).getId() == id) {
                return contributions.get(index);
            }
        }
        return null;
    }
}
